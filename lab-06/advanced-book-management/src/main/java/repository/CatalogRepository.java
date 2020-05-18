package repository;

import model.Catalog;

import java.util.Date;
import java.util.List;

public interface CatalogRepository {
    List<Catalog> getCatalog();
    void updateCatalog(int id, int quantity, int available);
    List<Object> getMostReadAuthor();
    List<Object> getBooksByReaderIdAndDate(String readerId, Date dateFrom, Date dateTo);
    List<Object> getReadersByBookAndDate(String bookId, Date dateFrom, Date dateTo);
    List<Object> getReadersByAuthorIdAndDate(String authorId, Date dateFrom, Date dateTo);
}
