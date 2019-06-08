package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerProdutosAdapter;
import br.ufc.mobile.vendasfacil.presenter.ProdutosPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.ProdutosPresenterImpl;

public class ProdutosActivity extends AppCompatActivity implements VendasFacilView.ViewMaster<Produto> {

    private RecyclerView recyclerProdutos;
    private RecyclerProdutosAdapter adapterProdutos;
    private ProdutosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        presenter = new ProdutosPresenterImpl(this);

        setUpToolbar();
        setUpListProdutos();
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
                adapterProdutos.getFilter().filter(s);
                return true;
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpListProdutos() {
        recyclerProdutos = findViewById(R.id.activity_produtos_recycler_produtos);
        recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));

        setUpListProdutosAdapter();
    }

    private void setUpListProdutosAdapter() {
        adapterProdutos = new RecyclerProdutosAdapter(presenter.getProdutos());
        recyclerProdutos.setAdapter(adapterProdutos);
    }

    public void openProdutosDetails(android.view.View view) {
        Intent it = new Intent(this, ProdutosDetailsActivity.class);
        startActivityForResult(it, 0);
    }

    @Override
    public void updateAdapter(List<Produto> dados) {
        adapterProdutos.setDados(dados);
        adapterProdutos.notifyDataSetChanged();
    }
}
