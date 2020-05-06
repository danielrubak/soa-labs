package database;

import api.BookAPI;
import model.Book;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(BookAPI.class)
public class BookDatabase implements BookAPI
{
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;


    public List<Book> getAllBooks()
    {
        try {
            Query q = em.createQuery("FROM BookEntity ", Book.class);
            return (List<Book>) q.getResultList();
        }
        catch(Exception e) {
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    public void addBook(Book book)
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
            Book book = em.find(Book.class,toDeleteId);
            em.remove(book);
        }catch (Exception e){
            System.err.println("An error occurred during book deletion. Id: "+toDeleteId+"\n"+e);
        }
    }

    public void updateBook(int bookID, Book book) {
        try {
            Book updateObject = em.find(Book.class, bookID);

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
