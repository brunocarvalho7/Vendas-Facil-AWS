package br.ufc.mobile.vendasfacil.presenter.impl;

import java.util.List;

import br.ufc.mobile.vendasfacil.dao.ClienteDao;
import br.ufc.mobile.vendasfacil.dao.DataStatus;
import br.ufc.mobile.vendasfacil.dao.impl.ClienteDaoImpl;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.presenter.ClientesPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;

public class ClientesPresenterImpl implements ClientesPresenter, DataStatus<Cliente> {

    private ClienteDao dao;
    private VendasFacilView.ViewMaster view;

    public ClientesPresenterImpl(VendasFacilView.ViewMaster view){
        dao = new ClienteDaoImpl(this);
        this.view = view;
    }

    @Override
    public List<Cliente> getClientes() {
        return dao.getAll();
    }

    @Override
    public void DataIsLoaded(List<Cliente> dados) {
        view.updateAdapter(dados);
    }
}
