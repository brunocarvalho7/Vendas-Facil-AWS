package br.ufc.mobile.vendasfacil.service;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FornecedorService {

    @POST("fornecedores")
    Call<Fornecedor> save(@Query("filial") Integer filial, @Body Fornecedor fornecedor);

    @PUT("fornecedores/{fornecedor}")
    Call<Fornecedor> update(@Path("fornecedor") String id, @Body Fornecedor fornecedor);

    @GET("fornecedores")
    Call<List<Fornecedor>> findAll(@Query("filial") Integer filial);

    @GET("fornecedores/{fornecedor}")
    Call<Fornecedor> findById(@Path("fornecedor") String id);

    @DELETE("fornecedores/{fornecedor}")
    Call<Boolean> delete(@Path("fornecedor") String id);

}
