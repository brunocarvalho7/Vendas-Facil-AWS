package br.ufc.mobile.vendasfacil.presenter;

import br.ufc.mobile.vendasfacil.model.Fornecedor;

public interface FornecedoresPresenter extends GenericPresenter<Fornecedor> {

    @Override
    void loadAdapterData();

    @Override
    void delete(Fornecedor fornecedor);
}
