package br.ufc.mobile.vendasfacil.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    private Integer id;
    private String nome;
    private String email;
    private String password;

    public Usuario() {
    }

    public Usuario(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid(){
        return this.email.trim().length() > 0 &&
                this.password.trim().length() > 0 &&
                this.nome.trim().length() > 0;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Usuario) obj).getId() == this.getId();
    }

}
