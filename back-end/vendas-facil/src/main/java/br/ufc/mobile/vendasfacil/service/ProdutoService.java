package br.ufc.mobile.vendasfacil.service;

import java.util.Optional;

import br.ufc.mobile.vendasfacil.interfaces.ISimpleServiceFindAllByFilial;
import br.ufc.mobile.vendasfacil.model.Produto;

public interface ProdutoService extends ISimpleServiceFindAllByFilial<Produto>{
	
	Optional<Produto> findByCodBarras(String codBarras);

}
