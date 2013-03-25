/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Ceara Java User Group - CEJUG.
 *
 * This application is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This application is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * There is a full copy of the GNU Lesser General Public License along with
 * this library. Look for the file license.txt at the root level. If you do not
 * find it, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA.
 *
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
 * EJB class CommonBean.
 * 
 * @author Helio Frota - http://www.heliofrota.com
 */
@Stateless
@LocalBean
public class CommonBean {

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
