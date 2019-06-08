package br.ufc.mobile.vendasfacil.presenter.impl;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.presenter.ProdutosDetailsPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosDetailsPresenterImpl implements ProdutosDetailsPresenter {

    private static String TAG = "produtos";

    private VendasFacilView.ViewDetails<Produto> mView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public ProdutosDetailsPresenterImpl(VendasFacilView.ViewDetails mView){
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
        this.mView = mView;
    }

    @Override
    public void onButtonConfirmClicked() {
        this.salvar();
    }

    @Override
    public void salvar() {
        Produto produto = mView.getData();

        if(produto.isValid()){
            if(produto.getId() != null){
                Call<Produto> callUpdate =
                        this.retrofitConfigAuthorization.getProdutoService().update(produto.getId(), produto);

                callUpdate.enqueue(new Callback<Produto>() {
                    @Override
                    public void onResponse(Call<Produto> call, Response<Produto> response) {
                        if(response.isSuccessful()){
                            mView.showText("Produto atualizado com sucesso!");
                            mView.finishActivity();
                        }else{
                            APIUtils.getInstance().onRequestError(response, mView);
                        }
                    }

                    @Override
                    public void onFailure(Call<Produto> call, Throwable t) {
                        APIUtils.getInstance().onRequestFailure(TAG,
                                APIUtils.MSG_ERRO_ATUALIZAR, t, mView);
                    }
                });
            }else{
                Call<Produto> callSave =
                        this.retrofitConfigAuthorization.getProdutoService().save(produto);

                callSave.enqueue(new Callback<Produto>() {
                    @Override
                    public void onResponse(Call<Produto> call, Response<Produto> response) {
                        if(response.isSuccessful()){
                            mView.showText("Produto salvo com sucesso!");
                            mView.finishActivity();
                        }else{
                            APIUtils.getInstance().onRequestError(response, mView);
                        }
                    }

                    @Override
                    public void onFailure(Call<Produto> call, Throwable t) {
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
