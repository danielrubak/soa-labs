import api.DatabaseAPI;
import database.BookEntity;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@ManagedBean(name = "BookBean")
@SessionScoped
public class BookBean
{
    @EJB
    DatabaseAPI dataBase;

    String authorSurname;
    String authorName;
    String bookTitle;
    String isbnNumber;
    Integer releaseDate;
    BigDecimal price;
    Integer selectedBookId;


    public String addBook()
    {
        BookEntity bookEntity = new BookEntity(this.authorName,this.authorSurname,this.bookTitle,
                this.isbnNumber,this.releaseDate,this.price);
        dataBase.addBook(bookEntity);
        this.setEmptyValues();

        return "BOOK_ADDED";
    }

    public String deleteBook()
    {
        System.out.println("BOOK ID TO REMOVE: " + this.getSelectedBookId());
        dataBase.deleteBookById(this.getSelectedBookId());
        this.setEmptyValues();

        return "BOOK_DELETED";
    }

    public String updateBook()
    {
        BookEntity bookEntity = new BookEntity(this.getAuthorName(), this.getAuthorSurname(), this.getBookTitle(),
                this.getIsbnNumber(), this.getReleaseDate(), this.getPrice());
        dataBase.updateBook(this.getSelectedBookId(), bookEntity);
        this.setEmptyValues();

        return "BOOK_UPDATED";
    }

    public List<BookEntity> getAllBooks()
    {
        return dataBase.getAllBooks();
    }

    public Map<String, Integer> getBooksMap() {
        Map<String, Integer> booksMap = new LinkedHashMap<>();

        String label = "";
        List <BookEntity> books = dataBase.getAllBooks();
        for (BookEntity be : books) {
            label = be.getAuthorName() + " " + be.getAuthorSurname() + ": " + be.getBookTitle();
            booksMap.put(label, be.getId());
        }

        return booksMap;
    }

    public void onBookSelection (AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<BookEntity> books = dataBase.getAllBooks();

        if ( this.getSelectedBookId() == null ) {
            this.setEmptyValues();
        } else {
            for (BookEntity book : books) {
                if ( book.getId() == this.getSelectedBookId()) {
                    this.authorName = book.getAuthorName();
                    this.authorSurname = book.getAuthorSurname();
                    this.bookTitle = book.getBookTitle();
                    this.isbnNumber = book.getIsbnNumber();
                    this.releaseDate = book.getReleaseDate();
                    this.price = book.getPrice();
                }
            }
        }
    }

    public String onBackButton () {
        this.setEmptyValues();
        return "ON_BACK_CALLBACK";
    }

    public void setEmptyValues () {
        this.authorName = null;
        this.authorSurname = null;
        this.bookTitle = null;
        this.isbnNumber = null;
        this.releaseDate = null;
        this.price = null;
        this.setSelectedBookId(null);
    }

    // Getters and Setters

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public Integer getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSelectedBookId() {
        return selectedBookId;
    }

    public void setSelectedBookId(Integer selectedBookId) {
        this.selectedBookId = selectedBookId;
    }
}
