package service;

import repository.AuthorRepository;
import model.Author;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(AuthorRepository.class)
public class AuthorService implements AuthorRepository {
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    @Override
    public List<Author> getAllAuthors() {
        try {
            Query q = em.createQuery("FROM Author ", Author.class);
            return (List<Author>) q.getResultList();
        }
        catch(Exception e) {
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    @Override
    public void addAuthor(Author author) {
        try {
            em.persist(author);
        }
        catch (Exception e) {
            System.err.println("An error occurred during addition of a book: " + author + "\n" + e);
        }
    }

    @Override
    public void deleteAuthor(int id) {
        try {
            Author author = em.find(Author.class, id);
            em.remove(author);
        } catch (Exception e){
            System.err.println("An error occurred during book deletion. Id: " + id + "\n" + e);
        }
    }

    @Override
    public void updateAuthor(int id, Author author) {
        try {
            Author updateObject = em.find(Author.class, id);

            if( author.getName() != null )
                updateObject.setName(author.getName());

            if( author.getSurname() != null )
                updateObject.setSurname(author.getSurname());

            em.persist(updateObject);
        } catch (Exception e) {
            System.err.println("An error occurred during updating a book object. Id = "+ id + "\n" + e);
        }
    }
}
