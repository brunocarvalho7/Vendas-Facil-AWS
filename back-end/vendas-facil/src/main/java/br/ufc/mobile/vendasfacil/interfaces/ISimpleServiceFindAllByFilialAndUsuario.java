package br.ufc.mobile.vendasfacil.interfaces;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Usuario;

public interface ISimpleServiceFindAllByFilialAndUsuario<T> extends ISimpleService<T> {

	List<T> findAll(Filial filial, Usuario usuario);
	
}
