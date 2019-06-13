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
import br.ufc.mobile.vendasfacil.interfaces.ISimpleControllerFindAllByFilialAndUsuario;
import br.ufc.mobile.vendasfacil.model.Filial;
import br.ufc.mobile.vendasfacil.model.ReportVendaDTO;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.model.Venda;
import br.ufc.mobile.vendasfacil.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
public class VendaController implements ISimpleControllerFindAllByFilialAndUsuario<Venda>{

	@Autowired
	VendaService vendaService;
	
	@Override
	@PostMapping("")
	public ResponseEntity<Venda> save(@Valid @RequestBody Venda venda, 
			@AuthenticationPrincipal Usuario usuario, @RequestParam("filial") Filial filial){
		
		if(filial == null)
			throw new NotFoundException("Filial não localizada");
		
		venda.setVendedor(usuario);
		venda.setFilial(filial);
		Venda vendaSaved = vendaService.save(venda);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{venda}").buildAndExpand(vendaSaved.getId()).toUri();
		return ResponseEntity.created(location).body(vendaSaved);
	}
	
	@Override
	@PutMapping("/{venda}")
	public ResponseEntity<Venda> update(@PathVariable Venda venda, 
			@RequestBody @Valid Venda vendaAtualizar){
		if(venda == null)
			throw new NotFoundException("Venda não localizado");
		
		return ResponseEntity.ok(vendaService.update(vendaAtualizar));
	}

	@Override
	@GetMapping("")
	public ResponseEntity<Collection<Venda>> findAll(@RequestParam("filial") Filial filial,
			@AuthenticationPrincipal Usuario usuario) {
		return ResponseEntity.ok(vendaService.findAll(filial, usuario));
	}

	@Override
	@GetMapping("/{venda}")
	public ResponseEntity<Venda> findById(@PathVariable Venda venda) {
		if(venda == null)
			throw new NotFoundException("Venda não localizado");
		
		return ResponseEntity.ok(venda);
	}

	@Override
	@DeleteMapping("/{venda}")
	public ResponseEntity<Boolean> delete(@PathVariable Venda venda) {
		if(venda == null)
			throw new NotFoundException("Venda não localizado");
		
		return ResponseEntity.ok(vendaService.delete(venda));
	}
	
	@GetMapping("/reports/dia")
	public ResponseEntity<ReportVendaDTO> reportByDia(@AuthenticationPrincipal Usuario usuario) {
		return ResponseEntity.ok(vendaService.reportByDia(usuario));
	}
	
	@GetMapping("/reports/semana")
	public ResponseEntity<Collection<ReportVendaDTO>> reportBySemana(@AuthenticationPrincipal Usuario usuario) {
		return ResponseEntity.ok(vendaService.reportBySemana(usuario));
	}
	
	@GetMapping("/reports/mes")
	public ResponseEntity<Collection<ReportVendaDTO>> reportByMes(@AuthenticationPrincipal Usuario usuario) {
		return ResponseEntity.ok(vendaService.reportByMes(usuario));
	}
	
}