package br.ufc.mobile.vendasfacil.presenter.impl;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.presenter.FornecedoresDetailsPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FornecedoresDetailsPresenterImpl implements FornecedoresDetailsPresenter {

    private static String TAG = "fornecedores";

    private VendasFacilView.ViewDetails<Fornecedor> mView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public FornecedoresDetailsPresenterImpl(VendasFacilView.ViewDetails mView){
        this.mView = mView;
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
    }

    @Override
    public void onButtonConfirmClicked() {
        this.salvar();
    }

    @Override
    public void salvar() {
        Fornecedor fornecedor = mView.getData();

        if(fornecedor.isValid()){
            if(fornecedor.getId() != null){
                Call<Fornecedor> callUpdate =
                        this.retrofitConfigAuthorization.getFornecedorService().update(fornecedor.getId(), fornecedor);

                callUpdate.enqueue(new Callback<Fornecedor>() {
                    @Override
                    public void onResponse(Call<Fornecedor> call, Response<Fornecedor> response) {
                        if(response.isSuccessful()){
                            mView.showText("Fornecedor atualizado com sucesso!");
                            mView.finishActivity();
                        }else{
                            APIUtils.getInstance().onRequestError(response, mView);
                        }
                    }

                    @Override
                    public void onFailure(Call<Fornecedor> call, Throwable t) {
                        APIUtils.getInstance().onRequestFailure(TAG,
                                APIUtils.MSG_ERRO_ATUALIZAR, t, mView);
                    }
                });
            }else{
                Call<Fornecedor> callSave =
                        this.retrofitConfigAuthorization.getFornecedorService()
                                .save(VendasFacilAuthentication.getInstance().getFilial().getId(), fornecedor);

                callSave.enqueue(new Callback<Fornecedor>() {
                    @Override
                    public void onResponse(Call<Fornecedor> call, Response<Fornecedor> response) {
                        if(response.isSuccessful()){
                            mView.showText("Fornecedor salvo com sucesso!");
                            mView.finishActivity();
                        }else{
                            APIUtils.getInstance().onRequestError(response, mView);
                        }
                    }

                    @Override
                    public void onFailure(Call<Fornecedor> call, Throwable t) {
                        APIUtils.getInstance().onRequestFailure(TAG,
                                APIUtils.MSG_ERRO_SALVAR, t, mView);
                    }
                });
            }
        }else{
            mView.showText("Informe as informações do produto");
        }
    }

}
