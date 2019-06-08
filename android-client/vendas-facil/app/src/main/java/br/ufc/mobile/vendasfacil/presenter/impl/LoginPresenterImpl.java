package br.ufc.mobile.vendasfacil.presenter.impl;

import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

import br.ufc.mobile.vendasfacil.config.RetrofitConfig;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.UsuarioDTO;
import br.ufc.mobile.vendasfacil.presenter.LoginPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthenticationFirebase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter {

    private VendasFacilView.ViewLogin mView;
    private RetrofitConfig retrofitConfig;

    public LoginPresenterImpl(VendasFacilView.ViewLogin mView) {
        this.mView = mView;
        this.retrofitConfig = APIUtils.getInstance().getRetrofitConfig();
    }

    @Override
    public void signIn() {
        UsuarioDTO usuarioDTO = mView.getData();

        if(usuarioDTO != null && usuarioDTO.isValid()){
            Call<Map<String, String>> callSigin = this.retrofitConfig.getUsuarioService().signin(usuarioDTO);
            callSigin.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if(response.isSuccessful()){
                        Map<String, String> auth = response.body();
                        VendasFacilAuthenticationFirebase.getInstance().setUsername(auth.get("username"));
                        VendasFacilAuthenticationFirebase.getInstance().setToken(auth.get("token"));

                        mView.abrirActivityPrincipal();
                    }else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            mView.showText(jObjError.getString("message"));
                        } catch (Exception e) {
                            mView.showText(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Log.e("Login", "Erro ao realizar login: "+ t.getMessage());
                    mView.showText("Erro ao realizar login: "+ t.getMessage());
                }
            });
        }else {
            mView.showText("Informe um email e uma senha");
        }
    }

    @Override
    public void signUp() {
        mView.abrirActivitySignUp();
    }

    @Override
    public void performSignUp(Usuario usuario) {
        Log.i("Cadastrar", "performSignUp: "+usuario);
        if(usuario != null && usuario.isValid()){
            Log.i("Cadastrar", "entrou");
            Call<Usuario> callSignup = this.retrofitConfig.getUsuarioService().signup(usuario);

            callSignup.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        mView.showText("Usuário cadastrado com sucesso!");
                    }else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            mView.showText(jObjError.getString("message"));
                        } catch (Exception e) {
                            mView.showText(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e("Login", "Erro ao cadastrar um novo usuário: "+ t.getMessage());
                    mView.showText("Ocorreu um erro ao tentar cadastrar o usuário. Tente novamente!");
                }
            });
        }else {
            mView.showText("Ocorreu um erro ao tentar cadastrar o usuário. Tente novamente!");
        }
    }
}
