package br.ufc.mobile.vendasfacil.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import retrofit2.Call;

public class VendasDiarias extends AsyncTask<Void, Void, Reports> {

    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private Context mContext;
    private Reports.VendaCalculoListener vendaCalculoListener;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public VendasDiarias(Context mContext){
        this.mContext = mContext;
        this.retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();
    }

    public void setVendaCalculoListener(Reports.VendaCalculoListener vendaCalculoListener){
        this.vendaCalculoListener = vendaCalculoListener;
    }

    @Override
    protected void onPreExecute() {
        if(progressDialog == null)
            progressDialog = new ProgressDialog(mContext);

        progressDialog.setMessage("Calculando as vendas diárias...");
        progressDialog.show();
    }

    @Override
    protected Reports doInBackground(Void... voids) {
        String dataAtual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        mDatabase = FirebaseDatabase.getInstance().getReference("vendas");
        mDatabase.orderByChild("data").startAt(dataAtual).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Reports report = new Reports();

                for(DataSnapshot venda: dataSnapshot.getChildren()){
                    Venda v = venda.getValue(Venda.class);
                    report.incTotal(v.getTotal());
                    report.incQtd();
                }

                onPostExecute(report);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("VendasMensal", "onCancelled: " + databaseError.toString());
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Reports report) {
       if(report != null) {
           Log.i("Relatório", report.toString());

           vendaCalculoListener.onPostCalculo(report);
           progressDialog.dismiss();
       }
    }

}