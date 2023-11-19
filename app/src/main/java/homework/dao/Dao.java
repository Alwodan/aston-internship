package homework.dao;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();

    T getById(Long id);

    Long save(T obj);

    void update(T obj);

    boolean delete(Long id);
}
