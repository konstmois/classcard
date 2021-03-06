package ru.classcard.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.classcard.model.StudentClass;
import ru.classcard.model.Target;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

public class TargetDAOImpl extends AbstractEntityDAOImpl implements TargetDAO {

    @Override
    @Transactional
    public List<Target> findBy(StudentClass clazz) {
        return getSession().createCriteria(Target.class).add(eq("studentClass", clazz)).list();
    }

    @Override
    @Transactional
    public List<Target> findActiveBy(StudentClass clazz) {
        return getSession().createCriteria(Target.class)
                .add(eq("studentClass", clazz))
                .add(eq("active", true))
                .list();
    }

    @Override
    @Transactional
    public <T> void save(T entity) {
        super.save(entity);
    }
}
