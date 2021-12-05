package br.com.gra.query.goldenraspberryawards.entity;

import java.util.ArrayList;
import java.util.List;

public class Award {
	List<Producer> min = new ArrayList<Producer>();
	List<Producer> max = new ArrayList<Producer>();;
	
	public Award(Producer minProducer, Producer maxProducer) {
		this.getMin().add(minProducer);
		this.getMax().add(maxProducer);
	}
	public List<Producer> getMin() {
		return min;
	}
	public void setMin(List<Producer> min) {
		this.min = min;
	}
	public List<Producer> getMax() {
		return max;
	}
	public void setMax(List<Producer> max) {
		this.max = max;
	}
	
	

}
