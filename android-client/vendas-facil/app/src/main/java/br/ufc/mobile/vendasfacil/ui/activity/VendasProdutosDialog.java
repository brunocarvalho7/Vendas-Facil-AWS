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
import android.widget.Toast;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerProdutosVendaAdapter;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendasProdutosDialog extends AppCompatDialogFragment
        implements RecyclerProdutosVendaAdapter.onRecyclerItemSelectedListener, VendasFacilView.IShowText{

    private static final String TAG = "Vendas-produto";

    private OnProdutoSelectListener listener;
    private View view;
    private RecyclerView recyclerView;
    private RecyclerProdutosVendaAdapter adapter;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
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
        adapter = new RecyclerProdutosVendaAdapter(this);
        recyclerView = view.findViewById(R.id.activity_venda_produto_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        this.loadData();
    }

    private void loadData(){
        Call<List<Produto>> callFindAll = this.retrofitConfigAuthorization
                .getProdutoService().findAll(VendasFacilAuthentication.getInstance().getFilial().getId());

        callFindAll.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if(response.isSuccessful()){
                    adapter.setDados(response.body());
                    adapter.notifyDataSetChanged();
                }else{
                    APIUtils.getInstance().onRequestError(response, VendasProdutosDialog.this);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, APIUtils.MSG_ERRO_LOCALIZAR_TODOS,
                        t, VendasProdutosDialog.this);
            }
        });
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
    public void showText(String s) {
        Toast.makeText(getContext(), "Erro ao tentar localizar os clientes", Toast.LENGTH_SHORT).show();
    }

    public interface OnProdutoSelectListener{
        void onProdutoSelected(Produto produto);
    }

}
