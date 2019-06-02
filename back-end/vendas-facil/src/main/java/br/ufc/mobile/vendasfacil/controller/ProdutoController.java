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
import br.ufc.mobile.vendasfacil.exception.VendasFacilException;
import br.ufc.mobile.vendasfacil.interfaces.ISimpleController;
import br.ufc.mobile.vendasfacil.model.Produto;
import br.ufc.mobile.vendasfacil.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController implements ISimpleController<Produto>{

	@Autowired
	ProdutoService produtoService;
	
	@Override
	@PostMapping("")
	public ResponseEntity<Produto> save(@Valid @RequestBody Produto produto){
		Produto produtoSaved = produtoService.save(produto);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{produto}").buildAndExpand(produtoSaved.getId()).toUri();
		return ResponseEntity.created(location).body(produtoSaved);
	}
	
	@Override
	@PutMapping("/{produto}")
	public ResponseEntity<Produto> update(@PathVariable Produto produto, 
			@RequestBody @Valid Produto produtoAtualizar){
		if(produto == null)
			throw new NotFoundException("Produto não localizado");
		
		return ResponseEntity.ok(produtoService.update(produtoAtualizar));
	}

	@Override
	@GetMapping("")
	public ResponseEntity<Collection<Produto>> findAll() {
		return ResponseEntity.ok(produtoService.findAll());
	}

	@Override
	@GetMapping("/{produto}")
	public ResponseEntity<Produto> findById(@PathVariable Produto produto) {
		if(produto == null)
			throw new NotFoundException("Produto não localizado");
		
		return ResponseEntity.ok(produto);
	}

	
	@GetMapping("/barcode/{codBarras}")
	public ResponseEntity<Produto> findById(@PathVariable String codBarras) {
		if(codBarras == null)
			throw new VendasFacilException("Código de barras inválido");
		
		Produto produto = produtoService.findByCodBarras(codBarras).orElseThrow( 
				() -> new NotFoundException("Produto não localizado"));
		
		return ResponseEntity.ok(produto);
	}

	@Override
	@DeleteMapping("/{produto}")
	public ResponseEntity<Boolean> delete(@PathVariable Produto produto) {
		if(produto == null)
			throw new NotFoundException("Produto não localizado");
		
		return ResponseEntity.ok(produtoService.delete(produto));
	}
	
}
