package br.ufc.mobile.vendasfacil.task;

public class Reports {

    private static String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro"};

    private String label;
    private int qtd;
    private double total;

    public Reports() {
        this.label = "HOJE";
        this.qtd = 0;
        this.total = 0.0;
    }

    public Reports(String label) {
        super();
        this.label = MESES[Integer.parseInt(label) - 1];
    }

    public void incQtd(){
        this.qtd++;
    }

    public void incTotal(double valor){
        this.total += valor;
    }

    public String getLabel(){
        return label.toUpperCase();
    }

    public String getQtdText(){
        if(this.qtd > 1){
            return this.qtd + " VENDAS";
        }else{
            return this.qtd + " VENDA";
        }
    }

    public String getTotalText(){
        return "R$ "+String.format("%.2f", this.total);
    }

    @Override
    public String toString(){
        return getLabel() + ": " + getQtdText() + " -> " + getTotalText();
    }

    public interface VendaCalculoListener{
        void onPostCalculo(Reports report);
    }
}

