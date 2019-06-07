package br.ufc.mobile.vendasfacil.presenter.impl;

import java.util.List;

import br.ufc.mobile.vendasfacil.dao.DataStatus;
import br.ufc.mobile.vendasfacil.dao.ProdutoDao;
import br.ufc.mobile.vendasfacil.dao.impl.ProdutoDaoImpl;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.presenter.ProdutosPresenter;
import br.ufc.mobile.vendasfacil.ui.View;

public class ProdutosPresenterImpl implements ProdutosPresenter, DataStatus<Produto> {

    private ProdutoDao dao;
    private View.ViewMaster view;

    public ProdutosPresenterImpl(View.ViewMaster view){
        dao = new ProdutoDaoImpl(this);
        this.view = view;
    }

    @Override
    public List<Produto> getProdutos() {
        return dao.getAll();
    }

    @Override
    public void DataIsLoaded(List<Produto> dados) {
        view.updateAdapter(dados);
    }
}
