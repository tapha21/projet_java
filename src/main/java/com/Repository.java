package com;

import java.util.List;

public interface Repository<T>  {
    void insert(T objet);
    void update(T objet);
    List<T> selectAll();
    T selectById(int id);
    void remove(int id);
    T findby(int id);
}
