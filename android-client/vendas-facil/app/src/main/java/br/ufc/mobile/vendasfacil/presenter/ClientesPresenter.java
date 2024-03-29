package br.ufc.mobile.vendasfacil.presenter;

import br.ufc.mobile.vendasfacil.model.Cliente;

public interface ClientesPresenter extends GenericPresenter<Cliente> {

    void loadAdapterData();
    void delete(Cliente cliente);

}
