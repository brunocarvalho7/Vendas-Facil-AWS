package br.ufc.mobile.vendasfacil.dao;

import java.util.List;

public interface GenericDao<T> {

    void save(T obj);
    boolean remove(T obj);
    List<T> getAll();
    boolean update(T obj);
    String getNewId();

}
