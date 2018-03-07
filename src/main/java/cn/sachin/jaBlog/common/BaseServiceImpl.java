package cn.sachin.jaBlog.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Transactional
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    //private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

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
        getDao().deleteInBatch(getDao().findAll(ids));
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
        /*T t = null;
        try {
            t  = getDao().findById(id).get();
        } catch (Exception e) {
            log.error("当前ID:{},查询人员不存在", id);
            e.printStackTrace();
        }
        return t;*/
    }

    @Override
    public List<T> getAll() {
        return getDao().findAll();
    }
}
