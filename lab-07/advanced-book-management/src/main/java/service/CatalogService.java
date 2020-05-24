package service;

import model.*;
import repository.CatalogRepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Object> getMostReadAuthor() {
        try {
            Map<String, Date> dateMap = new HashMap<String, Date>();
            String queryStr = "SELECT COUNT(a) FROM Author a, Borrowing b WHERE b.book.author.id = a.id " +
                    "GROUP BY a.id";

            Query borrowingQuery = em.createQuery(queryStr);
            List<Long> borrowings = borrowingQuery.getResultList();
            Long maxBorrowsNum = borrowings.get(0);
            for ( Long i: borrowings ){
                if (i > maxBorrowsNum ) maxBorrowsNum = i;
            }

            String authorQueryStr = "SELECT a FROM Author a, Borrowing b WHERE b.book.author.id = a.id " +
                    "GROUP BY a.id HAVING COUNT(a) = :maxBorrowsNum";
            Query authorQuery = em.createQuery(authorQueryStr);
            authorQuery.setParameter("maxBorrowsNum", maxBorrowsNum);

            return authorQuery.getResultList();

        } catch (Exception e){
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getBooksByReaderIdAndDate(String readerId, Date dateFrom, Date dateTo) {
        try {
            Map<String, Date> queryParams = new HashMap<String, Date>();
            String queryStr = "SELECT DISTINCT books FROM Book books, Borrowing b WHERE b.reader.id = :readerId " +
                    "AND books.id = b.book.id";

            if ( dateFrom != null ){
                queryStr += " AND b.fromDate >= :dateFrom";
                queryParams.put("dateFrom", dateFrom);
            }

            if ( dateTo != null ){
                queryStr += " AND b.toDate <= :dateTo";
                queryParams.put("dateTo", dateTo);
            }

            Query query = em.createQuery(queryStr);
            query.setParameter("readerId", Integer.parseInt(readerId));
            query.setParameter("dateFrom", queryParams.get("dateFrom"));
            query.setParameter("dateTo", queryParams.get("dateTo"));

            return query.getResultList();

        } catch ( Exception e ) {
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getReadersByBookAndDate(String bookId, Date dateFrom, Date dateTo) {
        try {
            Map<String, Date> queryParams = new HashMap<String, Date>();
            String queryStr = "SELECT DISTINCT r FROM Reader r, Borrowing b WHERE b.reader.id = r.id " +
                    "AND b.book.id = :bookId";

            if ( dateFrom != null ){
                queryStr += " AND b.fromDate >= :dateFrom";
                queryParams.put("dateFrom", dateFrom);
            }

            if ( dateTo != null ){
                queryStr += " AND b.toDate <= :dateTo";
                queryParams.put("dateTo", dateTo);
            }

            Query query = em.createQuery(queryStr);
            query.setParameter("bookId", Integer.parseInt(bookId));
            query.setParameter("dateFrom", queryParams.get("dateFrom"));
            query.setParameter("dateTo", queryParams.get("dateTo"));

            return query.getResultList();

        } catch ( Exception e ) {
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Object> getReadersByAuthorIdAndDate(String authorId, Date dateFrom, Date dateTo) {
        try {
            Map<String, Date> queryParams = new HashMap<String, Date>();
            String queryStr = "SELECT DISTINCT r FROM Reader r, Borrowing b WHERE b.reader.id = r.id " +
                    "AND b.book.author.id = :authorId";

            if ( dateFrom != null ){
                queryStr += " AND b.fromDate >= :dateFrom";
                queryParams.put("dateFrom", dateFrom);
            }

            if ( dateTo != null ){
                queryStr += " AND b.toDate <= :dateTo";
                queryParams.put("dateTo", dateTo);
            }

            Query query = em.createQuery(queryStr);
            query.setParameter("authorId", Integer.parseInt(authorId));
            query.setParameter("dateFrom", queryParams.get("dateFrom"));
            query.setParameter("dateTo", queryParams.get("dateTo"));

            return query.getResultList();

        } catch ( Exception e ) {
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
            return null;
        }
    }
}
