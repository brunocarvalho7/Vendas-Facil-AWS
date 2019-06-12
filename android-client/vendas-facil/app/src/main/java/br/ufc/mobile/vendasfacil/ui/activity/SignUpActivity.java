package br.ufc.mobile.vendasfacil.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Usuario;

public class SignUpActivity extends AppCompatDialogFragment {

    private View view;
    private OnConfirmCadastroListener listener;
    private TextInputEditText txtNome, txtEmail, txtPassword;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view  = getActivity().getLayoutInflater().inflate(R.layout.activity_sign_up, null);

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle("Novo usuário")
            .setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                txtNome = view.findViewById(R.id.activity_sign_up_nome);
                txtEmail = view.findViewById(R.id.activity_sign_up_email);
                txtPassword = view.findViewById(R.id.activity_sign_up_password);

                Usuario usuario = new Usuario();
                usuario.setNome(txtNome.getText().toString());
                usuario.setEmail(txtEmail.getText().toString());
                usuario.setPassword(txtPassword.getText().toString());

                listener.onConfirm(usuario);
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            })
            .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (OnConfirmCadastroListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must be implement OnConfirmCadastroListener");
        }
    }

    public interface OnConfirmCadastroListener{
        void onConfirm(Usuario usuario);
    }
}
