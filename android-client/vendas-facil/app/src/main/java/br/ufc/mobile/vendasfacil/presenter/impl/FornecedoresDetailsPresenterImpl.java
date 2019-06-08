package br.ufc.mobile.vendasfacil.presenter.impl;

import br.ufc.mobile.vendasfacil.dao.FornecedorDao;
import br.ufc.mobile.vendasfacil.dao.impl.FornecedorDaoImpl;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.presenter.FornecedoresDetailsPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;

public class FornecedoresDetailsPresenterImpl implements FornecedoresDetailsPresenter {

    VendasFacilView.ViewDetails<Fornecedor> mView;
    FornecedorDao fornecedorDao;

    public FornecedoresDetailsPresenterImpl(VendasFacilView.ViewDetails mView){
        this.mView = mView;
        fornecedorDao = new FornecedorDaoImpl(null);
    }


    @Override
    public void onButtonConfirmClicked() {
        if(this.salvar()) {
            mView.showText("Fornecedor salvo com sucesso!");
            mView.finishActivity();
        }
    }

    @Override
    public boolean salvar() {
        Fornecedor fornecedor = mView.getData();

        if(fornecedor.isValid()){
            if(fornecedor.getId() != null)
                return fornecedorDao.update(fornecedor);
            else
                fornecedorDao.save(fornecedor);

            return true;
        }else{
            mView.showText("Informe as informações do fornecedor");

            return false;
        }
    }
}
