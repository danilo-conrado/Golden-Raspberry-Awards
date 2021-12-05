package br.com.gra.query.config.database;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import br.com.gra.query.goldenraspberryawards.controllers.dao.MoviesDAO;
import br.com.gra.query.goldenraspberryawards.repository.MoviesRepository;

@Component
public class initialization {
	
	  @Autowired
	  private MoviesRepository moviesRepository;
	  @EventListener(ContextRefreshedEvent.class)
	  
	  public void contextRefreshedEvent() throws IOException, CsvException {
		  try {
	        String fileName = "c:\\gra\\movielist.csv";
	        List<MoviesDAO> listMoviesDAO = new CsvToBeanBuilder<MoviesDAO>(new FileReader(fileName))
													        		.withType(MoviesDAO.class)
													        		.withSeparator(';')
													        		.build().parse();
	        for(MoviesDAO movieDAO : listMoviesDAO) {
	        	moviesRepository.save(movieDAO.toMovie());
	        }
		  }catch (FileNotFoundException e) {
			  throw new FileNotFoundException("Verifique a localização do CSV, deve ficar em \"C:\\gra\\movielist.csv\".");
		}
	  }

}
