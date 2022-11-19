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
			value	= "SELECT  "
						+ "MIN(WM.INTERVALO), "
						+ "WM.PRODUCER, "
						+ "WM.PREVIOUSWIN, "
						+ "WM.FOLLOWINGWIN "
					+ "FROM "
						+ "WINNING_MOVIES WM "
					+ "GROUP BY "
						+ "WM.INTERVALO HAVING MIN(WM.INTERVALO) = (SELECT MIN(WM2.INTERVALO) FROM WINNING_MOVIES WM2) "
		 	)
	List<Object[]> getMinInterval();
	
	@Query(	nativeQuery = true,
			value	= "SELECT  "
						+ "MAX(WM.INTERVALO), "
						+ "WM.PRODUCER, "
						+ "WM.PREVIOUSWIN, "
						+ "WM.FOLLOWINGWIN "
					+ "FROM "
						+ "WINNING_MOVIES WM "
					+ "GROUP BY "
						+ "WM.INTERVALO HAVING MAX(WM.INTERVALO) = (SELECT MAX(WM2.INTERVALO) FROM WINNING_MOVIES WM2) "
		 	)
	List<Object[]> getMaxInterval();

	@Query("FROM Movie ")
	List<Movie> listarTodos();
}
