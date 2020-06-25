package br.com.jonatas.apicursoangular.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonatas.apicursoangular.repository.CursoEntity;
import br.com.jonatas.apicursoangular.repository.CursoRepository;

@RestController
@RequestMapping("/curso")
@CrossOrigin
public class CursoController {

	public static final Logger logger = LoggerFactory.getLogger(CursoController.class);

	@Autowired
	public CursoRepository cursoRepository;

	@GetMapping
	public ResponseEntity<?> findAll() {
		try {
			logger.info("Acessando função de listar cursos");
			return new ResponseEntity<>(cursoRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro em listar cursos: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> getByCodigo(@PathVariable(name = "codigo") Integer codigo) {
		try {
			logger.info("Acessando função de busca por codigo de curso");
			Optional<CursoEntity> curso = Optional.ofNullable(cursoRepository.findByCodigo(codigo));
			if (!curso.isPresent()) {
				logger.info("Codigo de curso não cadastrado: " + codigo);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(curso.get(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro na busca de curso por codigo: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> deleteByCodigo(@PathVariable(name = "codigo") Integer codigo) {
		try {
			logger.info("Acessando função de exclusão de curso por codigo");
			Optional<CursoEntity> curso = Optional.ofNullable(cursoRepository.findByCodigo(codigo));
			logger.info("teste");
			if (!curso.isPresent()) {
				logger.info("Codigo de curso não cadastrado: " + codigo);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			cursoRepository.deleteById(curso.get().getId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro na exclusão de curso por codigo: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody CursoEntity curso) {
		try {
			logger.info("Acessando função de cadastro de curso");
			cursoRepository.save(curso);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Erro no cadastro de curso: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<?> put(@PathVariable(name = "codigo") Integer codigo, @RequestBody CursoEntity curso) {
		try {
			logger.info("Acessando função de alteração de curso");
			Optional<CursoEntity> entity = Optional.ofNullable(cursoRepository.findByCodigo(codigo));
			if (!entity.isPresent()) {
				logger.info("Codigo de curso não cadastrado: " + codigo);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			curso.setId(entity.get().getId());
			cursoRepository.save(curso);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Erro na alteração de curso: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
