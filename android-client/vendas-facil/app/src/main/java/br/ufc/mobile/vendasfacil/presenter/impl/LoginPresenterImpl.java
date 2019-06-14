package br.ufc.mobile.vendasfacil.presenter.impl;

import android.util.Log;

import java.util.Map;

import br.ufc.mobile.vendasfacil.config.RetrofitConfig;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.UsuarioDTO;
import br.ufc.mobile.vendasfacil.presenter.LoginPresenter;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter {

    private static final String TAG = "Login";

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
                        VendasFacilAuthentication.getInstance().setUsername(auth.get("username"));
                        VendasFacilAuthentication.getInstance().setToken(auth.get("token"));

                        mView.abrirActivityPrincipal();
                    }else {
                        APIUtils.getInstance().onRequestError(response, mView);
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    APIUtils.getInstance().onRequestFailure(TAG, "Erro ao tentar fazer login ", t, mView);
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
        Log.i(TAG, "performSignUp: "+usuario);

        if(usuario != null && usuario.isValid()){
            Call<Usuario> callSignup = this.retrofitConfig.getUsuarioService().signup(usuario);

            callSignup.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        mView.showText("Usuário cadastrado com sucesso!");
                    }else {
                        APIUtils.getInstance().onRequestError(response, mView);
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    APIUtils.getInstance().onRequestFailure(TAG, "Ocorreu um erro ao tentar cadastrar o usuário. Tente novamente",
                            t, mView);
                }
            });
        }else {
            mView.showText("Ocorreu um erro ao tentar cadastrar o usuário. Tente novamente!");
        }
    }
}
