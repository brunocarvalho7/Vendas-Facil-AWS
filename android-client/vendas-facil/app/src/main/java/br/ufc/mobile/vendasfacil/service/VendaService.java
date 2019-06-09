package br.ufc.mobile.vendasfacil.service;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Venda;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VendaService {

    @POST("vendas")
    Call<Venda> save(@Body Venda venda);

    @PUT("vendas/{venda}")
    Call<Venda> update(@Path("venda") String id, @Body Venda venda);

    @GET("vendas")
    Call<List<Venda>> findAll();

    @GET("vendas/{venda}")
    Call<Venda> findById(@Path("venda") String id);

    @DELETE("vendas/{venda}")
    Call<Boolean> delete(@Path("venda") String id);

}
