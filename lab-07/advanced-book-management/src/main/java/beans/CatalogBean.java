package beans;

import model.Book;
import model.Catalog;
import repository.CatalogRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "CatalogBean")
@SessionScoped
public class CatalogBean {
    @EJB
    CatalogRepository catalogRepository;

    @PersistenceContext(name = "book-persistence-unit")
    private EntityManager em;

    private int id;
    private int quantity;
    private int available;
    private Book book;

    private Catalog EditedCatalog;
    private boolean editMode = false;

    // this variable stores result of last executed query
    private String lastQueryResult;

    public List<Catalog> getCatalog() {
        return catalogRepository.getCatalog();
    }

    public void setEditMode(Catalog catalog) {
        this.setEditedCatalog(catalog);
        this.setEditMode(true);
    }

    public void editCatalog(int newQuantity){
        Catalog editedCatalog = this.getEditedCatalog();
        int originalQuantity = editedCatalog.getQuantity();
        int originalAvailable = editedCatalog.getAvailable();

        int quantityDiff = newQuantity - originalQuantity;
        editedCatalog.setQuantity(newQuantity);
        int newAvailable = originalAvailable + quantityDiff;
        editedCatalog.setAvailable(newAvailable);

        catalogRepository.updateCatalog(editedCatalog.getId(), newQuantity, newAvailable);
        this.setEditMode(false);
    }

    public void cancelCallback() {
        this.setEditedCatalog(null);
        this.setEditMode(false);
    }

    public void getReadersByAuthorAndDate(String authorId, Date dateFrom, Date dateTo) {
        try {
            List<Object> readers = catalogRepository.getReadersByAuthorIdAndDate(authorId, dateFrom, dateTo);

            String resultStr = this.objList2String(readers);
            setLastQueryResult(resultStr);

        } catch ( Exception e ) {
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
        }
    }

    public void getReadersByBookAndDate(String bookId, Date dateFrom, Date dateTo) {
        try {
            List<Object> readers = catalogRepository.getReadersByBookAndDate(bookId, dateFrom, dateTo);

            String resultStr = this.objList2String(readers);
            setLastQueryResult(resultStr);

        } catch ( Exception e ) {
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
        }
    }

    public void getBooksByReaderIdAndDate(String readerId, Date dateFrom, Date dateTo) {
        try {
            List<Object> books = catalogRepository.getBooksByReaderIdAndDate(readerId, dateFrom, dateTo);

            String resultStr = this.objList2String(books);
            setLastQueryResult(resultStr);

        } catch ( Exception e ) {
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
        }
    }

    public void getMostReadAuthor() {
        try {
            List<Object> authors = catalogRepository.getMostReadAuthor();

            String resultStr = this.objList2String(authors);
            setLastQueryResult(resultStr);

        } catch (Exception e){
            System.err.println("An error occurred during filtering.\n" + e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Catalog getEditedCatalog() {
        return EditedCatalog;
    }

    public void setEditedCatalog(Catalog editedCatalog) {
        EditedCatalog = editedCatalog;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public String getLastQueryResult() {
        return lastQueryResult;
    }

    public void setLastQueryResult(String lastQueryResult) {
        this.lastQueryResult = lastQueryResult;
    }

    public String objList2String(List<Object> objectList) {
        int i = 1;

        String resultStr = "";
        if ( objectList.size() == 0 ) {
            resultStr = "No results found matching your search criteria";
        } else {
            for (Object o : objectList) {
                resultStr += o.toString();
                if ( i < objectList.size() )
                    resultStr += ", ";
                i += 1;
            }
        }

        return resultStr;
    }
}
