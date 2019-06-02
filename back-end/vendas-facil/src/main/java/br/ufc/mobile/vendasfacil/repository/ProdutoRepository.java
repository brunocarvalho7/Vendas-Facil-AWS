package br.ufc.mobile.vendasfacil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String>{

	Optional<Produto> findByCodBarras(String codBarras);
	
}
