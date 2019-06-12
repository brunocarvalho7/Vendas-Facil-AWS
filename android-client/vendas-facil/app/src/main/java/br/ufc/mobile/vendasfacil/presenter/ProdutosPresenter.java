package br.ufc.mobile.vendasfacil.presenter;

import br.ufc.mobile.vendasfacil.model.Produto;

public interface ProdutosPresenter extends GenericPresenter<Produto>{

    @Override
    void loadAdapterData();

    @Override
    void delete(Produto produto);
}
