package br.ufc.mobile.vendasfacil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.repository.UsuarioRepository;
import br.ufc.mobile.vendasfacil.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
