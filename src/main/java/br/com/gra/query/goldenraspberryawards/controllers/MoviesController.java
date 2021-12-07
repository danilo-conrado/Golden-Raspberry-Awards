package br.com.gra.query.goldenraspberryawards.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.gra.query.Utils.Utils;
import br.com.gra.query.goldenraspberryawards.entity.Award;
import br.com.gra.query.goldenraspberryawards.entity.Movies;
import br.com.gra.query.goldenraspberryawards.entity.Producer;
import br.com.gra.query.goldenraspberryawards.repository.MoviesRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {
	Gson gson = new GsonBuilder().create();
	Utils utils = new Utils();
	List<Producer> listProducer = new ArrayList<Producer>();
	List<Movies> listMovies = new ArrayList<Movies>();
	List<Producer> listProducersIndividual = new ArrayList<Producer>();
	Producer producerCheck = new Producer();
	Integer smaller = 100000;
	Integer bigger = 0;
	boolean addMin = false;
	boolean addMax = false;
	boolean hasPrevious = false;
	boolean hasFollowin = false;
	@Autowired
	private MoviesRepository moviesRepository;
	@ApiOperation(value = "Retorna os produtores com maior e menor intervalo na premiação do Golden Raspberry Awards!")
	@ApiResponse(code = 200, message = "Retorna o resultado.")
	
	@GetMapping(value="/MajorAndMinor", produces="application/json")
	public ResponseEntity<Award> findMajorAndMinor() throws CloneNotSupportedException {
		listProducersIndividual = new ArrayList<Producer>();
		listMovies = new ArrayList<Movies>();
		listProducer = new ArrayList<Producer>();

		listMovies = moviesRepository.findByWinnerOrderByIdAsc(true);
		for (Movies movie : listMovies) {
			String[] arrayProducerString = utils.fixSpaces(movie.getProducer()).toLowerCase().split(",|\\ and ");
			for (String producerString : arrayProducerString) {
				boolean exist = false;
				if (producerString.trim().length() > 0 || producerString.trim() != "") {
					for (Producer p : listProducer) {
						if (p.getProducer().equals(utils.firstToUpercase(producerString))) {
							exist = true;
						}
					}
					if (!exist) {
						listProducer.add(new Producer(movie.getId(), utils.firstToUpercase(producerString)));
					}

				}

			}
		}

		for (Producer producer : listProducer) {
			producerCheck = new Producer();
			producerCheck = producer.clone();
			smaller = 100000;
			bigger = 0;

			
			for (Movies movie : listMovies) {
				
				if (movie.getProducer().contains(producer.getProducer())) {
					if (movie.getYear() < smaller) {
						hasPrevious = true;
						smaller = movie.getYear();
						producerCheck.setPreviousWin(smaller);
					}
					if (movie.getYear() > bigger) {
						bigger = movie.getYear();
						producerCheck.setFollowingWin(bigger);
						if(producerCheck.getFollowingWin() != producerCheck.getPreviousWin()) {
							hasFollowin = true;
						}
					}
				}
				if(hasPrevious && hasFollowin) {

					listProducersIndividual.add(producerCheck);
					producerCheck = new Producer();
					producerCheck = producer.clone();
					producerCheck.setPreviousWin(bigger);
					smaller = bigger;
					bigger = 0;
					hasFollowin = false;
					
				}
			}

			
		}

		Producer min = new Producer();
		Producer max = new Producer();
		min.setInterval(1000);
		max.setInterval(0);
		for (Producer producerMinMax : listProducersIndividual) {
			if (producerMinMax.getIntervalByYear() < min.getInterval()) {
				min = producerMinMax;
				min.setInterval(producerMinMax.getIntervalByYear());
			}
			if (producerMinMax.getIntervalByYear() > max.getInterval()) {
				max = producerMinMax;
				max.setInterval(producerMinMax.getIntervalByYear());
			}
		}
		Award award = new Award(min, max);
		
		for (Producer producer : listProducersIndividual) {
			producerCheck = new Producer();
			producerCheck = producer;
			addMin = false;
			addMax = false;
			if (min.getInterval() == producer.getIntervalByYear()) {
				for(Producer checkProducerContainMin : award.getMin()) {
					if(!checkProducerContainMin.getProducer().equals(producer.getProducer())
							|| checkProducerContainMin.getProducer().equals(producer.getProducer())
							&& checkProducerContainMin.getPreviousWin() != producer.getPreviousWin()) {
						producer.setIntervalByGetintervalByYear();
						addMin = true;
					}
					
				}
			}
			if (max.getInterval() == producer.getIntervalByYear()) {
				for(Producer checkProducerContainMin : award.getMax()) {
					if(!checkProducerContainMin.getProducer().equals(producer.getProducer())
							|| checkProducerContainMin.getProducer().equals(producer.getProducer()) 
							&&checkProducerContainMin.getPreviousWin() != producer.getPreviousWin()) {
						producer.setIntervalByGetintervalByYear();
						addMax = true;
					}
					
				}
			}
			if(addMin) {
				award.getMin().add(producer);
			}else if(addMax) {
				award.getMax().add(producer);
			}
			
		}

		return ResponseEntity.ok(award);
	}

}
