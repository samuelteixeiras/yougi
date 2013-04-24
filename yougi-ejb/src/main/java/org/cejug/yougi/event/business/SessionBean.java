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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
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
        Session session = null;
        if (id != null) {
            session = em.find(Session.class, id);
            if(session != null) {
                session.setSpeakers(speakerBean.findSpeakers(session));
            }
        }
        return session;
    }

    public List<Session> findSessions(Event event) {
        return em.createQuery("select s from Session s where s.event = :event order by s.startDate, s.startTime asc")
                 .setParameter("event", event)
                 .getResultList();
    }

    /**
     * Returns all sessions with their speakers, which are related to the event.
     * A session may contain more than one speaker.
     * @param event The event with which the sessions are related.
     */
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

    public Session findPreviousSession(Session currentSession) {
        List<Session> foundSessions = em.createQuery("select s from Session s where s.event = :event and s.startDate <= :startDate and s.startTime < :startTime order by s.startDate, s.startTime desc")
                                        .setParameter("event", currentSession.getEvent())
                                        .setParameter("startDate", currentSession.getStartDate())
                                        .setParameter("startTime", currentSession.getStartTime())
                                        .getResultList();

        if(foundSessions != null && !foundSessions.isEmpty()) {
            return foundSessions.get(0);
        }

        return null;
    }

    public Session findNextSession(Session currentSession) {
        List<Session> foundSessions = em.createQuery("select s from Session s where s.event = :event and s.startDate >= :startDate and s.startTime > :startTime order by s.startDate, s.startTime asc")
                .setParameter("event", currentSession.getEvent())
                .setParameter("startDate", currentSession.getStartDate())
                .setParameter("startTime", currentSession.getStartTime())
                .getResultList();

        if(foundSessions != null && !foundSessions.isEmpty()) {
            return foundSessions.get(0);
        }

        return null;
    }

    public List<Session> findSessionsByTopic(String topic) {
        return em.createQuery("select s from Session s where s.topics like '%"+ topic +"%'").getResultList();
    }

    public List<Session> findRelatedSessions(Session session) {
        String strTopics = session.getTopics();
        if(strTopics == null) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(strTopics, ",");
        Set<Session> relatedSessions = new HashSet<>();
        String topic;
        while(st.hasMoreTokens()) {
            topic = st.nextToken().trim();
            relatedSessions.addAll(findSessionsByTopic(topic));
        }
        relatedSessions.remove(session);
        return new ArrayList<>(relatedSessions);
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