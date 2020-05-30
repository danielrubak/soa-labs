package service;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Persistence;

@ApplicationScoped
class ApplicationEntityManager {
    // create single entity manager for every repository
    private javax.persistence.EntityManager em;

    ApplicationEntityManager() {
        em = Persistence.createEntityManagerFactory("rest-persistence-unit").createEntityManager();
    }

    javax.persistence.EntityManager get() {
        return em;
    }
}
