package br.ufc.mobile.vendasfacil.config;

import java.io.IOException;

import br.ufc.mobile.vendasfacil.service.ClienteService;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthenticationFirebase;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfigAuthorization {

    private final Retrofit retrofit;

    public RetrofitConfigAuthorization(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
            new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", VendasFacilAuthenticationFirebase.getInstance().getToken())
                        .build();

                    return chain.proceed(newRequest);
                }
            }
        ).build();

        this.retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl("http://3.82.154.221:8080/vendas-facil/api/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    }

    public ClienteService getClienteService(){
        return this.retrofit.create(ClienteService.class);
    }

}
