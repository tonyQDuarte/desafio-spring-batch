package com.github.aqd.desafios.spring.batch.web.api;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.aqd.desafios.spring.batch.services.MovieService;
import com.github.aqd.desafios.spring.batch.web.dto.WinniersMoviesDTO;
import com.github.aqd.desafios.spring.batch.web.dto.WinningMovieDTO;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 
 * @author antonio.duarte
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "movies")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MovieResource {

	MovieService service;
	
	@GetMapping("winners/smallest")
	public ResponseEntity<List<WinningMovieDTO>> getSmallest() {
		return ok(service.getMinInteval());
	}
	
	@GetMapping("winners/greater")
	public ResponseEntity<List<WinningMovieDTO>> getGreater() {
		return ok(service.getMaxInteval());
	}
	
	@GetMapping("winners/smallest-and-greater-interval")
	public ResponseEntity<WinniersMoviesDTO> getWinniers() {
		return ok(service.getWinniers());
	}
	
}
