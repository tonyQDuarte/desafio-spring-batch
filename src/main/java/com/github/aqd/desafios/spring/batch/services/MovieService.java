package com.github.aqd.desafios.spring.batch.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.aqd.desafios.spring.batch.repositories.MovieRepository;
import com.github.aqd.desafios.spring.batch.web.dto.WinniersMoviesDTO;
import com.github.aqd.desafios.spring.batch.web.dto.WinningMovieDTO;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 
 * @author antonio.duarte
 *
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieService {

	@Autowired
	MovieRepository repository;
	
	public WinniersMoviesDTO getWinniers() {
		return WinniersMoviesDTO
				.builder()
					.min(getMinInteval())
					.max(getMaxInteval())
				.build();
	}
	
	public List<WinningMovieDTO> getMinInteval() {
		List<Object[]> movies = repository.getMinInterval();
		List<WinningMovieDTO> winningMovies = new ArrayList<>();
		if(movies != null) {
			movies.forEach(item -> {
				winningMovies.add(WinningMovieDTO
									.builder()
										.interval((Integer) item[0])
										.producer((String) item[1])
										.previousWin((Integer) item[2])
										.followingWin((Integer) item[3])
									.build());
				
			});
		}
		
		return winningMovies;
	}
	
	public List<WinningMovieDTO> getMaxInteval() {
		List<Object[]> movies = repository.getMaxInterval();
		List<WinningMovieDTO> winningMovies = new ArrayList<>();
		if(movies != null) {
			movies.forEach(item -> {
				winningMovies.add(WinningMovieDTO
									.builder()
										.interval((Integer) item[0])
										.producer((String) item[1])
										.previousWin((Integer) item[2])
										.followingWin((Integer) item[3])
									.build());
			});
		}
		
		return winningMovies;
	}
	
}
