package br.ufc.mobile.vendasfacil.interfaces;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Usuario;

public interface ISimpleServiceFindAllByUsuario<T> extends ISimpleService<T>{

	List<T> findAll(Usuario usuario);
	
}
