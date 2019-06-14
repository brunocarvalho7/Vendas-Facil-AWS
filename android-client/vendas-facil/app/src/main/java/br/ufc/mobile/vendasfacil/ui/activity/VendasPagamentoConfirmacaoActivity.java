package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import br.ufc.mobile.vendasfacil.R;

public class VendasPagamentoConfirmacaoActivity extends AppCompatActivity {

    private TextView txtTroco, txtValor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda_pagamento_confirmacao);

        txtTroco = findViewById(R.id.activity_venda_pagamento_confirmacao_lblTrocoValor);
        txtValor = findViewById(R.id.activity_venda_pagamento_confirmacao_lblValorRs);

        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().get("TROCO") != null &&
                    getIntent().getExtras().get("VALOR") != null){
                txtValor.setText( getIntent().getExtras().getString("VALOR") );
                txtTroco.setText( getIntent().getExtras().getString("TROCO") );
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(this, PrincipalActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.putExtra("inicial", false);
        startActivity(it);
        finish();
    }

    public void novaVenda(View view){
        Intent it = new Intent(this, VendasActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }

}
