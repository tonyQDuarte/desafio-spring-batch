package com.github.aqd.desafios.spring.batch.configs.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.database.JpaItemWriter;

import com.github.aqd.desafios.spring.batch.models.Movie;
import com.github.aqd.desafios.spring.batch.models.Producer;
import com.github.aqd.desafios.spring.batch.models.QProducer;
import com.github.aqd.desafios.spring.batch.models.QStudio;
import com.github.aqd.desafios.spring.batch.models.Studio;
import com.github.aqd.desafios.spring.batch.repositories.MovieRepository;
import com.github.aqd.desafios.spring.batch.repositories.ProducerRepository;
import com.github.aqd.desafios.spring.batch.repositories.StudioRepository;

import lombok.Setter;

/**
 * 
 * @author antonio.duarte
 *
 */
@Setter
public class MovieItemWriter extends JpaItemWriter<Movie> {

	StudioRepository studioRepository;
	ProducerRepository producerRepository;
	MovieRepository movieRepository;

	@Override
	public void write(List<? extends Movie> items) {
		items.forEach(item -> {
			
			List<Studio> studios = item.getStudios();
			List<Producer> producers = item.getProducers();

			item.setProducers(null);
			item.setStudios(null);
			
			item = movieRepository.save(item);

			item.setStudios(studios);
			item.setProducers(producers);
			
			saveStudios(item);
			saveProducers(item);
			
		});
	}

	private void saveStudios(Movie movie) {
		List<Studio> studios = new ArrayList<>();
		movie.getStudios().forEach(item -> {
			if(studioExist(item)) 
				item = studioRepository.findByName(item.getName());
			item.getMovies().add(movie);
			studios.add(studioRepository.save(item));
		});
		
		movie.setStudios(studios);
	}
	
	private Boolean studioExist(Studio studio) {
		QStudio qStudio = QStudio.studio;
		return studioRepository.exists(qStudio.name.eq(studio.getName()));
	}
	
	private void saveProducers(Movie movie) {
		List<Producer> producers = new ArrayList<>();
		movie.getProducers().forEach(item -> {
			if(producerExist(item))
				item = producerRepository.findByName(item.getName());
			item.getMovies().add(movie);
			producers.add(producerRepository.save(item));
		});
		
		movie.setProducers(producers);
	}
	
	private Boolean producerExist(Producer producer) {
		QProducer qProducer = QProducer.producer;
		return producerRepository.exists(qProducer.name.eq(producer.getName()));
	}
	
}
