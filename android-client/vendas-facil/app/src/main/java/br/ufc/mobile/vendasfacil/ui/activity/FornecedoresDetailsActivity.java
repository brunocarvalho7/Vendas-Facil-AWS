package br.ufc.mobile.vendasfacil.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.presenter.FornecedoresDetailsPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.FornecedoresDetailsPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;

public class FornecedoresDetailsActivity extends AppCompatActivity implements VendasFacilView.ViewDetails<Fornecedor> {

    private Fornecedor f;
    private FornecedoresDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fornecedores_details);

        presenter = new FornecedoresDetailsPresenterImpl(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().get(Fornecedor.KEY) != null) {
            f = (Fornecedor) getIntent().getExtras().get(Fornecedor.KEY);
            setTitle("Editar fornecedor");
        }
        else {
            f = new Fornecedor();
            setTitle("Cadastrar fornecedor");
        }

        setUpToolbar();
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
    public Fornecedor getData() {
        f.setNome( ((TextView) findViewById(R.id.activity_fornecedores_details_nome)).getText().toString() );
        f.setTelefone( ((TextView) findViewById(R.id.activity_fornecedores_details_telefone)).getText().toString() );
        f.setVendedor( ((TextView) findViewById(R.id.activity_fornecedores_details_vendedor)).getText().toString() );

        return f;
    }

    public void bindData(){
        if(f.getNome() != null)
            ((TextView) findViewById(R.id.activity_fornecedores_details_nome)).setText(f.getNome());

        if(f.getTelefone() != null)
            ((TextView) findViewById(R.id.activity_fornecedores_details_telefone)).setText(f.getTelefone());

        if(f.getVendedor() != null)
            ((TextView) findViewById(R.id.activity_fornecedores_details_vendedor)).setText(f.getVendedor());
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
