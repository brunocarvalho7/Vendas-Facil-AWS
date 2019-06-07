package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.presenter.ClientesPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.ClientesPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.View;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerClientesAdapter;

public class ClientesActivity extends AppCompatActivity implements View.ViewMaster<Cliente> {

    private RecyclerView recyclerClientes;
    private ClientesPresenter presenter;
    private RecyclerClientesAdapter adapterClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ClientesPresenterImpl(this);

        setContentView(R.layout.activity_clientes);
        setUpToolbar();
        setUpListClientes();
    }

    @Override
    protected void onResume() {
        setUpListProdutosAdapter();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pesquisa, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            setUpSearchView(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpSearchView(MenuItem item) {
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterClientes.getFilter().filter(s);
                return true;
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpListClientes() {
        recyclerClientes = findViewById(R.id.activity_clientes_recycler_clientes);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));

        setUpListProdutosAdapter();
    }

    private void setUpListProdutosAdapter() {
        adapterClientes = new RecyclerClientesAdapter(presenter.getClientes());
        recyclerClientes.setAdapter(adapterClientes);
    }

    public void openClienteDetails(android.view.View view) {
        Intent it = new Intent(this, ClientesDetailsActivity.class);
        startActivityForResult(it, 0);
    }

    @Override
    public void updateAdapter(List<Cliente> dados) {
        adapterClientes.setDados(dados);
        adapterClientes.notifyDataSetChanged();
    }
}
