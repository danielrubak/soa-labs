package beans;

import model.Book;
import model.Catalog;
import repository.CatalogRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "CatalogBean")
@SessionScoped
public class CatalogBean {
    @EJB
    CatalogRepository catalogRepository;

    private int id;
    private int quantity;
    private int available;
    private Book book;

    private Catalog EditedCatalog;
    private boolean editMode = false;

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
}
