package com.github.aqd.desafios.spring.batch.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.github.aqd.desafios.spring.batch.models.Movie;
import com.github.aqd.desafios.spring.batch.models.QMovie;

/**
 * 
 * @author antonio.duarte
 *
 */
public interface MovieRepository 
extends JpaRepository<Movie, Long>, 
		QuerydslPredicateExecutor<Movie>, 
		QuerydslBinderCustomizer<QMovie> {
	
	@Override
	default void customize(QuerydslBindings bindings, QMovie root) {
		
	}
	
	@Query(	nativeQuery = true,
			value	= "SELECT "
					    + "WP.PRODUCER, "
					    + "WP.PREVIOUSWIN, "
					    + "WP.FOLLOWINGWIN, "
					    + "WP.INTERVALO "
					+ "FROM "
					    + "WINNING_PRODUCERS WP "
					+ "WHERE "
					    + "WP.INTERVALO = (SELECT MIN(WP2.INTERVALO) FROM WINNING_PRODUCERS WP2)"
		 	)
	List<Object[]> getMinInterval();
	
	@Query(	nativeQuery = true,
			value	= "SELECT "
					    + "WP.PRODUCER, "
					    + "WP.PREVIOUSWIN, "
					    + "WP.FOLLOWINGWIN, "
					    + "WP.INTERVALO "
					+ "FROM "
					    + "WINNING_PRODUCERS WP "
					+ "WHERE "
					    + "WP.INTERVALO = (SELECT MAX(WP2.INTERVALO) FROM WINNING_PRODUCERS WP2)"
		 	)
	List<Object[]> getMaxInterval();
	
	@Query("FROM Movie ")
	List<Movie> listarTodos();
	
}
