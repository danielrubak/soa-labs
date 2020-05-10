package service;

import repository.ReaderRepository;
import model.Reader;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(ReaderRepository.class)
public class ReaderService implements ReaderRepository {
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    public List<Reader> getAllReaders()
    {
        try {
            Query q = em.createQuery("FROM Reader ", Reader.class);
            return (List<Reader>) q.getResultList();
        }
        catch(Exception e) {
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    @Override
    public void addReader(Reader reader) {
        try {
            em.persist(reader);
        }
        catch (Exception e) {
            System.err.println("An error occurred during addition of a book: " + reader + "\n" + e);
        }
    }

    @Override
    public void deleteReader(int id) {
        try {
            Reader reader = em.find(Reader.class, id);
            em.remove(reader);
        } catch (Exception e){
            System.err.println("An error occurred during book deletion. Id: " + id + "\n" + e);
        }
    }

    @Override
    public void updateReader(int id, Reader reader) {
        try {
            Reader updateObject = em.find(Reader.class, id);

            if( reader.getName() != null )
                updateObject.setName(reader.getName());

            if( reader.getSurname() != null )
                updateObject.setSurname(reader.getSurname());

            em.persist(updateObject);
        } catch (Exception e) {
            System.err.println("An error occurred during updating a book object. Id = "+ id + "\n" + e);
        }
    }
}
