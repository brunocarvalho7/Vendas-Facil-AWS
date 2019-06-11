package br.ufc.mobile.vendasfacil.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Venda;

public class RecyclerListaVendasAdapter extends RecyclerView.Adapter<RecyclerListaVendasAdapter.CustomViewHolder> {

    private List<Venda> dados;

    public RecyclerListaVendasAdapter() {
        this.dados = new ArrayList<>();
    }

    public void setDados(List<Venda> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.activity_lista_vendas_custom_recycler_item, viewGroup, false);

        return new CustomViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        Venda venda = dados.get(i);

        customViewHolder.txtId.setText(venda.getId());
        customViewHolder.txtData.setText(new SimpleDateFormat("dd/MM/yyyy").format(venda.getData()));
        customViewHolder.txtFormaPag.setText(venda.getFormaPagamento().toString());

        if (venda.getCliente() != null){
            customViewHolder.txtCliente.setText(venda.getCliente().getNome());
        }else{
            customViewHolder.txtCliente.setText("Cliente não informado");
        }

        customViewHolder.txtTotal.setText("R$ " + venda.getTotalText());

    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public Venda getItem(int position){
        return dados.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        public TextView txtId;
        public TextView txtData;
        public TextView txtFormaPag;
        public TextView txtCliente;
        public TextView txtTotal;

        public CustomViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            txtId       = itemView.findViewById(R.id.activity_lista_vendas_custom_recycler_item_id);
            txtData     = itemView.findViewById(R.id.activity_lista_vendas_custom_recycler_item_data);
            txtFormaPag = itemView.findViewById(R.id.activity_lista_vendas_custom_recycler_item_forma_pagamento);
            txtCliente  = itemView.findViewById(R.id.activity_lista_vendas_custom_recycler_item_cliente);
            txtTotal    = itemView.findViewById(R.id.activity_lista_vendas_custom_recycler_item_total);

        }
    }
}
