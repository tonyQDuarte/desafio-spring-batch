package com.github.aqd.desafios.spring.batch.configs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.github.aqd.desafios.spring.batch.configs.batch.JobCompletionNotificationListener;
import com.github.aqd.desafios.spring.batch.configs.batch.MovieItem;
import com.github.aqd.desafios.spring.batch.configs.batch.MovieItemWriter;
import com.github.aqd.desafios.spring.batch.configs.batch.MovieProcessor;
import com.github.aqd.desafios.spring.batch.models.Movie;
import com.github.aqd.desafios.spring.batch.repositories.MovieRepository;
import com.github.aqd.desafios.spring.batch.repositories.ProducerRepository;
import com.github.aqd.desafios.spring.batch.repositories.StudioRepository;

/**
 * 
 * @author antonio.duarte
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    MovieProcessor processor;
    
    @Autowired
    EntityManager entityManager;
    
    @Autowired
    StudioRepository studioRepository;
    
    @Autowired
	ProducerRepository producerRepository;
    
    @Autowired
	MovieRepository movieRepository;
    
    @Value("${arquivos.filmes}")
    String fileInput;
    
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public FlatFileItemReader<MovieItem> reader() {
    	return new FlatFileItemReaderBuilder()
    			.name("movieItemReader")
    			.resource(new ClassPathResource(fileInput))
    			.lineMapper(new DefaultLineMapper<MovieItem>() {{
					  setLineTokenizer(new DelimitedLineTokenizer() {{
						setNames(new String[] { "year",	"title", "studios", "producers", "winner" });
						setDelimiter(";");  
					  }});
					  setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
						  setTargetType(MovieItem.class);
				  }});
    			}})
			  .linesToSkip(1)
			  .delimited()
			  .names(new String[] { "year",	"title", "studios", "producers", "winner" })
			  .fieldSetMapper(new BeanWrapperFieldSetMapper() {{
			      setTargetType(MovieItem.class);
			  }})
			  .build();
    }
    
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importMoviesJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }
    
    @Bean
    public Step step1(ItemWriter<Movie> writer) {
        return stepBuilderFactory.get("step1")
            .<MovieItem, Movie> chunk(10)
            .reader(reader())
            .writer(writer)
            .processor(processor)
            .build();
    }
    
    @Bean
    public MovieItemWriter writer(EntityManagerFactory entityManagerFactory) {
    	MovieItemWriter movieItemWriter = new MovieItemWriter();
    	movieItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
    	movieItemWriter.setMovieRepository(movieRepository);
    	movieItemWriter.setProducerRepository(producerRepository);
    	movieItemWriter.setStudioRepository(studioRepository);
    	return movieItemWriter;
    }
    
}
