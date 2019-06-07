package br.ufc.mobile.vendasfacil.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.ItemVenda;

public class VendasAlterarQuantidadeDialog extends AppCompatDialogFragment {

    private Button incButton, decButton;
    private EditText txtQtd;
    private View view;
    private OnSetQuantidadeListener listener;
    private ItemVenda itemVenda;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view      = getActivity().getLayoutInflater().inflate(R.layout.activity_venda_alterar_quantidade, null);

        if(getArguments().getSerializable(ItemVenda.KEY) != null)
            itemVenda = (ItemVenda) getArguments().getSerializable(ItemVenda.KEY);

        setUpButtonsAndTextView();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Alterar quantidade")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSetQuantidade(itemVenda, Double.parseDouble(txtQtd.getText().toString()));
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

    private void setUpButtonsAndTextView() {
        txtQtd    = view.findViewById(R.id.activity_venda_alterar_quantidade_display);
        incButton = view.findViewById(R.id.activity_venda_alterar_quantidade_increment);
        decButton = view.findViewById(R.id.activity_venda_alterar_quantidade_decrement);

        txtQtd.setText(String.valueOf(itemVenda.getQtd()));

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(txtQtd.getText().toString()) > 1.0)
                txtQtd.setText( String.valueOf(Double.parseDouble(txtQtd.getText().toString()) - 1) );
            }
        });

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtQtd.setText( String.valueOf(Double.parseDouble(txtQtd.getText().toString()) + 1) );
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (OnSetQuantidadeListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must be implement OnSetQuantidadeListener");
        }
    }

    public interface OnSetQuantidadeListener{
        void onSetQuantidade(ItemVenda itemVenda, Double newQuantidade);
    }

}
