/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Hildeberto Mendonça.
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
 * */
package org.cejug.yougi.event.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.event.entity.Session;
import org.cejug.yougi.knowledge.business.TopicBean;
import org.cejug.yougi.entity.EntitySupport;

/**
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@Stateless
@LocalBean
public class SessionBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private TopicBean topicBean;

    @EJB
    private SpeakerBean speakerBean;

    public Session findSession(String id) {
        if (id != null) {
            return em.find(Session.class, id);
        }
        return null;
    }

    public List<Session> findSessions(Event event) {
        return em.createQuery("select s from Session s where s.event = :event order by s.startDate, s.startTime asc")
                 .setParameter("event", event)
                 .getResultList();
    }

    public List<Session> findSessionsWithSpeakers(Event event) {
        List<Session> sessions = em.createQuery("select s from Session s where s.event = :event order by s.startDate, s.startTime asc")
                                   .setParameter("event", event)
                                   .getResultList();

        if(sessions != null) {
            for(Session session: sessions) {
                session.setSpeakers(speakerBean.findSpeakers(session));
            }
        }

        return sessions;
    }

    public void save(Session session) {
        if (EntitySupport.INSTANCE.isIdNotValid(session)) {
            session.setId(EntitySupport.INSTANCE.generateEntityId());
            em.persist(session);
        } else {
            em.merge(session);
        }

        topicBean.consolidateTopics(session.getTopics());
    }

    public void remove(String id) {
        Session session = em.find(Session.class, id);
        if (session != null) {
            em.remove(session);
        }
    }
}