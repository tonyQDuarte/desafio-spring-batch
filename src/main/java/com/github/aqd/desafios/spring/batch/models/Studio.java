package com.github.aqd.desafios.spring.batch.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="studio")
@AttributeOverride(name = "id", column = @Column(name = "studio_id"))
public class Studio extends AbsEntity {

	@Column(name = "name", length = 100, unique = true)
	String name;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "studio_movie",
				joinColumns = {@JoinColumn(table="studio", name = "studio_id")},
				inverseJoinColumns = {@JoinColumn(table="movie", name = "movie_id")})
	List<Movie> movies;
	
	public Studio(String name) {
		this();
		this.name = name;
	}

	public Studio() {
		super();
		this.movies = new ArrayList<>();
	}
	
}
