package repository;

import model.Borrowing;

import java.util.List;

public interface BorrowingRepository {
    List<Borrowing> getAllBookBorrowing();
    Borrowing getBookBorrowingById(int id);
    void borrowBook(int readerId, int bookId);
    void returnBook(int borrowingId);
}
