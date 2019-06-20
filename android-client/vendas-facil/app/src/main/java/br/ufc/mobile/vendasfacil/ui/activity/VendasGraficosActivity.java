package br.ufc.mobile.vendasfacil.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.config.RetrofitConfigAuthorization;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.ui.VendasFacilView;
import br.ufc.mobile.vendasfacil.utils.APIUtils;
import br.ufc.mobile.vendasfacil.utils.VendasFacilAuthentication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendasGraficosActivity extends AppCompatActivity implements VendasFacilView.IShowText {

    private static final String TAG = "Relatorios-Semana";

    private List<ReportVendaDTO> reportVendasList;
    private LineChart chartVendas;
    private Toolbar toolbar;
    private RetrofitConfigAuthorization retrofitConfigAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas_graficos);

        chartVendas = findViewById(R.id.chartVendas);
        retrofitConfigAuthorization = APIUtils.getInstance().getRetrofitConfigAuthorization();

        setUpToolbar();
        loadData();
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void loadData(){
        Call<List<ReportVendaDTO>> callReportSemana = this.retrofitConfigAuthorization
                .getReportsService().vendasSemana(VendasFacilAuthentication.getInstance().getFilial().getId());

        callReportSemana.enqueue(new Callback<List<ReportVendaDTO>>() {
            @Override
            public void onResponse(Call<List<ReportVendaDTO>> call, Response<List<ReportVendaDTO>> response) {
                if(response.isSuccessful()){
                    reportVendasList = response.body();
                    setUpChart();
                }else{
                    APIUtils.getInstance().onRequestError(response, VendasGraficosActivity.this);
                }
            }

            @Override
            public void onFailure(Call<List<ReportVendaDTO>> call, Throwable t) {
                APIUtils.getInstance().onRequestFailure(TAG, "Erro ao carregar os relatorios semanais",
                        t, VendasGraficosActivity.this);
            }
        });
    }

    private void setUpChart(){
        chartVendas.getDescription().setEnabled(false);

        chartVendas.getAxisRight().setEnabled(false);

        final String[] datas = new String[reportVendasList.size()];

        Log.i(TAG, "setUpChart: " + reportVendasList);

        ArrayList<Entry> yAxes = new ArrayList<>();
        for(int i=0;i<reportVendasList.size();i++){
            ReportVendaDTO r = reportVendasList.get(i);

            datas[i] = r.getDate();
            yAxes.add(new Entry(Math.nextUp(new Float(i)), new Float(r.getTotal())));
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAxes, "Vendas/dia");


        lineDataSets.add(lineDataSet1);

        chartVendas.setData(new LineData(lineDataSets));

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return datas[(int) value];
            }
        };

        XAxis xAxis = chartVendas.getXAxis();
        chartVendas.invalidate();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    @Override
    public void showText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
