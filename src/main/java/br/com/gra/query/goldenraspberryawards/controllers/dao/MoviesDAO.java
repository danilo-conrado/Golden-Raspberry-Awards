package br.com.gra.query.goldenraspberryawards.controllers.dao;

import com.opencsv.bean.CsvBindByName;

import br.com.gra.query.goldenraspberryawards.entity.Movies;

public class MoviesDAO {

	@CsvBindByName(column = "year")
    private String year;

	@CsvBindByName(column = "title")
    private String title;

	@CsvBindByName(column = "studios")
    private String studio;

	@CsvBindByName(column = "producers")
    private String producer;
    
	@CsvBindByName(column = "winner")
    private String winner;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	public Movies toMovie() {
		Movies movie = new Movies();
		if(!this.getYear().matches("[0-9]*")) {
			System.out.println("Verificar o ano de indicacao do titulo: "+this.getTitle()+" no csv.");
			movie.setYear(null);
		}else {
			movie.setYear(Integer.parseInt(this.getYear()));
		}
		
		movie.setTitle(this.getTitle());
		movie.setStudio(this.getStudio());
		movie.setProducer(this.getProducer());
		
		
		if(this.getWinner().equals("yes")) {
		  movie.setWinner(true);	
		}else {
			movie.setWinner(false);
		}

		return movie;
	}
    
    

}
