package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.repository.ClienteRepository;
import br.ufc.mobile.vendasfacil.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	ClienteRepository clienteRepo;

	@Override
	public Cliente save(Cliente cliente) {
		if(cliente.getId() == null)
			cliente.setId(UUID.randomUUID().toString());
		
		return clienteRepo.save(cliente);
	}

	@Override
	public Cliente update(Cliente cliente) {
		return clienteRepo.save(cliente);
	}

	@Override
	public boolean delete(Cliente cliente) {
		clienteRepo.delete(cliente);
		
		if(clienteRepo.findById(cliente.getId()) != null)
			return true;
		
		return false;
	}

	@Override
	public List<Cliente> findAll(Usuario usuario) {
		return clienteRepo.findAll();
	}

	@Override
	public Optional<Cliente> findById(String id) {
		return clienteRepo.findById(id);
	}
	
}
