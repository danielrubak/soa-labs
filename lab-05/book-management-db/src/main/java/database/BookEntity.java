package database;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class BookEntity {
    private String authorSurname;
    private String authorName;
    private String bookTitle;
    private String isbnNumber;
    private Integer releaseDate;
    private BigDecimal price;
    private int id;

    public BookEntity() {
    }

    public BookEntity(String authorSurname, String authorName, String bookTitle, String isbnNumber, int releaseDate, BigDecimal price) {
        this.authorSurname = authorSurname;
        this.authorName = authorName;
        this.bookTitle = bookTitle;
        this.isbnNumber = isbnNumber;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    @Column(name = "author_surname")
    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    @Column(name = "author_name")
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Column(name = "book_title")
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Column(name = "isbn_number")
    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    @Column(name = "date_of_issue")
    public Integer getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
