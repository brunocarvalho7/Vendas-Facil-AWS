package br.ufc.mobile.vendasfacil.model;

public class UsuarioDTO {

    private String email;
    private String password;

    public UsuarioDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
                this.password.trim().length() > 0;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
