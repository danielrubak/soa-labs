package beans;

import model.Book;
import model.Catalog;
import model.Reader;
import repository.CatalogRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // TODO: query creator should be move to repository and service classes
    public void getReadersByAuthorAndDate(String author, Date dateFrom, Date dateTo){
        try {
            Map<String, Date> queryParams = new HashMap<String, Date>();
            String queryStr = "SELECT DISTINCT r FROM Reader r, Borrowing b WHERE b.reader.id = r.id AND b.book.author.id = :author";

            // update query only if date is selected
            if ( dateFrom != null ){
                queryStr +=" AND b.fromDate >= :dateFrom";
                queryParams.put("dateFrom", dateFrom);
            }

            // update query only if date is selected
            // TODO: should be compared with dateFrom
            if ( dateTo != null ){
                queryStr +=" AND b.toDate <= :dateTo";
                queryParams.put("dateTo", dateTo);
            }

            Query query = em.createQuery(queryStr);
            query.setParameter("author", Integer.parseInt(author));
            query.setParameter("dateFrom", queryParams.get("dateFrom"));
            query.setParameter("dateTo", queryParams.get("dateTo"));

            List<Reader> readers = query.getResultList();
            System.out.println(readers.toString());
            setLastQueryResult(readers.toString());

        } catch ( Exception e ){
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
}
