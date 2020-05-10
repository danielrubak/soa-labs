package repository;

import model.Catalog;

import java.util.List;

public interface CatalogRepository {
    List<Catalog> getCatalog();
    void updateCatalog(int id, int quantity, int available);
}
