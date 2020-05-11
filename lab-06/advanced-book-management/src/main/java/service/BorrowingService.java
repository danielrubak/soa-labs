package service;

import model.*;
import repository.BorrowingRepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
@Local(BorrowingRepository.class)
public class BorrowingService implements BorrowingRepository {
    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    @Override
    public List<Borrowing> getAllBookBorrowing() {
        try {
            Query q = em.createQuery("FROM Borrowing", Borrowing.class);
            List<Borrowing> borrowings = q.getResultList();
            return borrowings;
        }
        catch (Exception e){
            System.err.println("An error occurred during selecting entities: " + e);
        }
        return null;
    }

    public void borrowBook(int readerId, int bookId) {
        try {
            Calendar today = Calendar.getInstance();
            Calendar returnDate = (Calendar) today.clone();
            returnDate.add(Calendar.MONTH, 1);

            String catalogQueryStr = "SELECT c FROM Catalog c, Book b WHERE b.id = :id AND b.catalog.id = c.id";
            Query catalogQuery = em.createQuery(catalogQueryStr);
            catalogQuery.setParameter("id", bookId);
            List<Catalog> catalogs = catalogQuery.getResultList();

            String bookQueryStr = "SELECT b FROM Book b WHERE b.id = :id";
            Query bookQuery = em.createQuery(bookQueryStr);
            bookQuery.setParameter("id", bookId);
            Book book = (Book) bookQuery.getSingleResult();

            String readerQueryStr = "SELECT r FROM Reader r WHERE r.id= :id";
            Query readerQuery = em.createQuery(readerQueryStr);
            readerQuery.setParameter("id", readerId);
            Reader reader = (Reader) readerQuery.getSingleResult();

            if ( !catalogs.isEmpty()){
                if ( catalogs.get(0).getAvailable()>0 ){
                    Borrowing borrowing = new Borrowing(today.getTime(), returnDate.getTime());
                    borrowing.setBook(book);
                    borrowing.setReader(reader);
                    borrowing.setCatalog(catalogs.get(0));
                    borrowing.getCatalog().borrowBook();
                    em.persist(borrowing);
                }
            }
        }
        catch (Exception e){
            System.err.println("An error occurred during book borrowing. Book Id: " + bookId + "\n" +
                    ", Reader ID: " + readerId + "msg: " + e);
        }
    }

    @Override
    public void returnBook(int borrowingId) {
        try {
            Borrowing borrowing = em.find(Borrowing.class, borrowingId);

            borrowing.getCatalog().returnBook();
            borrowing.setToDate(Calendar.getInstance().getTime());

            em.persist(borrowing);
        } catch (Exception e) {
            System.err.println("An error occurred during book returning. Borrowing id = "+ borrowingId + "\n" + e);
        }
    }
}
