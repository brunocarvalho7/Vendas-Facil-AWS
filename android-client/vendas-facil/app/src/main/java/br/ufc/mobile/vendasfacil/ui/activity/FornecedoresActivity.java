package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.presenter.FornecedoresPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.FornecedoresPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.ui.adapter.RecyclerFornecedoresAdapter;

public class FornecedoresActivity extends AppCompatActivity implements VendasFacilView.ViewMaster<Fornecedor>{

    private RecyclerView recyclerFornecedores;
    private FornecedoresPresenter mPresenter;
    private RecyclerFornecedoresAdapter adapterFornecedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fornecedores);

        mPresenter = new FornecedoresPresenterImpl(this);
        setUpToolbar();
        setUpListFornecedores();
    }

    @Override
    protected void onResume() {
        mPresenter.loadAdapterData();
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
                adapterFornecedores.getFilter().filter(s);
                return true;
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpListFornecedores() {
        recyclerFornecedores = findViewById(R.id.activity_fornecedores_recycler_fornecedores);
        recyclerFornecedores.setLayoutManager(new LinearLayoutManager(this));

        adapterFornecedores = new RecyclerFornecedoresAdapter();
        recyclerFornecedores.setAdapter(adapterFornecedores);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                final Fornecedor fornecedor = adapterFornecedores.getItem(viewHolder.getAdapterPosition());

                AlertDialog.Builder builder = new AlertDialog.Builder(FornecedoresActivity.this);
                builder.setMessage("Deseja realmente remover o fornecedor " + fornecedor.getNome() + "?")
                        .setTitle("Remover cliente")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.delete(fornecedor);
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterFornecedores.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        })
                        .create().show();
            }
        }).attachToRecyclerView(recyclerFornecedores);

        mPresenter.loadAdapterData();
    }

    public void openFornecedorDetails(View view) {
        Intent it = new Intent(this, FornecedoresDetailsActivity.class);
        startActivityForResult(it, 0);
    }

    @Override
    public void updateAdapter(List<Fornecedor> dados) {
        adapterFornecedores.setDados(dados);
        adapterFornecedores.notifyDataSetChanged();
    }

    @Override
    public void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
