package br.ufc.mobile.vendasfacil.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
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
    private LocalDateTime data;

	@OneToOne
    private Cliente cliente;
    
    @OneToMany(mappedBy="venda", orphanRemoval=true)
    @JsonManagedReference
    private List<ItemVenda> itens;
    
    @NotNull
    private double total;
    
    @NotNull
    private FormaPagamento formaPagamento;

    public Venda() {
        this.data = LocalDateTime.now();
        this.itens = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
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
    
    private void calcularTotal(){
        this.total = 0;
        for(ItemVenda i:this.itens){
            this.total += i.getTotal();
        }
    }
}
