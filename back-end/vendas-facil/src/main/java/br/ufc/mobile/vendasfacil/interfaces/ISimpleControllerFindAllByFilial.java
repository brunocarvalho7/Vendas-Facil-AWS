package br.ufc.mobile.vendasfacil.interfaces;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Usuario;

public interface ISimpleControllerFindAllByFilial<T> extends ISimpleController<T> {
	
	ResponseEntity<T> save(T obj, Usuario usuario, Filial filial);
	ResponseEntity<Collection<T>> findAll(Filial filial);
	
}
