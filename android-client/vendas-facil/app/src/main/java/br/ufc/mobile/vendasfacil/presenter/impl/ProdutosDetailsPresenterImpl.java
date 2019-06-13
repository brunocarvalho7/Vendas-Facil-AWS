package br.ufc.mobile.vendasfacil.presenter.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.presenter.ProdutosDetailsPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.Utils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthenticationFirebase;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    public void onButtonConfirmClicked(String path) {
        this.salvar(path);
    }

    @Override
    public void salvar(final String path) {
        final Produto produto = mView.getData();

        if(produto.isValid()){
            if(produto.getId() != null){
                Call<Produto> callUpdate =
                        this.retrofitConfigAuthorization.getProdutoService().update(produto.getId(), produto);

                callUpdate.enqueue(new Callback<Produto>() {
                    @Override
                    public void onResponse(Call<Produto> call, Response<Produto> response) {
                        if(response.isSuccessful()){
                            if(path != null)
                                uploadFoto(produto.getId(), path);

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
                        this.retrofitConfigAuthorization.getProdutoService()
                                .save(VendasFacilAuthenticationFirebase.getInstance().getFilial().getId(), produto);

                callSave.enqueue(new Callback<Produto>() {
                    @Override
                    public void onResponse(Call<Produto> call, Response<Produto> response) {
                        if(response.isSuccessful()){
                            Produto p = response.body();
                            if(path != null)
                                uploadFoto(p.getId(), path);

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

    @Override
    public void uploadFoto(String idProduto, String path) {
        File file = new File(path);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody mpbFile = new MultipartBody.Builder()
                .addFormDataPart("file", path, mFile)
                .build();

        Call<Map<String, String>> callUpload =
                this.retrofitConfigAuthorization.getProdutoService().uploadPhoto(idProduto, mpbFile);

        callUpload.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if(response.isSuccessful()){
                    mView.showText("Iniciando o envio da foto do produto...");
                }else{
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG,
                        "Erro ao tentar fazer o upload da foto ", t, mView);
            }
        });
    }

    @Override
    public void resizeImage(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap bmpResized = Utils.getScaledDownBitmap(bitmap, 800, false);
        File file = new File(path);

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bmpResized.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            fOut.flush();
            fOut.close();;
        } catch (IOException e) {
            Log.e(TAG, "Error on resizeImage: " + e.getMessage());
        }
    }


}
