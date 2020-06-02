package br.com.munif.cursos.treinamento.api;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.munif.cursos.treinamento.entidades.Cidade;
import br.com.munif.cursos.treinamento.entidades.Estado;
import br.com.munif.cursos.treinamento.entidades.Pais;
import br.com.munif.cursos.treinamento.repositorios.CidadeRepository;

@RestController
@RequestMapping("/api/cidades")
public class CidadeApi {
	
	/// API <-------> SERVICE <------> REPOSITORY <---------> BANCO
	/// API <------- HORRIVEL -------> REPOSITORY <---------> BANCO
	
	@Autowired
	private CidadeRepository respositorioCidade;
	
	
	
	@GetMapping
	public List<Cidade> consultaTodos(){
		List<Cidade> todas = respositorioCidade.findAll();
		return todas;
	}

	@GetMapping(value = "/{id}")
    public ResponseEntity<Cidade> consultaUm(@PathVariable  String id) {
		Optional<Cidade> cid=respositorioCidade.findById(id);
		if (cid.isPresent()) {
		   return  ResponseEntity.ok(cid.get());
		}
		return ResponseEntity.notFound().build();
    }
	
	@DeleteMapping(value = "/{id}")
    public ResponseEntity<Cidade> apagaUm(@PathVariable  String id) {
		Optional<Cidade> cid=respositorioCidade.findById(id);
		if (!cid.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		respositorioCidade.delete(cid.get());
		return  ResponseEntity.ok(cid.get());
    }
	
    @PostMapping
    public ResponseEntity<Cidade> novo(@RequestBody Cidade nova) {
    	Cidade cidadeSalva= respositorioCidade.save(nova);
        return new ResponseEntity <>(cidadeSalva, HttpStatus.CREATED);
    }
	
    @PutMapping(value = "/{id}")
    public ResponseEntity<Cidade> alterar(@PathVariable("id") String id, @RequestBody Cidade novoValorCidade) {
		Optional<Cidade> valorVelhoDeCidade=respositorioCidade.findById(id);
		if (!valorVelhoDeCidade.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		novoValorCidade.setId(id);
		novoValorCidade.setCd(valorVelhoDeCidade.get().getCd());
		novoValorCidade.setUd(new Date());
    	Cidade cidadeSalva= respositorioCidade.save(novoValorCidade);
        return new ResponseEntity <>(cidadeSalva, HttpStatus.OK);
    }
	
	
	
	
	
}