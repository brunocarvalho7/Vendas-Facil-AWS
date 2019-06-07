package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.dao.VendaDao;
import br.ufc.mobile.vendasfacil.dao.impl.VendaDaoImpl;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.model.enums.FormaPagamento;

public class VendasPagamentoActivity extends AppCompatActivity {

    private VendaDao vendaDao;
    private EditText txtValor;
    private TextView txtTrocoLbl, txtTroco;
    private LinearLayout llFormaPagamento;
    private Venda venda;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda_pagamento);

        if(getIntent().getExtras() != null && getIntent().getExtras().getSerializable(Venda.KEY) != null){
            this.venda = (Venda) getIntent().getExtras().getSerializable(Venda.KEY);
        }

        vendaDao = new VendaDaoImpl(null);

        setUpEdtValor();
    }

    private void setUpEdtValor() {
        txtValor = findViewById(R.id.activity_venda_pagamento_edtValor);
        txtTroco = findViewById(R.id.activity_venda_pagamento_lblTrocoValor);
        txtTrocoLbl = findViewById(R.id.activity_venda_pagamento_lblTroco);
        llFormaPagamento = findViewById(R.id.activity_venda_pagamento_ll_formaPag);

        txtValor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    double rsPago = Double.parseDouble(txtValor.getText().toString());

                    txtTroco.setText( "R$: " + String.format("%.2f", rsPago - venda.getTotal()));

                    if(rsPago >= venda.getTotal()){
                        txtTrocoLbl.setText("TROCO: ");
                        llFormaPagamento.setVisibility(View.VISIBLE);
                        return false;
                    }else{
                        txtTrocoLbl.setText("FALTANDO: ");
                        llFormaPagamento.setVisibility(View.INVISIBLE);
                    }

                }
                return true;
            }
        });
        txtValor.setText( venda.getTotalText() );
    }

    private void finalizarVenda(FormaPagamento formaPagamento){
        venda.setFormaPagamento(formaPagamento);
        vendaDao.save(venda);
        Intent it = new Intent(this, VendasPagamentoConfirmacaoActivity.class);
        it.putExtra("TROCO", txtTroco.getText().toString());
        it.putExtra("VALOR", "R$: " + venda.getTotalText());
        startActivity(it);
    }

    public void finalizarVendaDinheiro(View view){
        this.finalizarVenda(FormaPagamento.DINHEIRO);
    }

    public void finalizarVendaCartao(View view){
        this.finalizarVenda(FormaPagamento.CARTAO);
    }
}
