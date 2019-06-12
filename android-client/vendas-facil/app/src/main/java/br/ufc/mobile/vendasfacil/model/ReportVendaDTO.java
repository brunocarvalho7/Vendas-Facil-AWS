package br.ufc.mobile.vendasfacil.model;

import java.util.Date;

public class ReportVendaDTO {

	private Date date;
	private Double total;
	
	public ReportVendaDTO(Date date, Double total) {
		this.date = date;
		this.total = total;
	}

	public Date getDate() {
		return date;
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
