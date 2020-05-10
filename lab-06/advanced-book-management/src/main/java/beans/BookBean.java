package beans;

import model.Book;
import repository.BookRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.math.BigInteger;
import java.util.List;

@ManagedBean(name = "BookBean")
@SessionScoped
public class BookBean {
    @EJB
    BookRepository bookRepository;

    private String title;
    private BigInteger ISBNNumber;
    private String authorName;
    private String authorSurname;
    private String category;
    private Integer quantity;

    private Integer selectedBookId;

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public String addBook() {
        bookRepository.addBook(this.getTitle(), this.getISBNNumber(), this.getAuthorName(), this.getAuthorSurname(),
                this.getCategory(), this.getQuantity());
        this.setEmptyValues();

        return "/books/books";
    }

    public String onBackButton () {
        this.setEmptyValues();

        return "/books/books";
    }

    public void setEmptyValues() {
        this.setTitle(null);
        this.setISBNNumber(null);
        this.setAuthorName(null);
        this.setAuthorSurname(null);
        this.setCategory(null);
        this.setQuantity(null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getISBNNumber() {
        return ISBNNumber;
    }

    public void setISBNNumber(BigInteger ISBNNumber) {
        this.ISBNNumber = ISBNNumber;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSelectedBookId() {
        return selectedBookId;
    }

    public void setSelectedBookId(Integer selectedBookId) {
        this.selectedBookId = selectedBookId;
    }
}
