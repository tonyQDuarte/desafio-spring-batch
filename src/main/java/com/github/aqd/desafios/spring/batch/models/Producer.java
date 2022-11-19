package com.github.aqd.desafios.spring.batch.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author antonio.duarte
 *
 */
@Entity
@Getter
@Setter
@ToString
@Table(name="producer")
@AttributeOverride(name = "id", column = @Column(name = "producer_id"))
public class Producer extends AbsEntity {

	@Column(name = "name", length = 100, unique = true)
	String name;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(	name = "producer_movie",
				joinColumns = {@JoinColumn(table="producer", name = "producer_id")},
				inverseJoinColumns = {@JoinColumn(table="movie", name = "movie_id")})
	List<Movie> movies = new ArrayList<>();

	public Producer(String name) {
		this();
		this.name = name;
	}

	public Producer() {
		super();
		this.movies = new ArrayList<>();
	}
	
}
