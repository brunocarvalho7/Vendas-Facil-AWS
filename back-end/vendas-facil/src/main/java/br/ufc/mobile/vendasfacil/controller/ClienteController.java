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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufc.mobile.vendasfacil.exception.NotFoundException;
import br.ufc.mobile.vendasfacil.interfaces.ISimpleControllerFindAllByFilial;
import br.ufc.mobile.vendasfacil.model.Cliente;
import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController implements ISimpleControllerFindAllByFilial<Cliente>{

	@Autowired
	private ClienteService clienteService;
	
	@Override
	@PostMapping("")
	public ResponseEntity<Cliente> save(@Valid @RequestBody Cliente cliente,
			@AuthenticationPrincipal Usuario usuario, @RequestParam("filial") Filial filial){
		
		if(filial == null)
			throw new NotFoundException("Filial não localizada");
		
		cliente.setFilial(filial);
		Cliente clienteSaved = clienteService.save(cliente);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{cliente}").buildAndExpand(clienteSaved.getId()).toUri();
		return ResponseEntity.created(location).body(clienteSaved);
	}
	
	@Override
	@PutMapping("/{cliente}")
	public ResponseEntity<Cliente> update(@PathVariable Cliente cliente, 
			@RequestBody @Valid Cliente clienteAtualizar){
		if(cliente == null)
			throw new NotFoundException("Cliente não localizado");
		
		return ResponseEntity.ok(clienteService.update(clienteAtualizar));
	}

	@Override
	@GetMapping("")
	public ResponseEntity<Collection<Cliente>> findAll(@RequestParam("filial") Filial filial) {
		if(filial == null)
			throw new NotFoundException("Filial não localizada");
		
		return ResponseEntity.ok(clienteService.findAll(filial));
	}

	@Override
	@GetMapping("/{cliente}")
	public ResponseEntity<Cliente> findById(@PathVariable Cliente cliente) {
		if(cliente == null)
			throw new NotFoundException("Cliente não localizado");
		
		return ResponseEntity.ok(cliente);
	}

	@Override
	@DeleteMapping("/{cliente}")
	public ResponseEntity<Boolean> delete(@PathVariable Cliente cliente) {
		if(cliente == null)
			throw new NotFoundException("Cliente não localizado");
		
		return ResponseEntity.ok(clienteService.delete(cliente));
	}
	
}
