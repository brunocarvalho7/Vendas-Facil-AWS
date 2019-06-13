package br.ufc.mobile.vendasfacil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Fornecedor implements Serializable {

    public static String KEY = "FORNECEDOR";

    private String id;
    private String nome;
    private String telefone;
    private String vendedor;
    private Filial filial;


    public Fornecedor() {
    }

    public Fornecedor(String id, String nome, String telefone, String vendedor) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.vendedor = vendedor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    @JsonIgnore
    public boolean isValid(){
        return nome.trim().length() > 0;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", Vendedor='" + vendedor + '\'' +
                '}';
    }
}
