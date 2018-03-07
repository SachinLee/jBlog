package cn.sachin.jaBlog.common;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, ID extends Serializable> {

    public void save(T entity);

    public void saveBatch(Iterable<T> entities);

    public void delete(ID id);

    public void delete(T entity);

    public void deleteAll(Iterable<ID> ids);

    public void deleteBatch(Iterable<T> entities);

    public void update(T entity);

    public void updateBatch(Iterable<T> entities);

    public T get(ID id);

    public List<T> getAll();

}
