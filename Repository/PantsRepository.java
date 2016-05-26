package Repository;

import Base.Model.Pants;
import Base.Repository.IPantsRepository;

/**
 * Created by Mateusz on 07/01/2016.
 */
public class PantsRepository extends RepositoryBase<Pants> implements IPantsRepository {
    public PantsRepository(String filePath) {
        super(Pants.class, filePath);
    }
}