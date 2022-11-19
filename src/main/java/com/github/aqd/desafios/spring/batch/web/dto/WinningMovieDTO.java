package com.github.aqd.desafios.spring.batch.web.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class WinningMovieDTO {

	String producer;
	Integer previousWin;
	Integer followingWin;
	Integer interval;
	
}
