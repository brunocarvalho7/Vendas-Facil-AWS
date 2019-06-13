package br.ufc.mobile.vendasfacil.service;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.Filial;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClienteService {

    @POST("clientes")
    Call<Cliente> save(@Query("filial") Integer filial, @Body Cliente cliente);

    @PUT("clientes/{cliente}")
    Call<Cliente> update(@Path("cliente") String id, @Body Cliente cliente);

    @GET("clientes")
    Call<List<Cliente>> findAll(@Query("filial") Integer filial);

    @GET("clientes/{cliente}")
    Call<Cliente> findById(@Path("cliente") String id);

    @DELETE("clientes/{cliente}")
    Call<Boolean> delete(@Path("cliente") String id);

}
