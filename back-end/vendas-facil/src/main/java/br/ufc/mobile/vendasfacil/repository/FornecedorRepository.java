package br.ufc.mobile.vendasfacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, String>{

	List<Fornecedor> findAllByFilial(Filial filial);

}
