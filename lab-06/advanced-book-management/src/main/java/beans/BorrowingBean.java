package beans;

import model.Book;
import model.Borrowing;
import model.Catalog;
import model.Reader;
import repository.BorrowingRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "BorrowingBean")
@SessionScoped
public class BorrowingBean {
    @EJB
    BorrowingRepository borrowingRepository;

    private Book book;
    private Reader reader;
    private Catalog catalog;
    private Date fromDate;
    private Date toDate;

    private Integer selectedReaderId;
    private Integer selectedBookId;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.getAllBookBorrowing();
    }

    public String borrowBook(){
        borrowingRepository.borrowBook(this.getSelectedReaderId(), this.getSelectedBookId());

        return "/borrowing/borrowing";
    }

    public String onBackButton () {
        this.setEmptyValues();

        return "/borrowing/borrowing";
    }

    public void setEmptyValues () {
        this.setSelectedBookId(null);
        this.setSelectedReaderId(null);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getSelectedReaderId() {
        return selectedReaderId;
    }

    public void setSelectedReaderId(Integer selectedReaderId) {
        this.selectedReaderId = selectedReaderId;
    }

    public Integer getSelectedBookId() {
        return selectedBookId;
    }

    public void setSelectedBookId(Integer selectedBookId) {
        this.selectedBookId = selectedBookId;
    }
}
