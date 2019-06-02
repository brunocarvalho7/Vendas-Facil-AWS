package br.ufc.mobile.vendasfacil.service;

import java.util.Optional;

import br.ufc.mobile.vendasfacil.interfaces.ISimpleService;
import br.ufc.mobile.vendasfacil.model.Produto;

public interface ProdutoService extends ISimpleService<Produto>{
	
	Optional<Produto> findByCodBarras(String codBarras);

}
