package br.com.gra.query.goldenraspberryawards.controllers.dto;

import br.com.gra.query.goldenraspberryawards.entity.Movies;

public class MoviesDto {

	private Long id;
	private Integer year;
	private String title;
	private String studio;
	private String producer;
	private boolean winner;

	public MoviesDto(Movies movie) {
		this.id = movie.getId();
		this.year = movie.getYear();
		this.title = movie.getTitle();
		this.studio = movie.getStudio();
		this.producer = movie.getStudio();
		this.winner = movie.isWinner();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
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

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

}
