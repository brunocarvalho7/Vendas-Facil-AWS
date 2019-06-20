package br.ufc.mobile.vendasfacil.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportVendaDTO {

	private Integer qtd;
	private Date date;
	private Double total;
	
	public ReportVendaDTO() {
	}
	
	public ReportVendaDTO(Date date) {
		this.qtd = 0;
		this.date = date;
		this.total = 0.0;
	}
	
	public ReportVendaDTO(Integer qtd, Date date, Double total) {
		this.qtd = qtd;
		this.date = date;
		this.total = total;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public String getDate() {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return date + ": R$" + String.format("%.2f", total);
	}
	
}
