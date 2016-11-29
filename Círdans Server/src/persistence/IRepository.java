package persistence;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;

public interface IRepository<T, ID extends Serializable> {
    public void save(T entity);
    public void update(T entity);
    public void delete(T entity);
    public T findById(ID id);
}
