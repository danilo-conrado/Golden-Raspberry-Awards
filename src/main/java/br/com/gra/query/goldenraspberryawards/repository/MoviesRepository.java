package br.com.gra.query.goldenraspberryawards.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gra.query.goldenraspberryawards.entity.Movies;

public interface MoviesRepository extends JpaRepository<Movies,Long>{
	List<Movies> findByWinnerOrderByIdAsc(boolean isWinner);
}
