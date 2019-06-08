package br.ufc.mobile.vendasfacil.presenter.impl;

import br.ufc.mobile.vendasfacil.dao.ClienteDao;
import br.ufc.mobile.vendasfacil.dao.impl.ClienteDaoImpl;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.presenter.ClientesDetailsPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;

public class ClientesDetailsPresenterImpl implements ClientesDetailsPresenter {

    VendasFacilView.ViewDetails<Cliente> mView;
    ClienteDao clienteDao;

    public ClientesDetailsPresenterImpl(VendasFacilView.ViewDetails mView){
        this.mView = mView;
        clienteDao = new ClienteDaoImpl(null);
    }


    @Override
    public void onButtonConfirmClicked() {
        if(this.salvar()) {
            mView.showText("Cliente salvo com sucesso!");
            mView.finishActivity();
        }
    }

    @Override
    public boolean salvar() {
        Cliente cliente = mView.getData();

        if(cliente.isValid()){
            if(cliente.getId() != null)
                return clienteDao.update(cliente);
            else
                clienteDao.save(cliente);

            return true;
        }else{
            mView.showText("Informe as informações do cliente");

            return false;
        }
    }
}
