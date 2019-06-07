package br.ufc.mobile.vendasfacil.dao;

import java.util.List;

public interface DataStatus<T> {
    void DataIsLoaded(List<T> dados);
}
