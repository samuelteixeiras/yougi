/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cejug.yougi.business;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * EJB class CommonBsn.
 * @author helio frota
 */
@Stateless
@LocalBean
public class CommonBsn {

    @PersistenceContext()
    private EntityManager em;

    /**
     * Returns a list of entity passed by parameter.
     * To reduce similar methods.
     */
    public < T > List < T > findAllOrderedBy(Class< T > entityClass, String orderBy, boolean asc) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery< T > criteria = builder.createQuery(entityClass);
        Root< T > entityRoot = criteria.from(entityClass);
        criteria.select(entityRoot);
        Order order;
        if (asc) {
            order = builder.asc(entityRoot.get(orderBy));
        } else {
            order = builder.desc(entityRoot.get(orderBy));
        }
        criteria.orderBy(order);
        return em.createQuery(criteria).getResultList();
    }
}
