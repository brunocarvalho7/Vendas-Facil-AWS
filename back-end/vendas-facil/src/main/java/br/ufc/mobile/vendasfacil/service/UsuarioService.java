package br.ufc.mobile.vendasfacil.service;

import br.ufc.mobile.vendasfacil.model.Usuario;

public interface UsuarioService {

    Usuario findByEmail(String email);

}
