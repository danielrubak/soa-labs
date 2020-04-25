package database;

import api.DataBaseAPI;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(DataBaseAPI.class)
public class Database implements DataBaseAPI
{
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;


    public List<BookEntity> getAllBooks()
    {
        try {
            Query q = em.createQuery("FROM BookEntity ", BookEntity.class);
            return (List<BookEntity>) q.getResultList();
        }
        catch(Exception e) {
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    public void addBook(BookEntity book)
    {
        try {
            // transaction-type="JTA" in persistance.xml handles transaction
            em.persist(book);
        }
        catch (Exception e) {
            System.err.println("An error occurred during addition of a book: "+book+"\n"+ e);
        }
    }

    public void deleteBookById(int toDeleteId) {
        try {
            // transaction-type="JTA" in persistance.xml handles transaction
            BookEntity bookEntity = em.find(BookEntity.class,toDeleteId);
            em.remove(bookEntity);
        }catch (Exception e){
            System.err.println("An error occurred during book deletion. Id: "+toDeleteId+"\n"+e);
        }
    }

    public void updateBook(int toUpdateId, BookEntity schema) {
        try {
            // transaction-type="JTA" in persistance.xml handles transaction
            BookEntity updateObject = em.find(BookEntity.class,toUpdateId);
            if(schema.getAuthorName()!=null) updateObject.setAuthorName(schema.getAuthorName());
            if(schema.getAuthorSurname()!=null) updateObject.setAuthorSurname(schema.getAuthorSurname());
            if(schema.getBookTitle()!=null) updateObject.setBookTitle(schema.getBookTitle());
            if(schema.getIsbnNumber()!=null) updateObject.setIsbnNumber(schema.getIsbnNumber());
            if(schema.getReleaseDate()!=null) updateObject.setReleaseDate(schema.getReleaseDate());
            if(schema.getPrice()!=null) updateObject.setPrice(schema.getPrice());
            em.persist(updateObject);
        }catch (Exception e)
        {
            System.err.println("An error occurred during updating a book object. Id = "+toUpdateId+"\n"+e);
        }
    }
}
