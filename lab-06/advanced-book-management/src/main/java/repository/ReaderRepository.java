package repository;

import model.Reader;

import java.util.List;

public interface ReaderRepository {

    List<Reader> getAllReaders();
    void addReader(Reader reader);
    void deleteReader(int id);
    void updateReader(int id, Reader reader);
}
