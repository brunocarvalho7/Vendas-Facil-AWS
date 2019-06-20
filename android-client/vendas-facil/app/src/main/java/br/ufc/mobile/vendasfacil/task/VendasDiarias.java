package br.ufc.mobile.vendasfacil.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendasDiarias extends AsyncTask<Void, Void, Reports> {

    private static final String TAG = "Relatorios-dia";

    private VendasFacilView.IShowText mView;
    private ProgressDialog progressDialog;
    private Context mContext;
    private Reports.VendaCalculoListener vendaCalculoListener;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    public VendasDiarias(Context mContext, VendasFacilView.IShowText mView){
        this.mView = mView;
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
        Call<ReportVendaDTO> callVendasDia = this.retrofitConfigAuthorization
                .getReportsService().vendasDia(VendasFacilAuthentication.getInstance().getFilial().getId());

        callVendasDia.enqueue(new Callback<ReportVendaDTO>() {
            @Override
            public void onResponse(Call<ReportVendaDTO> call, Response<ReportVendaDTO> response) {
                if(response.isSuccessful()){
                    Reports report = new Reports();
                    report.setQtd(response.body().getQtd());
                    report.setTotal(response.body().getTotal());

                    onPostExecute(report);
                }else{
                    APIUtils.getInstance().onRequestError(response, mView);
                }
            }

            @Override
            public void onFailure(Call<ReportVendaDTO> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, "Erro ao calcular as vendas do dia",
                        t, mView);
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