package br.ufc.mobile.vendasfacil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Cliente implements Serializable {

    public static String KEY = "CLIENTE";

    private String id;
    private String nome;
    private String endereco;
    private String telefone;
    private Filial filial;

    public Cliente() {
    }

    public Cliente(String id, String nome, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        return nome;
    }
}
