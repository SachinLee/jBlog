package cn.sachin.jaBlog.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    public abstract JpaRepository<T, ID> getDao();

    @Override
    public void save(T entity) {
        getDao().save(entity);
    }

    @Override
    public void saveBatch(Iterable<T> entities) {
        getDao().save(entities);
    }

    @Override
    public void delete(ID id) {
        getDao().delete(id);
    }

    @Override
    public void delete(T entity) {
        getDao().delete(entity);
    }

    @Override
    public void deleteAll(Iterable<ID> ids) {
        getDao().delete(getDao().findAll(ids));
    }

    @Override
    public void deleteBatch(Iterable<T> entities) {
        getDao().deleteInBatch(entities);
    }

    @Override
    public void update(T entity) {
        getDao().saveAndFlush(entity);
    }

    @Override
    public void updateBatch(Iterable<T> entities) {
        getDao().save(entities);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public T get(ID id) {
        return getDao().findOne(id);
    }

    @Override
    public Iterable<T> getAll() {
        return getDao().findAll();
    }
}
