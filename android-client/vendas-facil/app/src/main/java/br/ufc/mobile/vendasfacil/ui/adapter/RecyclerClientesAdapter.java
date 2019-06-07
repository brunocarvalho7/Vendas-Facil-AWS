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

import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.ui.activity.ClientesDetailsActivity;

public class RecyclerClientesAdapter extends RecyclerView.Adapter<RecyclerClientesAdapter.CustomViewHolder>
        implements Filterable {

    private List<Cliente> dados;
    private List<Cliente> dadosPesquisa;

    public RecyclerClientesAdapter(List<Cliente> dados) {
        this.dados = dados;
        this.dadosPesquisa = new ArrayList<>(dados);
    }

    public void setDados(List<Cliente> dados){
        this.dados = dados;
        this.dadosPesquisa = new ArrayList<>(dados);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);

        return new CustomViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        Cliente cliente = dadosPesquisa.get(i);

        customViewHolder.txtNome.setText(cliente.getNome());
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
                    List<Cliente> filteredList = new ArrayList<>();

                    if(constraint == null || constraint.toString().trim().length() == 0){
                        filteredList.addAll(dados);
                    }else{
                        String filterPattern = constraint.toString().toLowerCase().trim();

                        for(Cliente c : dados){
                            if(c.getNome().toLowerCase().contains(filterPattern)){
                                filteredList.add(c);
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

        public TextView txtNome;

        public CustomViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            txtNome = itemView.findViewById(android.R.id.text1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cliente c = dadosPesquisa.get(getLayoutPosition());

                    Intent it = new Intent(context, ClientesDetailsActivity.class);
                    it.putExtra(Cliente.KEY, c);
                    ((AppCompatActivity) context).startActivityForResult(it,0);
                }
            });
        }
    }
}
