package br.ufc.mobile.vendasfacil.interfaces;

import java.util.List;
import java.util.Optional;

import br.ufc.mobile.vendasfacil.model.Usuario;

public interface ISimpleService<T> {

	T save(T obj);
	T update(T obj);
	boolean delete(T obj);
	List<T> findAll(Usuario usuario);
	Optional<T> findById(String id);
	
}
