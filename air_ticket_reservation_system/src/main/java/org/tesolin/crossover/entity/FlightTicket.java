package org.tesolin.crossover.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FlightTicket implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3679909999869704383L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Column(nullable = false)
	private Long flight;
	
	private String seat;
	
	@Column(nullable = false)
	private BigDecimal price;
	@Column(nullable = false)
	private TypePerson type;
	
	@Column(nullable = false)
	private String user;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getFlight() {
		return flight;
	}
	public void setFlight(Long flight) {
		this.flight = flight;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public TypePerson getType() {
		return type;
	}
	public void setType(TypePerson type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
