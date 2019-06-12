package br.ufc.mobile.vendasfacil.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import br.ufc.mobile.vendasfacil.R;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;

public class VendasGraficosActivity extends AppCompatActivity {

    private LineChart chartVendas;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas_graficos);

        chartVendas = findViewById(R.id.chartVendas);

        setUpToolbar();
        setUpChart();
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpChart(){
        chartVendas.getDescription().setEnabled(false);

        chartVendas.getAxisRight().setEnabled(false);

        ArrayList<ReportVendaDTO> reportMock = new ArrayList<>();
        reportMock.add(new ReportVendaDTO(new Date(System.currentTimeMillis()), 12000.0));
        reportMock.add(new ReportVendaDTO(new Date(System.currentTimeMillis()), 13000.0));
        reportMock.add(new ReportVendaDTO(new Date(System.currentTimeMillis()), 9000.0));
        reportMock.add(new ReportVendaDTO(new Date(System.currentTimeMillis()), 15000.0));

        final String[] datas = new String[reportMock.size()];

        ArrayList<Entry> yAxes = new ArrayList<>();
        for(int i=0;i<reportMock.size();i++){
            ReportVendaDTO r = reportMock.get(i);

            datas[i] = new SimpleDateFormat("dd/MM/yyyy").format(r.getDate());
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
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }
}
