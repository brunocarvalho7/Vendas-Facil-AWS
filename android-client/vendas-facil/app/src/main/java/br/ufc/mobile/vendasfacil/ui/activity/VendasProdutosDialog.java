package br.ufc.mobile.vendasfacil.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.dao.DataStatus;
import br.ufc.mobile.vendasfacil.dao.ProdutoDao;
import br.ufc.mobile.vendasfacil.dao.impl.ProdutoDaoImpl;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerProdutosVendaAdapter;

public class VendasProdutosDialog extends AppCompatDialogFragment
        implements RecyclerProdutosVendaAdapter.onRecyclerItemSelectedListener,
        DataStatus<Produto> {

    private OnProdutoSelectListener listener;
    private ProdutoDao daoProduto;
    private View view;
    private RecyclerView recyclerView;
    private RecyclerProdutosVendaAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        daoProduto = new ProdutoDaoImpl(this);

        view = getActivity().getLayoutInflater().inflate(R.layout.activity_venda_produto, null);
        setUpListViewProdutos();
        setUpTextInputSearch();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
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
            listener = (OnProdutoSelectListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must be implement OnProdutoSelectListener");
        }
    }

    private void setUpListViewProdutos() {
        adapter = new RecyclerProdutosVendaAdapter(daoProduto.getAll(), this);
        recyclerView = view.findViewById(R.id.activity_venda_produto_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setUpTextInputSearch() {
        TextInputEditText txtSearch = view.findViewById(R.id.activity_venda_produto_descricao);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }

    @Override
    public void onItemSelected(Produto produto) {
        listener.onProdutoSelected(produto);
        dismiss();
    }

    @Override
    public void DataIsLoaded(List<Produto> dados) {
        adapter.setDados(dados);
        adapter.notifyDataSetChanged();
    }

    public interface OnProdutoSelectListener{
        void onProdutoSelected(Produto produto);
    }

}
