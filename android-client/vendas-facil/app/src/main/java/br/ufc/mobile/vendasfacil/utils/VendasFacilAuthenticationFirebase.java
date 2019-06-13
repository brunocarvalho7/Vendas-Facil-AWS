package br.ufc.mobile.vendasfacil.utils;

import com.google.firebase.auth.FirebaseUser;

import br.ufc.mobile.vendasfacil.model.Filial;

public class VendasFacilAuthenticationFirebase {

    private static VendasFacilAuthenticationFirebase instance;

    private String token;
    private FirebaseUser user;
    private String username;
    private Filial filial;

    private VendasFacilAuthenticationFirebase(){

    }

    public static VendasFacilAuthenticationFirebase getInstance(){
        if(instance == null)
            instance = new VendasFacilAuthenticationFirebase();

        return instance;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setUserAuthenticated(FirebaseUser user){
        this.user = user;
    }

    public FirebaseUser getUserAuthenticated(){
        return user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setFilial(Filial filial){
        this.filial = filial;
    }

    public Filial getFilial(){
        return filial;
    }
}
