package br.ufc.mobile.vendasfacil.presenter.impl;

import java.util.List;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.presenter.FornecedoresPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FornecedoresPresenterImpl implements FornecedoresPresenter {

    private static String TAG = "fornecedores";

    private VendasFacilView.ViewMaster mView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public FornecedoresPresenterImpl(VendasFacilView.ViewMaster mView) {
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
        this.mView = mView;
    }

    @Override
    public void loadAdapterData() {
        Call<List<Fornecedor>> callfindAll = this.retrofitConfigAuthorization.getFornecedorService()
                .findAll(VendasFacilAuthentication.getInstance().getFilial().getId());

        callfindAll.enqueue(new Callback<List<Fornecedor>>() {
            @Override
            public void onResponse(Call<List<Fornecedor>> call, Response<List<Fornecedor>> response) {
                if(response.isSuccessful()){
                    List<Fornecedor> fornecedores = response.body();

                    mView.updateAdapter(fornecedores);

                }else {
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<List<Fornecedor>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_TODOS, t, mView);
            }
        });
    }

    @Override
    public void delete(Fornecedor fornecedor) {
        Call<Boolean> callDelete = this.retrofitConfigAuthorization.getFornecedorService().delete(fornecedor.getId());

        callDelete.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    loadAdapterData();
                    mView.showText("Fornecedor removido com sucesso");
                }else{
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_REMOVER, t, mView);
            }
        });
    }
}
