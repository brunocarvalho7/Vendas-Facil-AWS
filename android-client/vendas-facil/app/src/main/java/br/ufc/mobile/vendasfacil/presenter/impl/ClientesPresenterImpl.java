package br.ufc.mobile.vendasfacil.presenter.impl;

import java.util.List;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.presenter.ClientesPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientesPresenterImpl implements ClientesPresenter {

    private static String TAG = "clientes";

    private VendasFacilView.ViewMaster mView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public ClientesPresenterImpl(VendasFacilView.ViewMaster mView){
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
        this.mView = mView;
    }

    @Override
    public void loadAdapterData() {
        Call<List<Cliente>> callfindAllClientes = this.retrofitConfigAuthorization.getClienteService().findAll();
        callfindAllClientes.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if(response.isSuccessful()){
                    List<Cliente> clientes = response.body();

                    mView.updateAdapter(clientes);

                }else {
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_TODOS, t, mView);
            }
        });
    }

    @Override
    public void delete(Cliente cliente) {
        Call<Boolean> callDelete = this.retrofitConfigAuthorization.getClienteService().delete(cliente.getId());

        callDelete.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    loadAdapterData();
                    mView.showText("Cliente removido com sucesso");
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
