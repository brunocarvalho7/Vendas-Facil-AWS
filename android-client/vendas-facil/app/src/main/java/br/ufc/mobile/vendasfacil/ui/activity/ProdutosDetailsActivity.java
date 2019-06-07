package br.ufc.mobile.vendasfacil.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.repository.CategoriaRepository;
import br.ufc.mobile.vendasfacil.model.Categoria;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.model.enums.Unidade;
import br.ufc.mobile.vendasfacil.presenter.ProdutosDetailsPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.ProdutosDetailsPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.View;

public class ProdutosDetailsActivity extends AppCompatActivity implements View.ViewDetails<Produto> {

    private Produto p;
    private ProdutosDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_details);

        presenter = new ProdutosDetailsPresenterImpl(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().get(Produto.KEY) != null) {
            p = (Produto) getIntent().getExtras().get(Produto.KEY);
            setTitle("Editar produto");
        }
        else {
            p = new Produto();
            setTitle("Cadastrar produto");
        }

        setUpToolbar();
        setUpUnidade();
        setUpCategoria();

        this.bindData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_confirmar:
                presenter.onButtonConfirmClicked();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Produto getData() {
        p.setDescricao( ((TextView)findViewById(R.id.txtProdutoDescricao)).getText().toString() );
        p.setCodBarras( ((TextView)findViewById(R.id.txtProdutoCodBarras)).getText().toString() );
        p.setRsCompra(  Double.parseDouble(((TextView)findViewById(R.id.txtProdutoRsCompra)).getText().toString()) );
        p.setRsVenda(  Double.parseDouble(((TextView)findViewById(R.id.txtProdutoRsVenda)).getText().toString()) );
        return p;
    }

    public void bindData(){
        ((TextView) findViewById(R.id.txtProdutoDescricao)).setText(p.getDescricao());
        ((TextView) findViewById(R.id.txtProdutoCodBarras)).setText(p.getCodBarras());
        ((TextView) findViewById(R.id.txtProdutoRsCompra)).setText(
                String.format("%.2f", p.getRsCompra()));
        ((TextView) findViewById(R.id.txtProdutoRsVenda)).setText(
                String.format("%.2f", p.getRsVenda()));

        if(p.getUnidade() != null)
            ((MaterialBetterSpinner) findViewById(R.id.spinnerProdutoUnidade)).setText(p.getUnidade().toString());

        if(p.getCategoria() != null)
        ((AutoCompleteTextView) findViewById(R.id.textCategoria)).setText(p.getCategoria().toString());
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void scanBarCode(android.view.View
                                    view){
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
                ((TextView)findViewById(R.id.txtProdutoCodBarras)).setText(result.getContents());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpCategoria() {
        ArrayAdapter<Categoria> categoriaArrayAdapter = new ArrayAdapter<Categoria>(this,
                android.R.layout.simple_list_item_1, CategoriaRepository.getInstance().getAll());
        final AutoCompleteTextView textCategoria = findViewById(R.id.textCategoria);
        textCategoria.setAdapter(categoriaArrayAdapter);
        textCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                p.setCategoria(((Categoria) textCategoria.getAdapter().getItem(position)));
            }
        });
    }

    private void setUpUnidade() {
        ArrayAdapter<Unidade> arrayAdapter = new ArrayAdapter<Unidade>(this,
                android.R.layout.simple_dropdown_item_1line, Unidade.values());
        MaterialBetterSpinner spinnerUnidade = findViewById(R.id.spinnerProdutoUnidade);
        spinnerUnidade.setAdapter(arrayAdapter);
        spinnerUnidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                p.setUnidade(Unidade.values()[position]);
            }
        });
    }

}
