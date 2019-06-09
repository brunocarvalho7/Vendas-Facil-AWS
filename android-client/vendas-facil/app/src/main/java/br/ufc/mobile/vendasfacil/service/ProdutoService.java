package br.ufc.mobile.vendasfacil.service;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProdutoService {

    @POST("produtos")
    Call<Produto> save(@Body Produto produto);

    @PUT("produtos/{produto}")
    Call<Produto> update(@Path("produto") String id, @Body Produto produto);

    @GET("produtos")
    Call<List<Produto>> findAll();
    @GET("produtos/{produto}")
    Call<Produto> findById(@Path("produto") String id);

    @GET("produtos/barcode/{codBarras}")
    Call<Produto> findByBarCode(@Path("codBarras") String barcode);

    @DELETE("produtos/{produto}")
    Call<Boolean> delete(@Path("produto") String id);

}
