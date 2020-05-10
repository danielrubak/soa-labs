package service;

import model.BookCategory;
import repository.BookCategoryRepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(BookCategoryRepository.class)
public class BookCategoryService implements BookCategoryRepository {
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    @Override
    public List<BookCategory> getAllCategories() {
        try {
            Query q = em.createQuery("FROM Category", BookCategory.class);
            return q.getResultList();
        } catch(Exception e) {
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    @Override
    public void addCategory(BookCategory category) {
        try {
            em.persist(category);
        } catch (Exception e) {
            System.err.println("An error occurred during addition of a category: " + category + "\n" + e);
        }
    }

    @Override
    public void updateCategory(int id, BookCategory category) {
        try {
            BookCategory updateObject = em.find(BookCategory.class, id);

            if( category.getName() != null )
                updateObject.setName(category.getName());

            em.persist(updateObject);
        } catch (Exception e) {
            System.err.println("An error occurred during updating a category object. Id = "+ id + "\n"
                    + e);
        }
    }

    @Override
    public void deleteCategory(int id) {
        try {
            BookCategory category = em.find(BookCategory.class, id);
            em.remove(category);
        } catch (Exception e) {
            System.err.println("An error occurred during category deletion. Id: " + id + "\n" + e);
        }
    }
}
