package service;

import repository.CategoryRepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(CategoryRepository.class)
public class CategoryService implements CategoryRepository {
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    @Override
    public List<Category> getAllCategories() {
        try {
            Query q = em.createQuery("FROM Category", Category.class);
            return q.getResultList();
        } catch(Exception e) {
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    @Override
    public void addCategory(Category category) {
        try {
            em.persist(category);
        } catch (Exception e) {
            System.err.println("An error occurred during addition of a category: " + category + "\n" + e);
        }
    }

    @Override
    public void updateCategory(int id, Category category) {
        try {
            Category updateObject = em.find(Category.class, id);

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
            Category category = em.find(Category.class, id);
            em.remove(category);
        } catch (Exception e) {
            System.err.println("An error occurred during category deletion. Id: " + id + "\n" + e);
        }
    }
}
