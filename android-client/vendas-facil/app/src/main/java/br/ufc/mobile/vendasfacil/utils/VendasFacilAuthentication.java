package br.ufc.mobile.vendasfacil.utils;

import br.ufc.mobile.vendasfacil.model.Filial;

public class VendasFacilAuthentication {

    private static VendasFacilAuthentication instance;

    private String token;
    private String username;
    private Filial filial;

    private VendasFacilAuthentication(){

    }

    public static VendasFacilAuthentication getInstance(){
        if(instance == null)
            instance = new VendasFacilAuthentication();

        return instance;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
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
