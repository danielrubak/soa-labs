package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema= "library", name = "category")
public class BookCategory {
    private int id;
    private String name;
    private List<Book> books = new ArrayList();

    public BookCategory() {
    }

    public BookCategory(String name) {
        this.name=name;
    }

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        books.add(book);
        book.setCategory(this);
    }

    public void removeBook(Book book){
        books.remove(book);
        book.setCategory(null);
    }
}
