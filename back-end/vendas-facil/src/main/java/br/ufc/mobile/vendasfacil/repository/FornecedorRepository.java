package br.ufc.mobile.vendasfacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.mobile.vendasfacil.model.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, String>{

}
