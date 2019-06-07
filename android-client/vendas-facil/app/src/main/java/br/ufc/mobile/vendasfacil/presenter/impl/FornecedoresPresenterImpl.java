package br.ufc.mobile.vendasfacil.presenter.impl;

import java.util.List;

import br.ufc.mobile.vendasfacil.dao.DataStatus;
import br.ufc.mobile.vendasfacil.dao.FornecedorDao;
import br.ufc.mobile.vendasfacil.dao.impl.FornecedorDaoImpl;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.presenter.FornecedoresPresenter;
import br.ufc.mobile.vendasfacil.ui.View;

public class FornecedoresPresenterImpl implements FornecedoresPresenter, DataStatus<Fornecedor> {

    private FornecedorDao dao;
    private View.ViewMaster view;

    public FornecedoresPresenterImpl(View.ViewMaster view){
        dao = new FornecedorDaoImpl(this);
        this.view = view;
    }

    @Override
    public List<Fornecedor> getFornecedores() {
        return dao.getAll();
    }

    @Override
    public void DataIsLoaded(List<Fornecedor> dados) {
        view.updateAdapter(dados);
    }
}
