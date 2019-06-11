package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.presenter.ListaVendasPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.ListaVendasPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerListaVendasAdapter;

public class ListaVendasActivity extends AppCompatActivity implements VendasFacilView.ViewMaster<Venda>{

    private RecyclerView recyclerVendas;
    private ListaVendasPresenter mPresenter;
    private RecyclerListaVendasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vendas);

        mPresenter = new ListaVendasPresenterImpl(this);

        setUpToolbar();
        setUpListFornecedores();
    }

    @Override
    protected void onResume() {
        mPresenter.loadAdapterData();
        super.onResume();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpListFornecedores() {
        recyclerVendas = findViewById(R.id.activity_lista_vendas_recycler_vendas);
        recyclerVendas.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerListaVendasAdapter();
        recyclerVendas.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                final Venda venda = adapter.getItem(viewHolder.getAdapterPosition());

                AlertDialog.Builder builder = new AlertDialog.Builder(ListaVendasActivity.this);
                builder.setMessage("Deseja realmente remover a venda " + venda.getId() + "?")
                        .setTitle("Remover Venda")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.delete(venda);
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        })
                        .create().show();
            }
        }).attachToRecyclerView(recyclerVendas);

        mPresenter.loadAdapterData();
    }

    public void novaVenda(View view){
        startActivity(new Intent(this, VendasActivity.class));
    }

    @Override
    public void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAdapter(List<Venda> dados) {
        adapter.setDados(dados);
        adapter.notifyDataSetChanged();
    }

}
