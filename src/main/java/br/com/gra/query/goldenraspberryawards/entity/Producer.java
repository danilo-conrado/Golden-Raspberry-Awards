package br.com.gra.query.goldenraspberryawards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Producer implements Cloneable {
	@JsonIgnore
	Long id;
	String producer;
	Integer interval;
	Integer previousWin;
	Integer followingWin;
	
	
	public Producer() {
	}

	public Producer(Long id, String producer) {
		this.id = id;
		this.producer = producer;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Integer getPreviousWin() {
		return previousWin;
	}
	public void setPreviousWin(Integer previousWin) {
		this.previousWin = previousWin;
	}
	public Integer getFollowingWin() {
		return followingWin;
	}
	public void setFollowingWin(Integer followingWin) {
		this.followingWin = followingWin;
	}

	@JsonIgnore
	public Integer getIntervalByYear() {
		return (this.getFollowingWin() - this.getPreviousWin());
	}
	@JsonIgnore
	public void setIntervalByGetintervalByYear() {
		this.interval = (this.getFollowingWin() - this.getPreviousWin());
	}
    @Override
    public Producer clone() throws CloneNotSupportedException {
        return (Producer) super.clone();
    }

	
	

}
