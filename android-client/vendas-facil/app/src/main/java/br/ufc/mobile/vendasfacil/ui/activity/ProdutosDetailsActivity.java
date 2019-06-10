package br.ufc.mobile.vendasfacil.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private static final int REQUEST_TAKE_PHOTO  = 1002;

    private Produto p;
    private ProdutosDetailsPresenter presenter;
    private ImageView imageView;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;
    private String currentPhotoPath;

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
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            resizeImage();
            setPic();
            //galleryAddPic();
            testUpload();
        }else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() != null) {
                    ((TextView) findViewById(R.id.txtProdutoCodBarras)).setText(result.getContents());
                }
            }
        }
    }

    private void testUpload() {
        File file = new File(currentPhotoPath);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        Call<Map<String, String>> callUpload =
                this.retrofitConfigAuthorization.getProdutoService().uploadPhoto(p, mFile);
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Erro: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProdutosDetails", "dispatchTakePictureIntent: " + ex.getMessage() );
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "br.ufc.mobile.vendasfacil",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
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

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void resizeImage(){
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);

        Log.e("ProdutosDetails", "Width: " + bitmap.getWidth());
        Log.e("ProdutosDetails", "Height: " + bitmap.getHeight());

        double newHeigth = ((400.0 / bitmap.getHeight()) * bitmap.getHeight());
        double newWidth = ((400.0 / bitmap.getWidth()) * bitmap.getWidth());

        Log.e("ProdutosDetails", "NewWidth: " + newHeigth);
        Log.e("ProdutosDetails", "NewHeight: " + newWidth);

        Bitmap bmpResized = Bitmap.createScaledBitmap(bitmap, (int) newWidth, (int) newHeigth, true);

        File file = new File(currentPhotoPath);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bmpResized.compress(Bitmap.CompressFormat.JPEG, 100, fOut);


            fOut.flush();
            fOut.close();;
        } catch (IOException e) {
            Log.e("ProdutosDetails", "Error on resizeImage: " + e.getMessage());
        }
    }

    private void setPic() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        imageView.setImageBitmap(bitmap);
        /*// Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        imageView.setImageBitmap(bitmap);*/
    }

}
