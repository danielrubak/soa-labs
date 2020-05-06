package api;

import model.Reader;

import java.util.List;

public interface ReaderAPI {

    List<Reader> getAllReaders();
    void addReader(Reader reader);
    void deleteReader(int id);
    void updateReader(int id, Reader reader);
}
