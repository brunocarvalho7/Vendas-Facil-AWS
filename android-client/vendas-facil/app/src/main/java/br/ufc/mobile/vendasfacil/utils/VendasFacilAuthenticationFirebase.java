package br.ufc.mobile.vendasfacil.utils;

import com.google.firebase.auth.FirebaseUser;

public class VendasFacilAuthenticationFirebase {

    private static VendasFacilAuthenticationFirebase instance;

    private FirebaseUser user;

    private VendasFacilAuthenticationFirebase(){

    }

    public static VendasFacilAuthenticationFirebase getInstance(){
        if(instance == null)
            instance = new VendasFacilAuthenticationFirebase();

        return instance;
    }

    public void setUserAuthenticated(FirebaseUser user){
        this.user = user;
    }

    public FirebaseUser getUserAuthenticated(){
        return user;
    }

}
