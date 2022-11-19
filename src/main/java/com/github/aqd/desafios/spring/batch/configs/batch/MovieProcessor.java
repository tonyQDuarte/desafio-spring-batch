package com.github.aqd.desafios.spring.batch.configs.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.github.aqd.desafios.spring.batch.models.Movie;
import com.github.aqd.desafios.spring.batch.models.Producer;
import com.github.aqd.desafios.spring.batch.models.Studio;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author antonio.duarte
 *
 */
@Slf4j
@Service
public class MovieProcessor implements ItemProcessor<MovieItem, Movie> {
	
	@Override
	public Movie process(final MovieItem item) throws Exception {
		
		Movie transformedItem = movieProcess(item);
		
		log.info("Converting ( {} ) into ( {} )", item, transformedItem);
		
		return transformedItem;
	}
	
	private Movie movieProcess(MovieItem item) {
		return Movie
				.builder()
					.year(item.getYear())
					.title(item.getTitle())
					.studios(processStudios(item.getStudios()))
					.producers(processProducers(item.getProducers()))
					.winner("yes".equalsIgnoreCase(item.getWinner()))
				.build();
	}
	
	private List<Producer> processProducers(String data) {
		List<String> items = new ArrayList<>();
		Collections.addAll(items, data.split(","));
		
		if(items.get(items.size() - 1).contains(" and ")) {
			String[] items2 = items.remove(items.size() - 1).split(" and ");
			
			for(String item : items2) {
				if(!item.trim().isEmpty())
					items.add(item);
			}
				
		}
		
		List<Producer> producers = new ArrayList<>();
		items.forEach(item -> {
			item = item.trim();
			if(!item.isEmpty())
				producers.add(new Producer(item));
		});
		
		log.debug("**************************************************");
		log.debug("Data: {}", data);
		log.debug("Producers: {}", items);
		log.debug("Resultado: {}", producers.stream().map(Producer::getName).collect(Collectors.toList()));
		log.debug("**************************************************");

		
		return producers;
	}
	
	private List<Studio> processStudios(String data) {
		List<String> items = Arrays.asList(data.split(","));
		List<Studio> studios = new ArrayList<>();
		
		items.forEach(item -> {
			item = item.trim();
			studios.add(new Studio(item));
		});
		
		log.debug("**************************************************");
		log.debug("Data: {}", data);
		log.debug("Studios: {}", items);
		log.debug("Resultado: {}", studios.stream().map(Studio::getName).collect(Collectors.toList()));
		log.debug("**************************************************");
		
		return studios;
	}
	
}
