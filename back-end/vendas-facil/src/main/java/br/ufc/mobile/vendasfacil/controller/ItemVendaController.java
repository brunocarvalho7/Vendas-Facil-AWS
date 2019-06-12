package br.ufc.mobile.vendasfacil.controller;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufc.mobile.vendasfacil.exception.NotFoundException;
import br.ufc.mobile.vendasfacil.interfaces.ISimpleController;
import br.ufc.mobile.vendasfacil.model.ItemVenda;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.service.ItemVendaService;

@RestController
@RequestMapping("/api/item-vendas")
public class ItemVendaController implements ISimpleController<ItemVenda>{

	@Autowired
	ItemVendaService itemVendaService;
	
	@Override
	@PostMapping("")
	public ResponseEntity<ItemVenda> save(@Valid @RequestBody ItemVenda itemVenda,
			@AuthenticationPrincipal Usuario usuario){
		ItemVenda itemVendaSaved = itemVendaService.save(itemVenda);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{itemVenda}").buildAndExpand(itemVendaSaved.getId()).toUri();
		return ResponseEntity.created(location).body(itemVendaSaved);
	}
	
	@Override
	@PutMapping("/{itemVenda}")
	public ResponseEntity<ItemVenda> update(@PathVariable ItemVenda itemVenda, 
			@RequestBody @Valid ItemVenda itemVendaAtualizar){
		if(itemVenda == null)
			throw new NotFoundException("ItemVenda não localizado");
		
		return ResponseEntity.ok(itemVendaService.update(itemVendaAtualizar));
	}

	@Override
	@GetMapping("")
	public ResponseEntity<Collection<ItemVenda>> findAll(@AuthenticationPrincipal Usuario usuario) {
		return ResponseEntity.ok(itemVendaService.findAll(usuario));
	}

	@Override
	@GetMapping("/{itemVenda}")
	public ResponseEntity<ItemVenda> findById(@PathVariable ItemVenda itemVenda) {
		if(itemVenda == null)
			throw new NotFoundException("ItemVenda não localizado");
		
		return ResponseEntity.ok(itemVenda);
	}

	@Override
	@DeleteMapping("/{itemVenda}")
	public ResponseEntity<Boolean> delete(@PathVariable ItemVenda itemVenda) {
		if(itemVenda == null)
			throw new NotFoundException("ItemVenda não localizado");
		
		return ResponseEntity.ok(itemVendaService.delete(itemVenda));
	}
	
}