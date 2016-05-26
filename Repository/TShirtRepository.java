package Repository;

import Base.Model.TShirt;
import Base.Repository.ITShirtRepository;

/**
 * Created by Mateusz on 07/01/2016.
 */
public class TShirtRepository extends RepositoryBase<TShirt> implements ITShirtRepository {
    public TShirtRepository(String filePath) {
        super(TShirt.class, filePath);
    }
}
