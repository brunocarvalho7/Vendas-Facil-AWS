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
import br.ufc.mobile.vendasfacil.interfaces.ISimpleControllerFindAll;
import br.ufc.mobile.vendasfacil.model.Categoria;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController implements ISimpleControllerFindAll<Categoria>{

	@Autowired
	private CategoriaService categoriaService;
	
	@Override
	@PostMapping("")
	public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria,
			@AuthenticationPrincipal Usuario usuario) {
		Categoria categoriaSaved = categoriaService.save(categoria);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{fornecedor}").buildAndExpand(categoriaSaved.getId()).toUri();
		return ResponseEntity.created(location).body(categoriaSaved);
	}

	@Override
	@PutMapping("/{categoria}")
	public ResponseEntity<Categoria> update(@PathVariable Categoria categoria, 
			@RequestBody @Valid Categoria categoriaAtualizar) {
		if(categoria == null)
			throw new NotFoundException("Categoria não localizada");
		
		return ResponseEntity.ok(categoriaService.update(categoriaAtualizar));
	}

	@Override
	@GetMapping("")
	public ResponseEntity<Collection<Categoria>> findAll() {
		return ResponseEntity.ok(categoriaService.findAll());
	}

	@Override
	@GetMapping("/{categoria}")
	public ResponseEntity<Categoria> findById(@PathVariable Categoria categoria) {
		if(categoria == null)
			throw new NotFoundException("Categoria não localizada");
		
		return ResponseEntity.ok(categoria);
	}

	@Override
	@DeleteMapping("/{categoria}")
	public ResponseEntity<Boolean> delete(@PathVariable Categoria categoria) {
		if(categoria == null)
			throw new NotFoundException("Categoria não localizada");
		
		return ResponseEntity.ok(categoriaService.delete(categoria));
	}

}
