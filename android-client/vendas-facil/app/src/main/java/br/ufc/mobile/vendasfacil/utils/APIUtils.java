package br.ufc.mobile.vendasfacil.utils;

import com.google.firebase.auth.FirebaseUser;

import br.ufc.mobile.vendasfacil.config.RetrofitConfig;

public class APIUtils {

    private static APIUtils instance;

    private RetrofitConfig retrofitConfig;

    private APIUtils(){
        retrofitConfig = new RetrofitConfig();
    }

    public static APIUtils getInstance(){
        if(instance == null)
            instance = new APIUtils();

        return instance;
    }

    public RetrofitConfig getRetrofitConfig(){
        return retrofitConfig;
    }
}
