package Repository;

import Base.Model.Jacket;
import Base.Repository.IJacketRepository;

/**
 * Created by Mateusz on 07/01/2016.
 */
public class JacketRepository extends RepositoryBase<Jacket> implements IJacketRepository {
    public JacketRepository(String filePath) {
        super(Jacket.class, filePath);
    }
}