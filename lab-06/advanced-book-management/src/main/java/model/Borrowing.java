package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booksBorrowing", schema = "library")
public class Borrowing {
    private int id;
    private Book book;
    private Reader reader;
    private Catalog catalog;
    private Date fromDate;
    private Date toDate;

    public Borrowing() {
    }

    public Borrowing(Date fromDate, Date toDate) {
        this.fromDate=fromDate;
        this.toDate=toDate;
    }

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    @ManyToOne
    public Reader getReader() {
        return reader;
    }
    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @OneToOne
    @JoinColumn(name = "catalog_id")
    public Catalog getCatalog() {
        return catalog;
    }
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Column(name = "fromDate", nullable = false)
    public Date getFromDate() {
        return fromDate;
    }
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Column(name = "toDate", nullable = false)
    public Date getToDate() {
        return toDate;
    }
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
