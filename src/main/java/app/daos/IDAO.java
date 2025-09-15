package app.daos;

import java.util.List;

public interface IDAO<T, I> {

    // Create
    T create(T t);

    // Read
    List<T> getAll();
    T getById(I id);

    // Update
    T update(T t);

    // Delete
    boolean delete(I id);
}
