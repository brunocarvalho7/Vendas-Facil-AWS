package br.ufc.mobile.vendasfacil.model;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import br.ufc.mobile.vendasfacil.model.enums.Unidade;

public class Produto implements Serializable {

    public static String KEY = "PRODUTO";

    private String id;
    private String descricao;
    private Unidade unidade;
    private String codBarras;
    private Categoria categoria;
    private Double rsCompra;
    private Double rsVenda;
    private Double estoque;
    private Filial filial;

    public Produto(String id, String descricao, Unidade unidade, String codBarras, Categoria categoria,
                   Double rsCompra, Double rsVenda, Double estoque) {
        this.id = id;
        this.descricao = descricao;
        this.unidade = unidade;
        this.codBarras = codBarras;
        this.categoria = categoria;
        this.rsCompra = rsCompra;
        this.rsVenda = rsVenda;
        this.estoque = estoque;
    }

    public Produto() {
        this.estoque = 0.0;
        this.rsCompra = 0.0;
        this.rsVenda = 0.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Double getRsCompra() {
        return rsCompra;
    }

    public void setRsCompra(Double rsCompra) {
        this.rsCompra = rsCompra;
    }

    public Double getRsVenda() {
        return rsVenda;
    }

    public void setRsVenda(Double rsVenda) {
        this.rsVenda = rsVenda;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    @JsonIgnore
    public String getEstoqueText(){
        if(getEstoque() > 0) {
            if (getUnidade().equals(Unidade.UND))
                return "Em estoque: " + String.format("%.0f", getEstoque()) + " " + getUnidade();
            else if(getUnidade().equals(Unidade.KG))
                return "Em estoque: " + String.format("%.3f", getEstoque()) + " " + getUnidade();
        }
        return "Sem estoque";
    }

    @JsonIgnore
    public String getRsVendaText(){
        return "R$ "+String.format("%.2f", getRsVenda());
    }

    @JsonIgnore
    public boolean isValid(){
        return descricao.trim().length() > 0 &&
               unidade != null &&
               rsVenda > 0.0;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", unidade=" + unidade +
                ", codBarras='" + codBarras + '\'' +
                ", categoria=" + categoria +
                ", rsCompra=" + rsCompra +
                ", rsVenda=" + rsVenda +
                ", estoque=" + estoque +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getId().equals(((Produto)obj).getId());
    }
}
