package br.ufc.mobile.vendasfacil.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.model.enums.Unidade;
import br.ufc.mobile.vendasfacil.presenter.ProdutosDetailsPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.ProdutosDetailsPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;

public class ProdutosDetailsActivity extends AppCompatActivity implements VendasFacilView.ViewDetails<Produto> {

    private static final int PERMISSION_CODE = 1000;
    private static final int REQUEST_TAKE_PHOTO  = 1002;

    private Produto p;
    private ProdutosDetailsPresenter presenter;
    private ImageView imageView;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_details);

        imageView = findViewById(R.id.activity_produtos_details_image);
        presenter = new ProdutosDetailsPresenterImpl(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().get(Produto.KEY) != null) {
            p = (Produto) getIntent().getExtras().get(Produto.KEY);
            setTitle("Editar produto");

            loadImage();
        }
        else {
            p = new Produto();
            setTitle("Cadastrar produto");
        }

        setUpToolbar();
        setUpUnidade();

        this.bindData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            presenter.resizeImage(currentPhotoPath);
            setPic();
        }else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() != null) {
                    ((TextView) findViewById(R.id.txtProdutoCodBarras)).setText(result.getContents());
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else{
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
                presenter.onButtonConfirmClicked(currentPhotoPath);
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
        p.setEstoque(  Double.parseDouble(((TextView)findViewById(R.id.txtProdutoEstoque)).getText().toString()) );
        return p;
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

    public void bindData(){
        ((TextView) findViewById(R.id.txtProdutoDescricao)).setText(p.getDescricao());
        ((TextView) findViewById(R.id.txtProdutoCodBarras)).setText(p.getCodBarras());
        ((TextView) findViewById(R.id.txtProdutoRsCompra)).setText(
                String.format("%.2f", p.getRsCompra()));
        ((TextView) findViewById(R.id.txtProdutoRsVenda)).setText(
                String.format("%.2f", p.getRsVenda()));
        ((TextView) findViewById(R.id.txtProdutoEstoque)).setText(
                String.format("%.2f", p.getEstoque()));

        if(p.getUnidade() != null)
            ((MaterialBetterSpinner) findViewById(R.id.spinnerProdutoUnidade)).setText(p.getUnidade().toString());
    }

    public void scanBarCode(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
        integrator.setPrompt("Novo produto");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Erro: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProdutosDetails", "dispatchTakePictureIntent: " + ex.getMessage() );
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "br.ufc.mobile.vendasfacil",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void dispatchTakePictureIntent(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission, PERMISSION_CODE);
            }else{
                openCamera();
            }
        }else{
            openCamera();
        }
    }

    private void setPic() {
        Picasso.get().load(new File(currentPhotoPath)).into(imageView);
    }

    private void loadImage() {
        Picasso
            .get()
            .load("https://vendas-facil.s3.amazonaws.com/produtos/" + p.getId()+".jpg")
            .error(R.drawable.ic_search)
            .into(imageView);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
