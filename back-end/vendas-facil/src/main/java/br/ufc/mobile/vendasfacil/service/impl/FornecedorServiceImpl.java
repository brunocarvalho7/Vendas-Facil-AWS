package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.repository.FornecedorRepository;
import br.ufc.mobile.vendasfacil.service.FornecedorService;

@Service
public class FornecedorServiceImpl implements FornecedorService {

	@Autowired
	FornecedorRepository fornecedorRepo;
	
	@Override
	public Fornecedor save(Fornecedor fornecedor) {
		if(fornecedor.getId() == null)
			fornecedor.setId(UUID.randomUUID().toString());
		
		return fornecedorRepo.save(fornecedor);
	}

	@Override
	public Fornecedor update(Fornecedor fornecedor) {
		return fornecedorRepo.save(fornecedor);
	}

	@Override
	public boolean delete(Fornecedor fornecedor) {
		fornecedorRepo.delete(fornecedor);
		
		if(fornecedorRepo.findById(fornecedor.getId()) != null)
			return true;
		
		return false;
	}

	@Override
	public List<Fornecedor> findAll(Filial filial) {
		return fornecedorRepo.findAllByFilial(filial);
	}

	@Override
	public Optional<Fornecedor> findById(String id) {
		return fornecedorRepo.findById(id);
	}

}
