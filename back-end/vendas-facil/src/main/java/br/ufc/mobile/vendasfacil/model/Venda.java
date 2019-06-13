package br.ufc.mobile.vendasfacil.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.ufc.mobile.vendasfacil.model.enums.FormaPagamento;

@Entity
public class Venda {
	
	@Id
	private String id;
	
	@NotNull
    private Date data;

	@OneToOne
    private Cliente cliente;
    
    @OneToMany(mappedBy="venda", orphanRemoval=true)
    @JsonManagedReference
    private List<ItemVenda> itens;
    
    @NotNull
    private double total;
    
    @NotNull
    private FormaPagamento formaPagamento;
    
    @ManyToOne
    @JoinColumn(name="vendedor_id")
    private Usuario vendedor;
    
    @ManyToOne
    @JoinColumn(name="filial_id")
    private Filial filial;
    
    public Venda() {
        this.data = new Date(System.currentTimeMillis());
        this.itens = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void addItem(ItemVenda item) {
        if(this.itens == null)
        	this.itens = new ArrayList<>();
        
        this.itens.add(item);
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

    public void remover(ItemVenda i){
        this.itens.remove(i);
        this.calcularTotal();
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public FormaPagamento getFormaPagamento(){
        return formaPagamento;
    }
    
    public void setVendedor(Usuario vendedor) {
    	this.vendedor = vendedor;
    }
    
    public Usuario getVendedor() {
    	return vendedor;
    }
    
    public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}
    
    private void calcularTotal(){
        this.total = 0;
        for(ItemVenda i:this.itens){
            this.total += i.getTotal();
        }
    }
}
