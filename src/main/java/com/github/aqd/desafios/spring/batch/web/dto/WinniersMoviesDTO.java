package com.github.aqd.desafios.spring.batch.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author antonio.duarte
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WinniersMoviesDTO {

	List<WinningMovieDTO> min;
	List<WinningMovieDTO> max;
	
}
