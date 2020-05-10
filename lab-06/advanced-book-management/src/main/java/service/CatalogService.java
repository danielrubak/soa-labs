package service;

import model.BookCategory;
import model.Catalog;
import repository.CatalogRepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(CatalogRepository.class)
public class CatalogService implements CatalogRepository {
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    @Override
    public List<Catalog> getCatalog() {
        try {
            Query q = em.createQuery("FROM Catalog ", Catalog.class);
            return (List<Catalog>) q.getResultList();
        } catch(Exception e) {
            System.err.println("An error occurred during selecting catalog: " + e);
        }
        return null;
    }

    @Override
    public void updateCatalog(int id, int quantity, int available) {
        try {
            Catalog updateObject = em.find(Catalog.class, id);

            updateObject.setQuantity(quantity);
            updateObject.setAvailable(available);

            em.persist(updateObject);
        } catch (Exception e) {
            System.err.println("An error occurred during updating a catalog object. Id = "+ id + "\n"
                    + e);
        }
    }
}
