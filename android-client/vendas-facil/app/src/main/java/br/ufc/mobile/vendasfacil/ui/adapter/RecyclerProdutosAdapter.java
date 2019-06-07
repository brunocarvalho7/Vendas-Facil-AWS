package br.ufc.mobile.vendasfacil.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.ui.activity.ProdutosDetailsActivity;

public class RecyclerProdutosAdapter extends RecyclerView.Adapter<RecyclerProdutosAdapter.CustomViewHolder> implements Filterable {

    private List<Produto> dados;
    private List<Produto> dadosPesquisa;

    public RecyclerProdutosAdapter(List<Produto> dados) {
        this.dados = dados;
        this.dadosPesquisa = new ArrayList<>(dados);
    }

    public void setDados(List<Produto> dados) {
        this.dados = dados;
        this.dadosPesquisa = new ArrayList<>(dados);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.activity_produtos_custom_recycler_item, viewGroup, false);

        return new CustomViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        Produto produto = dadosPesquisa.get(i);

        customViewHolder.txtDescricao.setText(produto.getDescricao());
        customViewHolder.txtValor.setText(produto.getRsVendaText());
        customViewHolder.txtQtd.setText(produto.getEstoqueText());
    }

    @Override
    public int getItemCount() {
        return dadosPesquisa.size();
    }

    @Override
    public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<Produto> filteredList = new ArrayList<>();

                    if(constraint == null || constraint.toString().trim().length() == 0){
                        filteredList.addAll(dados);
                    }else{
                        String filterPattern = constraint.toString().toLowerCase().trim();

                        for(Produto p : dados){
                            if(p.getDescricao().toLowerCase().contains(filterPattern)){
                                filteredList.add(p);
                            }
                        }
                    }

                    FilterResults results = new FilterResults();
                    results.values = filteredList;
                    results.count = filteredList.size();

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    dadosPesquisa.clear();
                    dadosPesquisa.addAll((List) results.values);
                    notifyDataSetChanged();
                }
            };
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        public TextView txtDescricao;
        public TextView txtValor;
        public TextView txtQtd;

        public CustomViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            txtDescricao = itemView.findViewById(R.id.txtDescricao);
            txtValor     = itemView.findViewById(R.id.txtValor);
            txtQtd       = itemView.findViewById(R.id.txtQtd);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Produto p = dadosPesquisa.get(getLayoutPosition());

                    Intent it = new Intent(context, ProdutosDetailsActivity.class);
                    it.putExtra(Produto.KEY, p);
                    ((AppCompatActivity) context).startActivityForResult(it,0);
                }
            });
        }
    }
}
