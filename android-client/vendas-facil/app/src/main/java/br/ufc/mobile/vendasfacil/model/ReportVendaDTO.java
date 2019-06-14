package br.ufc.mobile.vendasfacil.model;

public class ReportVendaDTO {

	private Integer qtd;
	private String date;
	private Double total;

	public ReportVendaDTO() {
	}

	public ReportVendaDTO(Integer qtd, String date, Double total) {
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
		return date;
	}

	public void setDate(String date) {
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
