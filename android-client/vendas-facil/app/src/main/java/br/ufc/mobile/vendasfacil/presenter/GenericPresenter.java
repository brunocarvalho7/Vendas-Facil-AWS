package br.ufc.mobile.vendasfacil.presenter;

public interface GenericPresenter<T> {
    void loadAdapterData();
    void delete(T obj);
}
