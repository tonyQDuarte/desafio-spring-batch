package com.github.aqd.desafios.spring.batch.configs.batch;

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
public class MovieItem {

	Integer year;
	String title;
	String studios;
	String producers;
	String winner;
	
}
