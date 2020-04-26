package database;

import api.DatabaseAPI;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(DatabaseAPI.class)
public class Database implements DatabaseAPI
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

    public void updateBook(int bookID, BookEntity book) {
        try {
            BookEntity updateObject = em.find(BookEntity.class, bookID);

            if( book.getAuthorName() != null )
                updateObject.setAuthorName(book.getAuthorName());

            if( book.getAuthorSurname() != null )
                updateObject.setAuthorSurname(book.getAuthorSurname());

            if( book.getBookTitle() != null )
                updateObject.setBookTitle(book.getBookTitle());

            if( book.getIsbnNumber() != null )
                updateObject.setIsbnNumber(book.getIsbnNumber());

            if( book.getReleaseDate() != null )
                updateObject.setReleaseDate(book.getReleaseDate());

            if( book.getPrice() != null )
                updateObject.setPrice(book.getPrice());

            em.persist(updateObject);
        } catch (Exception e) {
            System.err.println("An error occurred during updating a book object. Id = "+ bookID + "\n" + e);
        }
    }
}
