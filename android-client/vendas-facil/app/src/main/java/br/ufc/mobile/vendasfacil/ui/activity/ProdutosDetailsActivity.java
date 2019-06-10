package br.ufc.mobile.vendasfacil.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.repository.CategoriaRepository;
import br.ufc.mobile.vendasfacil.model.Categoria;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.model.enums.Unidade;
import br.ufc.mobile.vendasfacil.presenter.ProdutosDetailsPresenter;
import br.ufc.mobile.vendasfacil.presenter.impl.ProdutosDetailsPresenterImpl;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosDetailsActivity extends AppCompatActivity implements VendasFacilView.ViewDetails<Produto> {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE  = 1001;
    private Produto p;
    private ProdutosDetailsPresenter presenter;
    private ImageView imageView;
    private Uri image_uri;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_details);

        presenter = new ProdutosDetailsPresenterImpl(this);
        retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();

        if(getIntent().getExtras() != null && getIntent().getExtras().get(Produto.KEY) != null) {
            p = (Produto) getIntent().getExtras().get(Produto.KEY);
            setTitle("Editar produto");
        }
        else {
            p = new Produto();
            setTitle("Cadastrar produto");
        }

        imageView = findViewById(R.id.activity_produtos_details_image);

        setUpToolbar();
        setUpUnidade();

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
        if(requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK){
            Log.i("ProdutosDetails", "onActivityResult: "+image_uri.getPath());
            imageView.setImageURI(image_uri);

            String filePath = getRealPathFromURIPath(image_uri, ProdutosDetailsActivity.this);
            Log.i("ProdutosDetails", "onActivityResult: " + filePath);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

            Call<Map<String, String>> callUpload = this.retrofitConfigAuthorization.getProdutoService().uploadPhoto(p, fileToUpload);
            callUpload.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(ProdutosDetailsActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                    }else{
                        APIUtils.getInstance().onRequestError(response, ProdutosDetailsActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    APIUtils.getInstance().onRequestFailure("Teste", "Erro ", t, ProdutosDetailsActivity.this);
                }
            });

        }else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() != null) {
                    ((TextView) findViewById(R.id.txtProdutoCodBarras)).setText(result.getContents());
                }
            }
        }

        //super.onActivityResult(requestCode, resultCode, data);
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

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
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

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
