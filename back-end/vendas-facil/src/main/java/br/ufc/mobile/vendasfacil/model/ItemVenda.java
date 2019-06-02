package br.ufc.mobile.vendasfacil.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemVenda {
	
	@Id
	private String id;
	
	@NotNull
	@OneToOne
    private Produto produto;
	
	@NotNull
    private double qtd;
	
	@NotNull
    private double rs;
	
	@ManyToOne
	@JsonBackReference
	private Venda venda;

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
    
    public void setRs(double rs) {
		this.rs = rs;
	}
    
    public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

    @JsonIgnore
    public double getTotal(){
        return getQtd() * getProduto().getRsVenda();
    }
}
