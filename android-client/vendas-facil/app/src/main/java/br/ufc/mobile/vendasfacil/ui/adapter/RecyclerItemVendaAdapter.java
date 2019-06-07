package br.ufc.mobile.vendasfacil.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.ui.activity.VendasAlterarQuantidadeDialog;

public class RecyclerItemVendaAdapter
        extends RecyclerView.Adapter<RecyclerItemVendaAdapter.CustomViewHolder> {

    private List<ItemVenda> dados;

    public RecyclerItemVendaAdapter(List<ItemVenda> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.activity_venda_custom_recycler_item_venda, viewGroup, false);

        return new CustomViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        ItemVenda itemVenda = dados.get(i);

        customViewHolder.txtQtd.setText( itemVenda.getQtdText() );
        customViewHolder.txtDescricao.setText( itemVenda.getProduto().getDescricao() );
        customViewHolder.txtTotal.setText( itemVenda.getTotalText() );
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public ItemVenda getItem(int position){
        return dados.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        public TextView txtQtd;
        public TextView txtDescricao;
        public TextView txtTotal;

        public CustomViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            txtQtd       = itemView.findViewById(R.id.activity_venda_custom_recycler_item_venda_qtd);
            txtTotal     = itemView.findViewById(R.id.activity_venda_custom_recycler_item_venda_total);
            txtDescricao = itemView.findViewById(R.id.activity_venda_custom_recycler_item_venda_descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ItemVenda.KEY, dados.get(getLayoutPosition()));

                    VendasAlterarQuantidadeDialog vendasAlterarQuantidadeDialog = new VendasAlterarQuantidadeDialog();
                    vendasAlterarQuantidadeDialog.setArguments(bundle);
                    vendasAlterarQuantidadeDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "Vendas-Qtd");
                }
            });
        }
    }
}
