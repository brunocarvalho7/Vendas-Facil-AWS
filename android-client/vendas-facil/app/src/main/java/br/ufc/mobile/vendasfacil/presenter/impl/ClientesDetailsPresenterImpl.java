package br.ufc.mobile.vendasfacil.presenter.impl;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.presenter.ClientesDetailsPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientesDetailsPresenterImpl implements ClientesDetailsPresenter {

    private static String TAG = "clientes";

    private VendasFacilView.ViewDetails<Cliente> mView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public ClientesDetailsPresenterImpl(VendasFacilView.ViewDetails mView){
        this.mView = mView;
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
    }

    @Override
    public void onButtonConfirmClicked() {
       this.salvar();
    }

    @Override
    public void salvar() {
        Cliente cliente = mView.getData();

        if(cliente.isValid()){
            if(cliente.getId() != null){
                Call<Cliente> callUpdateCliente =
                        this.retrofitConfigAuthorization.getClienteService().update(cliente.getId(), cliente);

                callUpdateCliente.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        if(response.isSuccessful()){
                            mView.showText("Cliente atualizado com sucesso!");
                            mView.finishActivity();
                        }else{
                            APIUtils.getInstance().onRequestError(response, mView);
                        }
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        APIUtils.getInstance().onRequestFailure(TAG,
                                APIUtils.MSG_ERRO_ATUALIZAR, t, mView);
                    }
                });
            }else{
                Call<Cliente> callSaveCliente =
                        this.retrofitConfigAuthorization.getClienteService()
                                .save(VendasFacilAuthentication.getInstance().getFilial().getId(), cliente);

                callSaveCliente.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        if(response.isSuccessful()){
                            mView.showText("Cliente salvo com sucesso!");
                            mView.finishActivity();
                        }else{
                            APIUtils.getInstance().onRequestError(response, mView);
                        }
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        APIUtils.getInstance().onRequestFailure(TAG,
                                APIUtils.MSG_ERRO_SALVAR, t, mView);
                    }
                });
            }
        }else{
            mView.showText("Informe as informações do cliente");
        }
    }
}
