package UI;

import Base.Infrastructure.ReflectionHelper;
import Base.Infrastructure.ShowInUI;
import Base.Model.EntityBase;
import UI.Fields.NumberField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Mateusz on 17/01/2016.
 * A form used to creating and editing elements. A logic of form uses reflections to select fields to display.
 */
public class EditForm<T extends EntityBase> extends Stage {
    private T editedEntity;
    private T originalEntity;

    @SuppressWarnings("unchecked")
    public EditForm(T entity) throws IllegalAccessException, InstantiationException {
        if (entity == null)
            throw new IllegalArgumentException("Argument entity can not by null.");

        this.editedEntity = (T) entity.getClass().newInstance();
        this.originalEntity = entity;

        this.render();

        this.sizeToScene();
        this.setResizable(false);
    }

    /**
     * Renders a content for edit form.
     */
    private void render() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(gridPane);

        List<Field> fields = ReflectionHelper.getAllFields(editedEntity.getClass());
        fields.removeIf(field -> field.getAnnotation(ShowInUI.class) == null);

        int row = 0;
        for (Field field : fields) {
            gridPane.add(new Label(UIHelper.getDisplayName(field)), 0, row);

            try {
                field.setAccessible(true);
                Object value = field.get(originalEntity);

                // TODO: Pozbyć się tej drabinki if-else (np. słownik typ -> action)
                Control control;
                if (Enum.class.isAssignableFrom(field.getType())) {
                    control = createComboBox(field, value);
                } else if (field.getType() == BigDecimal.class) {
                    control = createBigDecimalField(field, (BigDecimal) value);
                } else if (field.getType() == double.class) {
                    control = createDoubleField(field, (double) value);
                } else if (field.getType() == boolean.class) {
                    control = createCheckBox(field, (boolean) value);
                } else if (field.getType() == int.class) {
                    control = createIntField(field, (int) value);
                } else if (field.getType() == java.awt.Color.class) {
                    control = createColorField(field, (java.awt.Color) value);
                } else if (field.getType() == Date.class) {
                    control = createDateField(field, value);
                } else {
                    control = createTextField(field, value);
                }

                control.setId(field.getName());
                gridPane.add(control, 1, row++);
            } catch (Exception ex) {
                UIHelper.showException(ex);
                return;
            }
        }

        Button saveButton = new Button("Zapisz");
        saveButton.setOnAction(onSaving(scene));
        gridPane.add(saveButton, 0, row);
        this.setScene(scene);
    }

    private TextField createTextField(Field field, Object value) {
        TextField textField = new TextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, newValue);
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        textField.setText(value == null ? "" : value.toString());
        return textField;
    }

    private DatePicker createDateField(Field field, Object value) {
        DatePicker datePicker = new DatePicker();
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        if (value != null) {
            datePicker.setValue(((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        return datePicker;
    }

    private ColorPicker createColorField(Field field, java.awt.Color value) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, new java.awt.Color((float) newValue.getRed(), (float) newValue.getGreen(), (float) newValue.getBlue()));
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        java.awt.Color color = value;
        if (color != null) {
            colorPicker.setValue(Color.rgb(color.getRed(), color.getGreen(), color.getGreen()));
        }
        return colorPicker;
    }

    private NumberField createIntField(Field field, int value) {
        NumberField numberField = new NumberField(0);
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, new IntegerStringConverter().fromString(newValue));
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        numberField.setText(new IntegerStringConverter().toString(value));
        return numberField;
    }

    private CheckBox createCheckBox(Field field, boolean value) {
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, newValue);
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        checkBox.setSelected(value);
        return checkBox;
    }

    private NumberField createDoubleField(Field field, double value) {
        NumberField numberField = new NumberField();
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, new DoubleStringConverter().fromString(newValue));
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        numberField.setText(new DoubleStringConverter().toString(value));
        return numberField;
    }

    private NumberField createBigDecimalField(Field field, BigDecimal value) {
        NumberField numberField = new NumberField();
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, new BigDecimalStringConverter().fromString(newValue));
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        });

        numberField.setText(new BigDecimalStringConverter().toString(value));
        return numberField;
    }

    private ComboBox<?> createComboBox(Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ComboBox<?> comboBox = createComboBoxFor(field.getType());
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                field.set(editedEntity, newValue);
            } catch (IllegalAccessException ex) {
                UIHelper.showException(ex);
            }
        });

        ((ComboBox<Object>) comboBox).setValue(value);
        return comboBox;
    }

    /**
     * @param scene A root scene for EditForm.
     * @return EventHandler which will be called when the user saves changes.
     */
    private EventHandler<ActionEvent> onSaving(Scene scene) {
        return saveEventArg -> {
            try {
                boolean isValid = true;

                List<Field> fields = ReflectionHelper.getAllFields(editedEntity.getClass());
                fields.removeIf(field -> field.getAnnotation(ShowInUI.class) == null);

                for (Field field : fields) {
                    Control control = (Control) scene.lookup("#" + field.getName());
                    control.setStyle("");

                    field.setAccessible(true);

                    Object value = field.get(editedEntity);
                    if (value == null || (field.getType() == String.class && value == "")) {
                        control.setStyle("-fx-border-color: red;");
                        isValid = false;
                    }
                }

                if (isValid) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        field.set(originalEntity, field.get(editedEntity));
                    }

                    this.setUserData(originalEntity);
                    this.close();
                }

                this.sizeToScene();
            } catch (Exception ex) {
                UIHelper.showException(ex);
            }
        };
    }

    /**
     * @param type Type of enum which are used to create values of dropdown.
     * @param <T>
     * @return Returns ComboBox.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private <T> ComboBox<T> createComboBoxFor(Class<T> type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.getItems().addAll((T[]) type.getMethod("values").invoke(null));
        return comboBox;
    }
}