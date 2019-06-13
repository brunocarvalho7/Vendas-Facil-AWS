package br.ufc.mobile.vendasfacil.service;

import java.util.List;
import java.util.Map;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Produto;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProdutoService {

    @POST("produtos")
    Call<Produto> save(@Query("filial") Integer filial, @Body Produto produto);

    @PUT("produtos/{produto}")
    Call<Produto> update(@Path("produto") String id, @Body Produto produto);

    @GET("produtos")
    Call<List<Produto>> findAll(@Query("filial") Integer filial);

    @GET("produtos/{produto}")
    Call<Produto> findById(@Path("produto") String id);

    @GET("produtos/barcode/{codBarras}")
    Call<Produto> findByBarCode(@Path("codBarras") String barcode);

    @DELETE("produtos/{produto}")
    Call<Boolean> delete(@Path("produto") String id);

    @Multipart
    @POST("produtos/{produto}/photo")
    Call<Map<String, String>> uploadPhoto(@Path("produto") String id, @Part("file") MultipartBody file);

}
