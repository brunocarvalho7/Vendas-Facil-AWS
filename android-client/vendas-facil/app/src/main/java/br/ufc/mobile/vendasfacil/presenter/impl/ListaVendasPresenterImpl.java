package br.ufc.mobile.vendasfacil.presenter.impl;

import java.util.List;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.presenter.ListaVendasPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaVendasPresenterImpl implements ListaVendasPresenter {

    private static String TAG = "vendas-listagem";

    private VendasFacilView.ViewMaster mView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public ListaVendasPresenterImpl(VendasFacilView.ViewMaster mView) {
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
        this.mView = mView;
    }

    @Override
    public void loadAdapterData() {
        Call<List<Venda>> callfindAll = this.retrofitConfigAuthorization.getVendaService()
                .findAll(VendasFacilAuthentication.getInstance().getFilial().getId());

        callfindAll.enqueue(new Callback<List<Venda>>() {
            @Override
            public void onResponse(Call<List<Venda>> call, Response<List<Venda>> response) {
                if(response.isSuccessful()){
                    List<Venda> vendas = response.body();

                    mView.updateAdapter(vendas);

                }else {
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<List<Venda>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_TODOS, t, mView);
            }
        });
    }

    @Override
    public void delete(Venda venda) {
        Call<Boolean> callDelete = this.retrofitConfigAuthorization.getVendaService().delete(venda.getId());

        callDelete.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    loadAdapterData();
                    mView.showText("Venda removida com sucesso");
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
