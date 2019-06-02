package br.ufc.mobile.vendasfacil.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.ufc.mobile.vendasfacil.model.enums.Unidade;

@Entity
public class Produto {
	
	@Id
	private String id;
	
	@NotNull
    private String descricao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    private Unidade unidade;
	
    private String codBarras;
    
    @OneToOne
    private Categoria categoria;
    
    private Double rsCompra;
    
    @NotNull
    private Double rsVenda;
    
    private Double estoque;

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
}
