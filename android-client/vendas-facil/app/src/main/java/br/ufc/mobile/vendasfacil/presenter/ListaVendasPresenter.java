package br.ufc.mobile.vendasfacil.presenter;

import br.ufc.mobile.vendasfacil.model.Venda;

public interface ListaVendasPresenter extends GenericPresenter<Venda>{

    @Override
    void loadAdapterData();

    @Override
    void delete(Venda venda);

}
