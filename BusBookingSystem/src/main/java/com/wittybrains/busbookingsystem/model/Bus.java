package com.wittybrains.busbookingsystem.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="bus")
public class Bus {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String number;
	    private String type;
//	    @ManyToOne
//	    private MyUser user;
	    private String source;
	    private String destination;
	    private String timing;
//	    @OneToMany(mappedBy = "bus")
//	    private Set<Seat> seats;
	    public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getTiming() {
			return timing;
		}

		public void setTiming(String timing) {
			this.timing = timing;
		}

		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getNumber() {
	        return number;
	    }

	    public void setNumber(String number) {
	        this.number = number;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

//	    public MyUser getUser() {
//	        return user;
//	    }
//
//	    public void setUser(MyUser user) {
//	        this.user = user;
//	    }
//	    public Set<Seat> getSeats() {
//	        return seats;
//	    }
//
//	    public void setSeats(Set<Seat> seats) {
//	        this.seats = seats;
//	    }
	}

