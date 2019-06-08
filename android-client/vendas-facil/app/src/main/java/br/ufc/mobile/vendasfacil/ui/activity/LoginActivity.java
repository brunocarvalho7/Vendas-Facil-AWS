package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Map;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.config.RetrofitConfig;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.UsuarioDTO;
import br.ufc.mobile.vendasfacil.presenter.LoginPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.LoginPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthenticationFirebase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        VendasFacilView.ViewLogin,
        SignUpActivity.OnConfirmCadastroListener{

    private LoginPresenter mPresenter;
    private TextInputEditText txtEmail, txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.mPresenter = new LoginPresenterImpl(this);
        setUpLogin();
    }

    private void setUpLogin(){
        txtEmail = findViewById(R.id.activity_login_email);
        txtSenha = findViewById(R.id.activity_login_senha);
        findViewById(R.id.activity_login_btn_signin).setOnClickListener(this);
        findViewById(R.id.activity_login_btn_signup).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.activity_login_btn_signin){
            mPresenter.signIn();
        }else if(i == R.id.activity_login_btn_signup) {
            mPresenter.signUp();
        }
    }

    @Override
    public void abrirActivityPrincipal(){
        Intent it = new Intent(this, PrincipalActivity.class);
        startActivity(it);
    }

    @Override
    public void abrirActivitySignUp() {
        SignUpActivity signUpActivity = new SignUpActivity();
        signUpActivity.show(getSupportFragmentManager(), "SignUp");
    }

    @Override
    public void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public UsuarioDTO getData() {
        return new UsuarioDTO(txtEmail.getText().toString(), txtSenha.getText().toString());
    }

    @Override
    public void onConfirm(Usuario usuario) {
        mPresenter.performSignUp(usuario);
    }
}