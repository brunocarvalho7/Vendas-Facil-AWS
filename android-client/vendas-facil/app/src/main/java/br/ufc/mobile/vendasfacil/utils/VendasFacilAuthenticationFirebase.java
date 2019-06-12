package br.ufc.mobile.vendasfacil.utils;

import com.google.firebase.auth.FirebaseUser;

public class VendasFacilAuthenticationFirebase {

    private static VendasFacilAuthenticationFirebase instance;

    private String token;
    private FirebaseUser user;
    private String username;

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
}
