package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.repository.ItemVendaRepository;
import br.ufc.mobile.vendasfacil.service.ItemVendaService;

@Service
public class ItemVendaServiceImpl implements ItemVendaService{

	@Autowired
	ItemVendaRepository itemVendaRepo;
	
	@Override
	public ItemVenda save(ItemVenda itemVenda) {
	    if(itemVenda.getId() == null)
	        itemVenda.setId(UUID.randomUUID().toString());
	    
	    return itemVendaRepo.save(itemVenda);
	}

	@Override
	public ItemVenda update(ItemVenda itemVenda) {
	    return itemVendaRepo.save(itemVenda);
	}

	@Override
	public boolean delete(ItemVenda itemVenda) {
	    itemVendaRepo.delete(itemVenda);
	    
	    if(itemVendaRepo.findById(itemVenda.getId()) != null)
	        return true;
	    
	    return false;
	}

	@Override
	public List<ItemVenda> findAll(Usuario usuario) {
	    return itemVendaRepo.findAllByUsuario(usuario);
	}

	@Override
	public Optional<ItemVenda> findById(String id) {
	    return itemVendaRepo.findById(id);
	}

}
