package br.ufc.mobile.vendasfacil.service;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.model.Venda;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReportsService {

    @GET("vendas/reports/dia")
    Call<ReportVendaDTO> vendasDia(@Query("filial") Integer filial);

    @GET("vendas/reports/semana")
    Call<List<ReportVendaDTO>> vendasSemana(@Query("filial") Integer filial);

    @GET("vendas/reports/mes")
    Call<ReportVendaDTO> vendasMes(@Query("filial") Integer filial);

}
