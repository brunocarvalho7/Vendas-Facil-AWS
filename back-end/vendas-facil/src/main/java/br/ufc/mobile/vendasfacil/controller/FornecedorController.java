package br.ufc.mobile.vendasfacil.controller;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import br.ufc.mobile.vendasfacil.model.Fornecedor;
import br.ufc.mobile.vendasfacil.service.FornecedorService;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController implements ISimpleController<Fornecedor>{

	@Autowired
	FornecedorService fornecedorService;
	
	@Override
	@PostMapping("")
	public ResponseEntity<Fornecedor> save(@Valid @RequestBody Fornecedor fornecedor) {
		Fornecedor fornecedorSaved = fornecedorService.save(fornecedor);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{fornecedor}").buildAndExpand(fornecedorSaved.getId()).toUri();
		return ResponseEntity.created(location).body(fornecedorSaved);
	}

	@Override
	@PutMapping("/{fornecedor}")
	public ResponseEntity<Fornecedor> update(@PathVariable Fornecedor fornecedor, 
			@RequestBody @Valid Fornecedor fornecedorAtualizar) {
		if(fornecedor == null)
			throw new NotFoundException("Fornecedor não localizado");
		
		return ResponseEntity.ok(fornecedorService.update(fornecedorAtualizar));
	}

	@Override
	@GetMapping("")
	public ResponseEntity<Collection<Fornecedor>> findAll() {
		return ResponseEntity.ok(fornecedorService.findAll());
	}

	@Override
	@GetMapping("/{fornecedor}")
	public ResponseEntity<Fornecedor> findById(@PathVariable Fornecedor fornecedor) {
		if(fornecedor == null)
			throw new NotFoundException("Fornecedor não localizado");
		
		return ResponseEntity.ok(fornecedor);
	}

	@Override
	@DeleteMapping("/{fornecedor}")
	public ResponseEntity<Boolean> delete(@PathVariable Fornecedor fornecedor) {
		if(fornecedor == null)
			throw new NotFoundException("Cliente não localizado");
		
		return ResponseEntity.ok(fornecedorService.delete(fornecedor));
	}

}
