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
	Integer menor = 100000;
	Integer maior = 0;
	@Autowired
	private MoviesRepository moviesRepository;
	@ApiOperation(value = "Retorna os produtores com maior e menor intervalo na premiação do Golden Raspberry Awards!")
	@ApiResponse(code = 200, message = "Retorna o resultado.")
	
	@GetMapping(value="/MajorAndMinor", produces="application/json")
	public ResponseEntity<Award> findMajorAndMinor() {
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
					if (exist == false) {
						listProducer.add(new Producer(movie.getId(), utils.firstToUpercase(producerString)));
					}

				}

			}
		}

		for (Producer producer : listProducer) {
			producerCheck = new Producer();
			producerCheck = producer;
			menor = 100000;
			maior = 0;
			for (Movies movie : listMovies) {
				if (movie.getProducer().contains(producer.getProducer())) {
					if (movie.getYear() < menor) {
						menor = movie.getYear();
						producerCheck.setPreviousWin(menor);
					}
					if (movie.getYear() > maior) {
						maior = movie.getYear();
						producerCheck.setFollowingWin(maior);
					}
				}
			}
			if (producerCheck.getPreviousWin() != producerCheck.getFollowingWin()) {
				listProducersIndividual.add(producerCheck);
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

		return ResponseEntity.ok(award);
	}

}
