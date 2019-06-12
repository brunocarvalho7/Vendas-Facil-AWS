package br.ufc.mobile.vendasfacil.config;

import br.ufc.mobile.vendasfacil.service.UsuarioService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig(){
        this.retrofit = new Retrofit.Builder()
            .baseUrl("http://3.82.154.221:8080/vendas-facil/api/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    }

    public UsuarioService getUsuarioService(){
        return this.retrofit.create(UsuarioService.class);
    }

}
