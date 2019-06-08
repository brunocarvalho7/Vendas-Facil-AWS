package br.ufc.mobile.vendasfacil.utils;

import android.util.Log;

import org.json.JSONObject;

import br.ufc.mobile.vendasfacil.config.RetrofitConfig;
import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import retrofit2.Response;

public class APIUtils {

    public static final String MSG_ERRO_REMOVER = "Erro ao remover ";
    public static final String MSG_ERRO_LOCALIZAR_TODOS = "Erro ao localizar todos ";
    public static final String MSG_ERRO_SALVAR = "Erro ao salvar ";
    public static final String MSG_ERRO_ATUALIZAR = "Erro ao atualizar ";
    private static APIUtils instance;

    private RetrofitConfig retrofitConfig;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    private APIUtils(){
    }

    public static APIUtils getInstance(){
        if(instance == null)
            instance = new APIUtils();

        return instance;
    }

    public RetrofitConfig getRetrofitConfig(){
        if(retrofitConfig == null)
            retrofitConfig = new RetrofitConfig();

        return retrofitConfig;
    }

    public RetrofitConfigAuthorization getRetrofitConfigAuthorization(){
        if(retrofitConfigAuthorization == null)
            retrofitConfigAuthorization = new RetrofitConfigAuthorization();

        return retrofitConfigAuthorization;
    }

    public void onRequestError(Response response, VendasFacilView.IShowText mView){
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            mView.showText(jObjError.getString("message"));
        } catch (Exception e) {
            mView.showText(e.getMessage());
        }
    }

    public void onRequestFailure(String tag, String msg, Throwable t, VendasFacilView.IShowText mView){
        mView.showText( msg +  tag + ": " +  t.getMessage());
        Log.e(tag, msg +  tag + ": " +  t.getMessage());
    }
}
