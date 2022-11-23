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

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author antonio.duarte
 *
 */
@Entity
@Table(name="movie")
@Builder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "movie_id"))
public class Movie extends AbsEntity {
	
	@Column(name = "\"year\"")
	Integer year;
	
	@Column(name = "title", length = 100)
	String title;
	
	@ManyToMany
	@JoinTable(	name = "movie_studio",
				joinColumns = {@JoinColumn(table="movie", name = "movie_id")},
				inverseJoinColumns = {@JoinColumn(table="studio", name = "studio_id")})
	List<Studio> studios;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "movie_producer",
				joinColumns = {@JoinColumn(table="movie", name = "movie_id")},
				inverseJoinColumns = {@JoinColumn(table="producer", name = "producer_id")})
	List<Producer> producers;
	
	@NotNull
	@Column(name = "winner")
	Boolean winner;

	public Movie(	Long id, 
					Integer year, 
					String title, 
					List<Studio> studios, 
					List<Producer> producers, 
					Boolean winner ) {
		super(id);
		this.year = year;
		this.title = title;
		this.studios = new ArrayList<>();
		this.producers = new ArrayList<>();
		this.winner = winner;
		
		if(studios != null)
			this.studios.addAll(studios);
		
		if(producers != null)
			this.producers.addAll(producers);
		
	}
	
}
