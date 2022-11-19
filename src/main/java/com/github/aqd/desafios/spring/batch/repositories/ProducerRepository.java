package com.github.aqd.desafios.spring.batch.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.github.aqd.desafios.spring.batch.models.Producer;
import com.github.aqd.desafios.spring.batch.models.QProducer;

/**
 * 
 * @author antonio.duarte
 *
 */
public interface ProducerRepository 
	extends JpaRepository<Producer, Long>, 
			QuerydslPredicateExecutor<Producer>, 
			QuerydslBinderCustomizer<QProducer> {
	
	@Override
	default void customize(QuerydslBindings bindings, QProducer root) {
		
	}
	
	@Query("SELECT "
				+ "movie.producers "
			+ "FROM "
				+ "Movie movie "
			+ "WHERE "
				+ "movie.id = ?1")
	List<Producer> findByMovieId(Long movieId);
	
	Producer findByName(String name);
}
