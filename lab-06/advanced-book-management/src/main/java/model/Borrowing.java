package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booksBorrowing", schema = "library")
public class BooksBorrowing {
    private int id;
    Book book;
    Reader reader;
    Catalog catalog;
    Date fromDate;
    Date toDate;

    public BooksBorrowing() {
        super();
    }
    public BooksBorrowing(Date fromDate, Date toDate) {
        super();
        this.fromDate=fromDate;
        this.toDate=toDate;
    }

    @OneToOne
    @JoinColumn(name = "catalog_id")
    public Catalog getCatalog() { return catalog; }
    public void setCatalog(Catalog catalog) { this.catalog = catalog; }

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public String toString() {
        return "BooksBorrowing{" +
                "id=" + id +
                ", book=" + book +
                ", reader=" + reader +
                ", catalog=" + catalog +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
