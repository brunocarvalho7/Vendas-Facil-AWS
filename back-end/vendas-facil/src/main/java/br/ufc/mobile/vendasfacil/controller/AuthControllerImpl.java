package br.ufc.mobile.vendasfacil.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufc.mobile.vendasfacil.config.JwtTokenProvider;
import br.ufc.mobile.vendasfacil.exception.NotAllowedException;
import br.ufc.mobile.vendasfacil.exception.NotFoundException;
import br.ufc.mobile.vendasfacil.exception.VendasFacilException;
import br.ufc.mobile.vendasfacil.model.AuthenticationRequest;
import br.ufc.mobile.vendasfacil.model.Usuario;
import br.ufc.mobile.vendasfacil.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
public class AuthControllerImpl {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.usuarioRepository.findByEmail(username).getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", "Bearer " + token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Usuario> signup(@RequestBody @Valid Usuario usuario) {
    	
    	if(usuarioRepository.findByEmail(usuario.getEmail()) != null)
    		throw new VendasFacilException("E-mail já cadastrado");
    	
    	usuario.setId(null);
    	usuario.setHabilitado(true);
    	usuario.setPassword(new BCryptPasswordEncoder(12).encode(usuario.getPassword()));
    	
    	Usuario usuarioSaved = usuarioRepository.save(usuario);
    	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    			.path("{usuario}").buildAndExpand(usuarioSaved.getId()).toUri(); 
    	
    	return ResponseEntity.created(location).body(usuarioSaved);
    }
    
    @GetMapping("/{usuario}")
    public ResponseEntity<Usuario> findById(@AuthenticationPrincipal Usuario usuarioAutenticado,
    		@PathVariable Usuario usuario) {
    	if(usuario == null)
    		throw new NotFoundException("Usuário não encontrado");
    	
    	if(!usuarioAutenticado.equals(usuario))
    		throw new NotAllowedException();
    
    	return ResponseEntity.ok(usuario);
    }

}
