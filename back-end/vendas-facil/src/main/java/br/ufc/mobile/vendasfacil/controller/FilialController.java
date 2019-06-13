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
import br.ufc.mobile.vendasfacil.interfaces.ISimpleControllerFindAll;
import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.service.FilialService;

@RestController
@RequestMapping("/api/filiais")
public class FilialController implements ISimpleControllerFindAll<Filial>{

	@Autowired
	private FilialService filialService;
	
    @Override
	@PostMapping("")
	public ResponseEntity<Filial> save(@Valid @RequestBody Filial filial,
			@AuthenticationPrincipal Usuario usuario){
		Filial filialSaved = filialService.save(filial);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{filial}").buildAndExpand(filialSaved.getId()).toUri();
		return ResponseEntity.created(location).body(filialSaved);
	}
	
	@Override
	@PutMapping("/{filial}")
	public ResponseEntity<Filial> update(@PathVariable Filial filial, 
			@RequestBody @Valid Filial filialAtualizar){
		if(filial == null)
			throw new NotFoundException("Filial não localizada");
		
		return ResponseEntity.ok(filialService.update(filialAtualizar));
	}

	@Override
	@GetMapping("")
	public ResponseEntity<Collection<Filial>> findAll() {
		return ResponseEntity.ok(filialService.findAll());
	}

	@GetMapping("/findByCoordenadas")
	public ResponseEntity<Filial> findByLongitudeAndLatitude(@RequestParam("latitude") Double latitude, 
			@RequestParam("longitude") Double longitude) {
		
		return ResponseEntity.ok(filialService.findByLongitudeAndLatitude(latitude, longitude));
	}
	
	@Override
	@GetMapping("/{filial}")
	public ResponseEntity<Filial> findById(@PathVariable Filial filial) {
		if(filial == null)
			throw new NotFoundException("Filial não localizada");
		
		return ResponseEntity.ok(filial);
	}

	@Override
	@DeleteMapping("/{filial}")
	public ResponseEntity<Boolean> delete(@PathVariable Filial filial) {
		if(filial == null)
			throw new NotFoundException("Filial não localizada");
		
		return ResponseEntity.ok(filialService.delete(filial));
	}
	
}
