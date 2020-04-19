import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookLibrary {
    List<Book> books = new ArrayList<>();

    public BookLibrary() {
        Book hp_05 = new Book("Harry Potter and the Order of the Phoenix","J. K. Rowling", "Fantasy", 15, "GBP", 800);
        Book hp_07 = new Book("harry potter and deathly hallows", "J. K. Rowling", "Fantasy", 70, "PLN", 759);
        Book dd = new Book("Done Deal: An Insider's Guide to Football Contracts", "Daniel Geey", "Sport", 15, "USD", 336);
        Book tf = new Book ("The Four: The Hidden DNA of Amazon, Apple, Facebook, and Google", "Scott Galloway", "Finance",10, "EUR", 368);

        books.add(hp_05);
        books.add(hp_07);
        books.add(dd);
        books.add(tf);

        // just for searching and presenting
        books.add(new Book("Title 1 ","Author 1","Fantasy",20,"GBP",300));
        books.add(new Book("Title 2 ","Author 2","Sport",20,"EUR",300));
        books.add(new Book("Title 3 ","Author 3","Sport",20,"PLN",300));
        books.add(new Book("Title 4 ","Author 4","Finance",20,"USD",300));
    }
}
