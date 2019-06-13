package br.ufc.mobile.vendasfacil.service;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Filial;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilialService {

    @POST("filiais")
    Call<Filial> save(@Body Filial filial);

    @PUT("filiais/{filial}")
    Call<Filial> update(@Path("filial") Integer id, @Body Filial filial);

    @GET("filiais")
    Call<List<Filial>> findAll();

    @GET("filiais/findByCoordenadas")
    Call<Filial> findByCoordenadas(@Query("latitude") Double latitude,
                                          @Query("longitude") Double longitude);

    @GET("filiais/{filial}")
    Call<Filial> findById(@Path("filial") String id);

    @DELETE("filiais/{filial}")
    Call<Boolean> delete(@Path("filial") String id);

}
