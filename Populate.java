import Base.IContext;
import Base.Model.*;
import Base.Repository.IRepository;
import Repository.ModelContext;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Created by Mateusz on 17/01/2016.
 */
public class Populate {
    public static void main(String[] args) {
        IContext context = new ModelContext();

        IRepository<Boots> bootsRepository = context.getRepositoryFor(Boots.class);
        for (int i = 0; i < 5; i++) {
            Boots boots = new Boots();
            boots.setName("Buty");
            boots.setPrice(new BigDecimal(199.90));
            boots.setSex(Sex.WOMAN);
            boots.setColor(Color.BLACK);
            boots.setBrand("Adidas");
            boots.setHeels(false);
            boots.setSize(40);

            bootsRepository.add(boots);
        }

        IRepository<ElegantShirt> elegantShirtRepository = context.getRepositoryFor(ElegantShirt.class);
        for (int i = 0; i < 5; i++) {
            ElegantShirt shirt = new ElegantShirt();
            shirt.setName("Koszule");
            shirt.setPrice(new BigDecimal(199.99));
            shirt.setSex(Sex.WOMAN);
            shirt.setColor(Color.BLACK);
            shirt.setBrand("Adidas");
            shirt.setTie(true);
            shirt.setCollarSize(40);
            shirt.setMaterial("Materiał");

            elegantShirtRepository.add(shirt);
        }

        IRepository<Jacket> jacketRepository = context.getRepositoryFor(Jacket.class);
        for (int i = 0; i < 5; i++) {
            Jacket jacket = new Jacket();
            jacket.setName("Jacket");
            jacket.setPrice(new BigDecimal(199.99));
            jacket.setSex(Sex.WOMAN);
            jacket.setColor(Color.BLACK);
            jacket.setBrand("Adidas");
            jacket.setSize(Size.M);
            jacket.setJacketType(JacketType.SUMMER_JACKET);
            jacket.setLockType(LockType.BUTTONS);

            jacketRepository.add(jacket);
        }

        IRepository<Pants> pantsRepository = context.getRepositoryFor(Pants.class);
        for (int i = 0; i < 5; i++) {
            Pants pants = new Pants();
            pants.setName("Spodnie");
            pants.setPrice(new BigDecimal(199.99));
            pants.setSex(Sex.WOMAN);
            pants.setColor(Color.BLACK);
            pants.setBrand("Adidas");
            pants.setLengthSize(44);
            pants.setWidthSize(35);
            pants.setMaterial("Materiał");

            pantsRepository.add(pants);
        }

        IRepository<TShirt> tShirtIRepository = context.getRepositoryFor(TShirt.class);
        for (int i = 0; i < 5; i++) {
            TShirt tShirt = new TShirt();
            tShirt.setName("Koszulki");
            tShirt.setPrice(new BigDecimal(199.99));
            tShirt.setSex(Sex.WOMAN);
            tShirt.setColor(Color.BLACK);
            tShirt.setBrand("Adidas");
            tShirt.setSize(Size.L);
            tShirt.setMaterial("Materiał");

            tShirtIRepository.add(tShirt);
        }
    }
}
