package br.ufc.mobile.vendasfacil.presenter.impl;

import java.util.List;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.presenter.ProdutosPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosPresenterImpl implements ProdutosPresenter {

    private static String TAG = "produtos";

    private VendasFacilView.ViewMaster mView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public ProdutosPresenterImpl(VendasFacilView.ViewMaster mView){
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
        this.mView = mView;
    }


    @Override
    public void loadAdapterData() {
        Call<List<Produto>> callfindAll = this.retrofitConfigAuthorization.getProdutoService()
                .findAll(VendasFacilAuthentication.getInstance().getFilial().getId());

        callfindAll.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if(response.isSuccessful()){
                    List<Produto> produtos = response.body();

                    mView.updateAdapter(produtos);

                }else {
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_TODOS, t, mView);
            }
        });
    }

    @Override
    public void delete(Produto produto) {
        Call<Boolean> callDelete = this.retrofitConfigAuthorization.getProdutoService().delete(produto.getId());

        callDelete.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    loadAdapterData();
                    mView.showText("Produto removido com sucesso");
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
