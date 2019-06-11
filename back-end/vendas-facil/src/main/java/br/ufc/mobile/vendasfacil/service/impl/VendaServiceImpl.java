package br.ufc.mobile.vendasfacil.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.repository.VendaRepository;
import br.ufc.mobile.vendasfacil.service.ItemVendaService;
import br.ufc.mobile.vendasfacil.service.VendaService;

@Service
public class VendaServiceImpl implements VendaService{

	@Autowired
	VendaRepository vendaRepo;
	
	@Autowired
	ItemVendaService itemVendaService;
	
	@Override
	public Venda save(Venda venda) {
		if(venda.getId() == null)
	        venda.setId(UUID.randomUUID().toString());
		
		Venda vendaSaved = vendaRepo.save(venda);
		
		for(ItemVenda i : venda.getItens()) {
			i.setVenda(vendaSaved);
			vendaSaved.addItem(itemVendaService.save(i));
		}
	 
	    return vendaRepo.save(venda);
	}

	@Override
	public Venda update(Venda venda) {
	    return vendaRepo.save(venda);
	}

	@Override
	public boolean delete(Venda venda) {
	    vendaRepo.delete(venda);
	    
	    if(vendaRepo.findById(venda.getId()) != null)
	        return true;
	    
	    return false;
	}

	@Override
	public List<Venda> findAll(Usuario usuario) {
	    return vendaRepo.findAllByVendedor(usuario);
	}

	@Override
	public Optional<Venda> findById(String id) {
	    return vendaRepo.findById(id);
	}

}
