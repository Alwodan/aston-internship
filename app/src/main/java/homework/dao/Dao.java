package homework.dao;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();

    T getById(Long id);

    T save(T obj);

    void update(T obj);

    void delete(Long id);
}
