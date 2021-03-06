package ru.classcard.dao;

import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;
import ru.classcard.model.Card;
import ru.classcard.model.CardOperation;
import ru.classcard.model.Target;

import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.isNotNull;
import static ru.classcard.model.OperationType.EXPENSE;
import static ru.classcard.model.OperationType.INCOME;

public class CardOperationDAOImpl extends AbstractEntityDAOImpl implements CardOperationDAO {

    private static final String CARD_FIELD_NAME = "card";

    @Override
    @Transactional
    public List<CardOperation> getOperationsBy(Card card, int first, int pageSize, String sortField, javax.swing.SortOrder sortOrder, Map<String, Object> filters) {
        filters.put(CARD_FIELD_NAME, card);
        return buildCriteria(CardOperation.class, first, pageSize, sortField, sortOrder, filters).list();
    }

    @Override
    @Transactional
    public int getOperationsCountBy(Card card, Map<String, Object> filters) {
        filters.put(CARD_FIELD_NAME, card);
        return ((Long) buildCriteria(CardOperation.class, -1, -1, null, null, filters).setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    @Override
    @Transactional
    public List<CardOperation> findIncomesBy(Target target) {
        return getSession().createCriteria(CardOperation.class)
                .add(eq("target", target))
                .add(eq("type", INCOME))
                .add(isNotNull("student"))
                .add(eq("rest", FALSE))
                .list();
    }

    @Override
    @Transactional
    public List<CardOperation> findRestIncomesBy(Target target) {
        return getSession().createCriteria(CardOperation.class)
                .add(eq("target", target))
                .add(eq("type", INCOME))
                .add(eq("rest", TRUE))
                .list();
    }



    @Override
    @Transactional
    public List<CardOperation> findExpensesBy(Target target) {
        return getSession().createCriteria(CardOperation.class)
                .add(eq("target", target))
                .add(eq("type", EXPENSE))
                .list();
    }

    @Override
    @Transactional
    public <T> void save(T entity) {
        super.save(entity);
    }
}
