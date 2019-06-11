package br.ufc.mobile.vendasfacil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Categoria implements Serializable {

    private String id;
    private String descricao;

    public Categoria(){
    }

    public Categoria(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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

    @JsonIgnore
    public boolean isValid(){
        return descricao.trim().length() > 0;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
