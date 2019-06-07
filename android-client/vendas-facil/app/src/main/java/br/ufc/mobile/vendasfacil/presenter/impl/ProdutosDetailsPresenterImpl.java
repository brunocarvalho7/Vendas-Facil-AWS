package br.ufc.mobile.vendasfacil.presenter.impl;

import br.ufc.mobile.vendasfacil.dao.ProdutoDao;
import br.ufc.mobile.vendasfacil.dao.impl.ProdutoDaoImpl;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.presenter.ProdutosDetailsPresenter;
import br.ufc.mobile.vendasfacil.ui.View;

public class ProdutosDetailsPresenterImpl implements ProdutosDetailsPresenter {

    View.ViewDetails<Produto> mView;
    ProdutoDao produtoDao;

    public ProdutosDetailsPresenterImpl(View.ViewDetails mView){
        this.mView = mView;
        produtoDao = new ProdutoDaoImpl(null);
    }

    @Override
    public void onButtonConfirmClicked() {
        if(this.salvar()) {
            mView.showText("Produto salvo com sucesso!");
            mView.finishActivity();
        }
    }

    @Override
    public boolean salvar() {
        Produto produto = mView.getData();

        if(produto.isValid()){
            if(produto.getId() != null)
                return produtoDao.update(produto);
            else
                produtoDao.save(produto);

            return true;
        }else{
            mView.showText("Informe as informações do produto");

            return false;
        }
    }
}
