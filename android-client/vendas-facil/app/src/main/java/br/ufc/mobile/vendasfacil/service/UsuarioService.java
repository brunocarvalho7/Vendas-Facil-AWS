package br.ufc.mobile.vendasfacil.service;

import java.util.Map;

import br.ufc.mobile.vendasfacil.model.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("usuarios/signin")
    Call<Map<String, String>> signin(@Body UsuarioDTO usuarioDTO);

}
