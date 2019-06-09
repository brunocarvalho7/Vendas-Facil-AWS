package br.ufc.mobile.vendasfacil.model;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ItemVenda implements Serializable {

    public static final String KEY = "itemVenda";

    private String id;
    private Produto produto;
    private double qtd;
    private double rs;

    public ItemVenda() {
    }

    public ItemVenda(Produto produto, double qtd) {
        this.produto = produto;
        this.qtd = qtd;
        this.rs = produto.getRsVenda();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public double getRs() {
        return rs;
    }

    public double getTotal(){
        return getQtd() * getProduto().getRsVenda();
    }

    @JsonIgnore
    public String getQtdText(){
       return String.format("%.3f", getQtd());
    }

    @JsonIgnore
    public String getTotalText(){
        return "R$ "+String.format("%.2f", getQtd() * getProduto().getRsVenda());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.produto.equals(((ItemVenda)obj).getProduto());
    }
}
