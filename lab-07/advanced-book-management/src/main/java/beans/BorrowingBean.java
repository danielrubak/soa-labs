package beans;

import model.Book;
import model.Borrowing;
import model.Catalog;
import model.Reader;
import repository.BorrowingRepository;
import repository.ReaderRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean(name = "BorrowingBean")
@SessionScoped
public class BorrowingBean {
    @EJB
    BorrowingRepository borrowingRepository;

    @EJB
    ReaderRepository readerRepository;

    private Book book;
    private Reader reader;
    private Catalog catalog;
    private Date fromDate;
    private Date toDate;

    // view logic fields
    private Map<Integer, Boolean> canReturnMap = new LinkedHashMap<>();
    private boolean showReturnBookField;
    private Integer selectedReaderId;
    private Integer selectedBookId;

    public List<Borrowing> getAllBorrowings() {
        Date today = Calendar.getInstance().getTime();
        Map<Integer, Boolean> canReturnMap = new LinkedHashMap<>();

        List<Borrowing> borrowings = borrowingRepository.getAllBookBorrowing();
        for (Borrowing borrowing: borrowings) {
            Date returnDate = borrowing.getToDate();
            if ( returnDate.compareTo(today) >= 0) {
                canReturnMap.put(borrowing.getId(), true);
            } else {
                canReturnMap.put(borrowing.getId(), false);
            }
        }

        this.setCanReturnMap(canReturnMap);
        this.setShowReturnBookField(canReturnMap.containsValue(true));

        return borrowings;
    }

    public String borrowBook() {
        borrowingRepository.borrowBook(this.getSelectedReaderId(), this.getSelectedBookId());
        this.setEmptyValues();

        return "/borrowing/borrowing";
    }

    public void returnBook(int id) {
        borrowingRepository.returnBook(id);
    }

    public String onBackButton () {
        this.setEmptyValues();

        return "/borrowing/borrowing";
    }

    public void setEmptyValues () {
        this.setSelectedBookId(null);
        this.setSelectedReaderId(null);
    }

    public String getMessage(int mode) {
        switch (mode) {
            case 1:
//                return "Reader " + getReader().getName() + " " + getReader().getSurname() + " borrowed "
//                        + getBook().getTitle();
                return "Reader " + this.getSelectedReaderId() + " borrowed " + this.getSelectedBookId();
            case 2:
//                return "Reader " + getReader().getName() + " " + getReader().getSurname() + " returned "
//                        + getBook().getTitle();
                return "Reader " + this.getSelectedReaderId() + " returned " + this.getSelectedBookId();
            default:
                return null;
        }
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

    public Map<Integer, Boolean> getCanReturnMap() {
        return canReturnMap;
    }

    public void setCanReturnMap(Map<Integer, Boolean> canReturnMap) {
        this.canReturnMap = canReturnMap;
    }

    public boolean isShowReturnBookField() {
        return showReturnBookField;
    }

    public void setShowReturnBookField(boolean showReturnBookField) {
        this.showReturnBookField = showReturnBookField;
    }
}
