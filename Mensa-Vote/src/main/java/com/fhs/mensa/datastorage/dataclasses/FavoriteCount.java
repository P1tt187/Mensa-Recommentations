package com.fhs.mensa.datastorage.dataclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** counts how often a tag was used */
@Entity
@Table(name = "TBLFAVCOUNT")
public class FavoriteCount {
	@Override
    public String toString() {
	    return "FavoriteCount [id=" + id + ", tag=" + tag + ", value=" + value + "]";
    }

	/** the id */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long	id;
	
	/** the tag */
	@OneToOne
	@JoinColumn(name = "fk_tag")
	private Tag	    tag;
	
	/** the count */
	@Column(name = "VALUE")
	private Short	value;
	/** the user */
	@ManyToOne
	@JoinColumn(name = "fk_favcount", nullable = false)
	private User	user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Tag getTag() {
		return tag;
	}
	
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public Short getValue() {
	    return value;
    }
	
	public void setValue(Short value) {
	    this.value = value;
    }
	
	public void incrementCount() {
		if (value == null) {
			value = 0;
		}
		value++;
	}
	
	public void decrementCount() {
		if (value == null) {
			value = 0;
			
		} else if (value > 0) {
			value--;
		}
		
	}
	
}
