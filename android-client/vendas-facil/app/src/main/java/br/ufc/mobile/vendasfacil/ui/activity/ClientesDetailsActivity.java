package br.ufc.mobile.vendasfacil.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.presenter.ClientesDetailsPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.ClientesDetailsPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.View;

public class ClientesDetailsActivity extends AppCompatActivity  implements View.ViewDetails<Cliente> {

    private Cliente c;
    private ClientesDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_details);

        presenter = new ClientesDetailsPresenterImpl(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().get(Cliente.KEY) != null) {
            c = (Cliente) getIntent().getExtras().get(Cliente.KEY);
            setTitle("Editar cliente");
        }
        else {
            c = new Cliente();
            setTitle("Cadastrar cliente");
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
    public Cliente getData() {
        c.setNome( ((TextView) findViewById(R.id.activity_clientes_details_nome)).getText().toString() );
        c.setEndereco( ((TextView) findViewById(R.id.activity_clientes_details_endereco)).getText().toString() );
        c.setTelefone( ((TextView) findViewById(R.id.activity_clientes_details_telefone)).getText().toString() );

        return c;
    }

    public void bindData(){
        if(c.getNome() != null)
            ((TextView) findViewById(R.id.activity_clientes_details_nome)).setText(c.getNome());

        if(c.getEndereco() != null)
            ((TextView) findViewById(R.id.activity_clientes_details_endereco)).setText(c.getEndereco());

        if(c.getTelefone() != null)
            ((TextView) findViewById(R.id.activity_clientes_details_telefone)).setText(c.getTelefone());
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
