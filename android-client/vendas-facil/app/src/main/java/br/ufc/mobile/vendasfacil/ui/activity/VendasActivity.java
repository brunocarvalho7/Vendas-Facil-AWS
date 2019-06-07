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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.dao.ClienteDao;
import br.ufc.mobile.vendasfacil.dao.DataStatus;
import br.ufc.mobile.vendasfacil.dao.ProdutoDao;
import br.ufc.mobile.vendasfacil.dao.impl.ClienteDaoImpl;
import br.ufc.mobile.vendasfacil.dao.impl.ProdutoDaoImpl;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerItemVendaAdapter;

public class VendasActivity extends AppCompatActivity
        implements VendasClienteDialog.OnClienteSelectListener,
        VendasProdutosDialog.OnProdutoSelectListener,
        VendasAlterarQuantidadeDialog.OnSetQuantidadeListener, DataStatus<Cliente> {

    private Toolbar toolbar;
    private Button buttonCliente, buttonTotal;
    private RecyclerView recyclerItens;
    private RecyclerItemVendaAdapter adapterItens;
    private Venda venda;
    private ProdutoDao produtoDao;
    private ClienteDao clienteDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);
        venda = new Venda();
        produtoDao = new ProdutoDaoImpl(null);
        clienteDao = new ClienteDaoImpl(this);

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
                if(venda.getItens().size() == 0) {
                    Toast.makeText(VendasActivity.this,
                            "Insira itens na venda para continuar",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Intent it = new Intent(VendasActivity.this, VendasPagamentoActivity.class);
                    it.putExtra(Venda.KEY, venda);
                    startActivity(it);
                }
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

        adapterItens = new RecyclerItemVendaAdapter(this.venda.getItens());
        recyclerItens.setAdapter(adapterItens);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ItemVenda itemVenda = adapterItens.getItem(viewHolder.getAdapterPosition());
                venda.remover(itemVenda);
                totalizarItens();
            }
        }).attachToRecyclerView(recyclerItens);
    }

    public void searchProduto(View view) {
        VendasProdutosDialog vendasProdutosDialog = new VendasProdutosDialog();
        vendasProdutosDialog.show(getSupportFragmentManager(), "Vendas-Produto");
    }

    public void scanBarCode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
        integrator.setPrompt("Novo produto");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                Produto p = produtoDao.getByBarCode(result.getContents());
                if(p != null)
                    incluirProduto(p);
                else
                    Toast.makeText(this, "Produto não localizado!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClienteSelected(Cliente cliente) {
        if(cliente != null){
            this.venda.setCliente(cliente);
            buttonCliente.setText(cliente.getNome());
        }
    }

    @Override
    public void onProdutoSelected(Produto produto) {
        incluirProduto(produto);
    }

    private void incluirProduto(Produto produto){
        this.venda.incluir(produto);
        totalizarItens();
    }

    private void totalizarItens(){
        adapterItens.notifyDataSetChanged();
        if(this.venda.getItens().size() == 0)
            buttonTotal.setText(R.string.activity_venda_sem_itens);
        else if(this.venda.getItens().size() == 1)
            buttonTotal.setText(this.venda.getItens().size() + " item = R$ " + this.venda.getTotalText());
        else
            buttonTotal.setText(this.venda.getItens().size() + " itens = R$ " + this.venda.getTotalText());
    }

    @Override
    public void onSetQuantidade(ItemVenda itemVenda, Double newQuantidade) {
        if(itemVenda != null){
            itemVenda.setQtd(newQuantidade);
        }
        this.totalizarItens();
    }

    @Override
    public void DataIsLoaded(List<Cliente> dados) {
        if(venda.getCliente() == null) {
            onClienteSelected(clienteDao.getClientePadrao());
        }
    }
}
