package com.libertex.qa.challenge.utils;

import com.libertex.qa.challenge.model.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class SessionManager {

    @Inject
    EntityManager entityManager;

    public Session getSessionByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> criteriaQuery = criteriaBuilder.createQuery(Session.class);
        Root<Session> rootEntry = criteriaQuery.from(Session.class);
        CriteriaQuery<Session> selectQuery = criteriaQuery
                .select(rootEntry)
                .where(criteriaBuilder.equal(rootEntry.get("username"), username));
        TypedQuery<Session> query = entityManager.createQuery(selectQuery);

        List<Session> sessions = query.getResultList();

        Session session = null;
        if (!sessions.isEmpty()) {
            session = sessions.get(0);
        }

        return session;
    }

    public Session getSessionBySessionId(String sessionId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> criteriaQuery = criteriaBuilder.createQuery(Session.class);
        Root<Session> rootEntry = criteriaQuery.from(Session.class);
        CriteriaQuery<Session> selectQuery = criteriaQuery
                .select(rootEntry)
                .where(criteriaBuilder.equal(rootEntry.get("sessionId"), sessionId));
        TypedQuery<Session> query = entityManager.createQuery(selectQuery);

        List<Session> sessions = query.getResultList();

        Session session = null;
        if (!sessions.isEmpty()) {
            session = sessions.get(0);
        }

        return session;
    }

    public void deleteSession(Session session) {
        entityManager.remove(session);
    }

}