
package entity.dao;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import entity.Worker;
import entity.Worker_;

@Named
public class WorkerDAOImpl
    implements WorkerDAO
{

    @Inject
    protected EntityManager entityManager;
    private final static long serialVersionUID = 1667214942531583493L;

    public WorkerDAOImpl() {
    }

    @Override
    public List<Worker> findAll() {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Worker> cq = cb.createQuery(Worker.class);
        cq.from(Worker.class);
        return getResultList(cq);
    }

    @Override
    public Worker findByPK(final int id) {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Worker> cq = cb.createQuery(Worker.class);
        Root<Worker> root = cq.from(Worker.class);
        Path<int> idPath = root.get(Worker_.id);
        Predicate idPredicate = equals(idPath, id);
        cq.where(idPredicate);
        return this.getSingleResult(cq);
    }

    protected<E >E getSingleResult(CriteriaQuery<E> criteriaQuery) {
        return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
    }

    protected<T >List<T> getResultList(CriteriaQuery<T> criteriaQuery) {
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    protected<V >Predicate equals(Path<V> path, V value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.equal(path, value);
    }

    protected Predicate between(Path<java.util.Date> path, java.util.Date start, java.util.Date end) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        java.util.Date startDate = new java.sql.Date(start.getTime());
        java.util.Date endDate = new java.sql.Date(end.getTime());
        return criteriaBuilder.and(this.isOnOrAfter(path, startDate), this.isOnOrBefore(path, endDate));
    }

    protected Predicate isBefore(Path<java.util.Date> path, java.util.Date value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.lessThan(path, new java.sql.Date(value.getTime()));
    }

    protected Predicate isAfter(Path<java.util.Date> path, java.util.Date value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.greaterThan(path, new java.sql.Date(value.getTime()));
    }

    protected Predicate isOnOrBefore(Path<java.util.Date> path, java.util.Date value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.lessThanOrEqualTo(path, new java.sql.Date(value.getTime()));
    }

    protected Predicate isOnOrAfter(Path<java.util.Date> path, java.util.Date value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.greaterThanOrEqualTo(path, new java.sql.Date(value.getTime()));
    }

    protected<N extends Comparable<N> >Predicate isGreaterThan(Path<N> path, N value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.greaterThan(path, value);
    }

    protected<N extends Comparable<N> >Predicate isLessThan(Path<N> path, N value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.lessThan(path, value);
    }

    protected<N extends Comparable<N> >Predicate isGreaterThanOrEqualTo(Path<N> path, N value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.greaterThanOrEqualTo(path, value);
    }

    protected<N extends Comparable<N> >Predicate isLessThanOrEqualTo(Path<N> path, N value) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.lessThanOrEqualTo(path, value);
    }

    protected Predicate ilike(Expression<String> x, Expression<String> pattern) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.like(criteriaBuilder.lower(x), criteriaBuilder.lower(pattern));
    }

    protected Predicate ilike(Expression<String> x, Expression<String> pattern, char escapeChar) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.like(criteriaBuilder.lower(x), criteriaBuilder.lower(pattern), escapeChar);
    }

    protected Predicate ilike(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.like(criteriaBuilder.lower(x), criteriaBuilder.lower(pattern), escapeChar);
    }

    protected Predicate ilike(Expression<String> x, String pattern) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.like(criteriaBuilder.lower(x), pattern);
    }

    protected Predicate ilike(Expression<String> x, String pattern, char escapeChar) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.like(criteriaBuilder.lower(x), pattern, escapeChar);
    }

    protected Predicate ilike(Expression<String> x, String pattern, Expression<Character> escapeChar) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.like(criteriaBuilder.lower(x), pattern, escapeChar);
    }

    protected Predicate startsWith(Expression<String> x, String value) {
        return this.ilike(x, String.format("%s%%", value));
    }

    protected Predicate endsWith(Expression<String> x, String value) {
        return this.ilike(x, String.format("%%%s", value));
    }

    protected Predicate contains(Expression<String> x, String value) {
        return this.ilike(x, String.format("%%%s%%", value));
    }

    protected Expression<String> trim(Expression<String> x) {
        CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
        return criteriaBuilder.function("ltrim", String.class, criteriaBuilder.function("rtrim", String.class, x));
    }

}
