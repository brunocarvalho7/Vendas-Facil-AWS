package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.presenter.VendasPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.VendasPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerItemVendaAdapter;

public class VendasActivity extends AppCompatActivity implements
        VendasClienteDialog.OnClienteSelectListener,
        VendasProdutosDialog.OnProdutoSelectListener,
        VendasAlterarQuantidadeDialog.OnSetQuantidadeListener,
        VendasFacilView.ViewVendas {

    private Toolbar toolbar;
    private Button buttonCliente, buttonTotal;
    private RecyclerView recyclerItens;
    private RecyclerItemVendaAdapter adapterItens;
    private VendasPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        mPresenter = new VendasPresenterImpl(this, this);

        setUpToolbar();
        setUpButtonClienteETotal();
        setUpListViewItens();
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar_venda);
        setSupportActionBar(toolbar);
    }

    private void setUpButtonClienteETotal() {
        buttonTotal = findViewById(R.id.activity_venda_button_total);
        buttonTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkoutVenda();
            }
        });

        buttonCliente = findViewById(R.id.toolbar_venda_button_cliente);
        buttonCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VendasClienteDialog vendasClienteDialog = new VendasClienteDialog();
                vendasClienteDialog.show(getSupportFragmentManager(), "Vendas-Cliente");
            }
        });
    }

    private void setUpListViewItens() {
        recyclerItens = findViewById(R.id.activity_venda_recycler_itens);
        recyclerItens.setLayoutManager(new LinearLayoutManager(this));

        adapterItens = new RecyclerItemVendaAdapter(mPresenter.getItensVenda());
        recyclerItens.setAdapter(adapterItens);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mPresenter.removerItemVenda(adapterItens.getItem(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerItens);
    }

    public void searchProduto(View view) {
        VendasProdutosDialog vendasProdutosDialog = new VendasProdutosDialog();
        vendasProdutosDialog.show(getSupportFragmentManager(), "Vendas-Produto");
    }

    public void scanBarCode(View view) {
        mPresenter.initScanBarCode();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mPresenter.onScanProdutoResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClienteSelected(Cliente cliente) {
        mPresenter.setCliente(cliente);
    }

    @Override
    public void onProdutoSelected(Produto produto) {
        mPresenter.incluirProduto(produto);
    }

    @Override
    public void onSetQuantidade(ItemVenda itemVenda, Double newQuantidade) {
       mPresenter.setQuantidade(itemVenda, newQuantidade);
    }

    @Override
    public void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyAdapterItensDataSetChanged() {
        adapterItens.notifyDataSetChanged();
    }

    @Override
    public void setButtonTotalText(String newText) {
        buttonTotal.setText(newText);
    }

    @Override
    public void setButtonTotalText(int resid) {
        buttonTotal.setText(resid);
    }

    @Override
    public void setButtonClienteText(String newText) {
        buttonCliente.setText(newText);
    }

    @Override
    public void openVendasPagamentoActivity(Venda venda) {
        Intent it = new Intent(this, VendasPagamentoActivity.class);
        it.putExtra(Venda.KEY, venda);
        startActivity(it);
    }

}
