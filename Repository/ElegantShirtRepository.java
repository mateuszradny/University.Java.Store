package Repository;

import Base.Model.ElegantShirt;
import Base.Repository.IElegantShirtRepository;

/**
 * Created by Mateusz on 07/01/2016.
 */
public class ElegantShirtRepository extends RepositoryBase<ElegantShirt> implements IElegantShirtRepository {
    public ElegantShirtRepository(String filePath) {
        super(ElegantShirt.class, filePath);
    }
}