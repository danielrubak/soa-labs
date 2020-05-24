package repository;

import model.Borrowing;

import java.util.List;

public interface BorrowingRepository {
    List<Borrowing> getAllBookBorrowing();
    void borrowBook(int readerId, int bookId);
    void returnBook(int borrowingId);
}
