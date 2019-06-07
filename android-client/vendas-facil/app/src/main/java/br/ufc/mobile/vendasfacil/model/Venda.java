package br.ufc.mobile.vendasfacil.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.ufc.mobile.vendasfacil.model.enums.FormaPagamento;

public class Venda implements Serializable {

    public static final String KEY = "Venda";

    private String id;
    private String data;
    private Cliente cliente;
    private ArrayList<ItemVenda> itens;
    private double total;
    private FormaPagamento formaPagamento;

    public Venda() {
        this.data = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'").format(new Date());
        this.itens = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Exclude
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getClienteKey(){
        return this.cliente.getId();
    }

    public ArrayList<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemVenda> itens) {
        this.itens = itens;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void incluir(Produto p){
        this.incluir(p, 1.0);
    }

    public void incluir(Produto p, Double qtd){
        for(ItemVenda i:this.itens){
            if(i.getProduto().equals(p)) {
                i.setQtd(i.getQtd() + qtd);
                this.calcularTotal();
                return ;
            }
        }
        this.itens.add(new ItemVenda(p, qtd));
        this.calcularTotal();
    }

    private void calcularTotal(){
        this.total = 0;
        for(ItemVenda i:this.itens){
            this.total += i.getTotal();
        }
    }

    public void remover(ItemVenda i){
        this.itens.remove(i);
        this.calcularTotal();
    }

    @Exclude
    public String getTotalText() {
        this.calcularTotal();
        return String.format("%.2f", this.total);
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public FormaPagamento getFormaPagamento(){
        return formaPagamento;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id='" + id + '\'' +
                ", cliente=" + cliente +
                ", itens=" + itens +
                ", total=" + total +
                ", formaPagamento=" + formaPagamento +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.id.equals(((Venda)obj).getId());
    }
}
