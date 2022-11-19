package com.github.aqd.desafios.spring.batch.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.github.aqd.desafios.spring.batch.models.QStudio;
import com.github.aqd.desafios.spring.batch.models.Studio;

/**
 * 
 * @author antonio.duarte
 *
 */
public interface StudioRepository 
	extends JpaRepository<Studio, Long>, 
			QuerydslPredicateExecutor<Studio>, 
			QuerydslBinderCustomizer<QStudio> {
	
	@Override
	default void customize(QuerydslBindings bindings, QStudio root) {
		
	}
	
	@Query("SELECT "
			+ "movie.studios "
		+ "FROM "
			+ "Movie movie "
		+ "WHERE "
			+ "movie.id = ?1")
	List<Studio> findByMovieId(Long movieId);
	
	Studio findByName(String name);
}
