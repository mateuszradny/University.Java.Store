package Repository;

import Base.Model.Boots;
import Base.Repository.IBootsRepository;

/**
 * Created by Mateusz on 06/01/2016.
 */
public class BootsRepository extends RepositoryBase<Boots> implements IBootsRepository {
    public BootsRepository(String path) {
        super(Boots.class, path);
    }
}