package UI;

/**
 * Created by Mateusz on 10/01/2016.
 */

import Base.IContext;
import Base.Infrastructure.ReflectionHelper;
import Base.Infrastructure.ShowInUI;
import Base.Logic.IDiscountLogic;
import Base.Model.*;
import Logic.DiscountLogic;
import Repository.ModelContext;
import UI.TableCells.ColorTableCell;
import UI.TableCells.DateTableCell;
import UI.TableCells.NumberTableCell;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Entry point of application.
 */
public class MainForm extends Application {
    private final IContext context = new ModelContext();
    private final IDiscountLogic discountLogic = new DiscountLogic(context.discounts());
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Sklep odzieżowy");
        primaryStage.setHeight(500);
        primaryStage.setWidth(1000);
        primaryStage.setResizable(false);
        Scene scene = new Scene(new VBox(), 500, 500);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        for (Class<?> type : context.getModelTypes()) {
            Tab tab = new Tab();
            tab.setText(UIHelper.getDisplayName(type));
            tab.setContent(getContentFor(type));
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                TableView tableView = (TableView) newValue.getContent().lookup("#TableView" + type.getSimpleName());
                if (tableView != null)
                    tableView.refresh();
            });
        }

        ((VBox) scene.getRoot()).getChildren().addAll(tabPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node getContentFor(Class<?> type) {
        TableView tableView = createTableViewFor(type);
        tableView.setId("TableView" + type.getSimpleName());

        if (type == Discount.class)
            tableView.getColumns().add(createProductNameColumn());

        for (Field field : ReflectionHelper.getAllFields(type)) {
            TableColumn tableColumn = new TableColumn(UIHelper.getDisplayName(field));
            tableColumn.setStyle("-fx-alignment: CENTER;");

            if (field.getType() == BigDecimal.class) {
                tableColumn.setCellFactory(param -> new NumberTableCell());
            } else if (field.getType() == Color.class) {
                tableColumn.setCellFactory(param -> new ColorTableCell());
            } else if (field.getType() == Date.class) {
                tableColumn.setCellFactory(param -> new DateTableCell());
            }

            tableColumn.setCellValueFactory(new PropertyValueFactory(field.getName()));
            tableColumn.setVisible(field.getAnnotation(ShowInUI.class) != null);
            tableView.getColumns().add(tableColumn);
        }

        if (type == Boots.class || type == Pants.class)
            tableView.getColumns().add(createDiscountColumn());

        ObservableList observableList = FXCollections.observableArrayList(context.getRepositoryFor(type).getAll());
        FilteredList filteredList = new FilteredList(observableList);
        SortedList sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedList);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        if (type != Discount.class) {
            tableView.getSelectionModel().selectedItemProperty().addListener(onSelectedItemChanged(imageView));
        }

        final Button addButton = new Button("Dodaj");
        addButton.setOnAction(onAdding(type, tableView, observableList));

        final Button editButton = new Button("Edytuj");
        editButton.setOnAction(onEditing(type, tableView));

        final Button deleteButton = new Button("Usuń");
        deleteButton.setOnAction(onDeleting(type, tableView, observableList));

        final Button setImageButton = new Button("Ustaw zdjęcie");
        setImageButton.setOnAction(event -> {
            try {
                EntityBase product = (EntityBase) tableView.getSelectionModel().getSelectedItem();
                if (product != null) {
                    final FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Wybierz zdjęcie");
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                            new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                            new FileChooser.ExtensionFilter("PNG", "*.png")
                    );
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        context.images().setForProduct((Product) product, file.getPath());
                        imageView.setImage(new Image(file.toURI().toString()));
                    }
                }
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        final Button discountButton = new Button("Ustaw promocję");
        discountButton.setOnAction(onAddingDiscount(tableView));

        TextField searchField = new TextField();
        searchField.textProperty().addListener(onSearchTextChanged(filteredList));


        HBox menuBox = new HBox();
        menuBox.setSpacing(5);
        menuBox.setPadding(new Insets(10, 10, 10, 10));
        menuBox.getChildren().addAll(new Label("Szukaj: "), searchField);
        if (type != Discount.class)
            menuBox.getChildren().addAll(addButton, editButton);
        menuBox.getChildren().addAll(deleteButton);
        if (type != Discount.class)
            menuBox.getChildren().add(setImageButton);
        if (type == Boots.class || type == Pants.class)
            menuBox.getChildren().add(discountButton);

        HBox mainBox = new HBox();
        mainBox.setSpacing(5);
        mainBox.setPadding(new Insets(10, 10, 10, 10));
        mainBox.getChildren().addAll(tableView, imageView);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(menuBox, mainBox);
        return vbox;
    }

    private ChangeListener onSelectedItemChanged(ImageView imageView) {
        return (observable, oldValue, newValue) -> {
            Product product = (Product) newValue;
            if (product == null)
                imageView.setImage(null);
            else
                imageView.setImage(context.images().getForProduct(product.getId()));

        };
    }

    private ChangeListener<String> onSearchTextChanged(FilteredList filteredList) {
        return (observable, oldValue, newValue) -> {
            filteredList.setPredicate(entity -> {
                try {
                    if (newValue == null || newValue.isEmpty())
                        return true;

                    String lowerCaseFilter = newValue.toLowerCase();

                    for (Field field : ReflectionHelper.getAllFields(entity.getClass())) {
                        ShowInUI showInUI = field.getAnnotation(ShowInUI.class);
                        if (showInUI != null) {
                            field.setAccessible(true);
                            Object value = field.get(entity);
                            String text = value == null ? "" : value.toString();

                            if (text.toLowerCase().contains(lowerCaseFilter))
                                return true;
                        }
                    }
                } catch (Exception ex) {
                    UIHelper.showException(ex);
                }

                return false;
            });
        };
    }

    private EventHandler<ActionEvent> onAddingDiscount(TableView tableView) {
        return discountEventArg -> {
            try {
                Product product = (Product) tableView.getSelectionModel().getSelectedItem();
                if (product != null) {
                    Discount discount = new Discount();
                    discount.setProductId(product.getId());

                    Stage dialog = new EditForm<>(discount);
                    dialog.setTitle("Promocja");
                    dialog.setHeight(200);
                    dialog.setWidth(300);
                    dialog.showAndWait();

                    discount = (Discount) dialog.getUserData();
                    if (discount == null)
                        return;

                    discountLogic.addDiscount(discount, product);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Promocja");
                    alert.setHeaderText("Dodano promocje dla produktu: " + product.getName());
                    alert.showAndWait();

                    tableView.refresh();

                    TableView discountTable = (TableView) primaryStage.getScene().lookup("#TableView" + Discount.class.getSimpleName());
                    if (discountTable != null) {
                        ObservableList observableList = FXCollections.observableArrayList(discountTable.getItems());
                        observableList.add(discount);
                        FilteredList filteredList = new FilteredList(observableList);
                        SortedList sortedList = new SortedList(filteredList);
                        sortedList.comparatorProperty().bind(discountTable.comparatorProperty());

                        discountTable.setItems(sortedList);

                    }
                }
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        };
    }

    private EventHandler<ActionEvent> onDeleting(Class<?> type, TableView tableView, ObservableList observableList) {
        return deleteEventArg -> {
            EntityBase product = (EntityBase) tableView.getSelectionModel().getSelectedItem();
            if (product != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText("Usuwanie elementu");
                alert.setContentText("Jesteś pewny, że chcesz usunąć ten produkt?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    context.getRepositoryFor(type).delete(product);

                    observableList.remove(product);
                    FilteredList filteredList = new FilteredList(observableList);
                    SortedList sortedList = new SortedList(filteredList);
                    sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                    tableView.setItems(sortedList);

                    TableView discountTable = (TableView) primaryStage.getScene().lookup("#TableView" + Discount.class.getSimpleName());
                    ObservableList discountList =  FXCollections.observableArrayList(discountTable.getItems());
                    for (Discount discount : context.discounts().getAllByProductId(product.getId())) {
                        context.discounts().delete(discount);
                        discountList.remove(discount);
                    }
                    SortedList sortedDiscountList = new SortedList(new FilteredList(discountList));
                    sortedDiscountList.comparatorProperty().bind(discountTable.comparatorProperty());
                    discountTable.setItems(sortedDiscountList);
                }
            }
        };
    }

    private EventHandler<ActionEvent> onEditing(Class<?> type, TableView tableView) {
        return editEventArg -> {
            try {
                EntityBase product = (EntityBase) tableView.getSelectionModel().getSelectedItem();
                if (product != null) {
                    Stage dialog = new EditForm<>(product);
                    dialog.setTitle("Edycja elementu");
                    dialog.showAndWait();

                    product = (EntityBase) dialog.getUserData();
                    if (product == null)
                        return;
                    if (Discount.class == type) {
                        try {

                        } catch (Exception ex) {

                        }
                    } else
                        context.getRepositoryFor(type).update(product);
                    tableView.refresh();
                }
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        };
    }

    private EventHandler<ActionEvent> onAdding(Class<?> type, TableView tableView, ObservableList observableList) {
        return addEventArg -> {
            try {
                Product product = (Product) type.getConstructor().newInstance();
                Stage dialog = new EditForm<>(product);
                dialog.setTitle("Dodawanie nowego elementu");
                dialog.showAndWait();

                product = (Product) dialog.getUserData();
                if (product == null)
                    return;

                context.getRepositoryFor(type).add(product);

                observableList.add(product);
                FilteredList filteredList = new FilteredList(observableList);
                SortedList sortedList = new SortedList(filteredList);
                sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedList);

            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        };
    }

    private <T> TableView<T> createTableViewFor(Class<T> type) {
        return new TableView<>();
    }

    private TableColumn createDiscountColumn() {
        TableColumn tableColumn = new TableColumn("CENA PROMOCYJNA!");
        tableColumn.setStyle("-fx-text-fill: green; -fx-font-weight:bold; -fx-alignment: CENTER;");
        tableColumn.setCellFactory(param -> new NumberTableCell());
        tableColumn.setCellValueFactory(cellDataFeatures -> {
            try {
                Product product = ((TableColumn.CellDataFeatures<Product, ?>) cellDataFeatures).getValue();
                return new SimpleDoubleProperty(discountLogic.getPriceAfterDiscount(product).doubleValue());
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }

            return null;
        });

        return tableColumn;
    }

    private TableColumn createProductNameColumn() {
        TableColumn tableColumn = new TableColumn("Nazwa produktu");
        tableColumn.setStyle("-fx-text-fill: green; -fx-font-weight:bold; -fx-alignment: CENTER;");
        tableColumn.setCellValueFactory(cellDataFeatures -> {
            try {
                Discount discount = ((TableColumn.CellDataFeatures<Discount, ?>) cellDataFeatures).getValue();
                return new SimpleStringProperty(context.getProductById(discount.getProductId()).getName());
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }

            return null;
        });

        return tableColumn;
    }
}