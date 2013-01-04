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

/** saves user choises */
@Entity
@Table(name = "TBLUSERCHOISE")
public class UserChoise {
	/** the id */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long	id;
	
	/** the food for which is the vote */
	// @Column(name = "FOOT", nullable = false)
	@OneToOne
	@JoinColumn(name = "foot_fk", nullable = false)
	private Foot	foot;
	/** the vote value */
	@Column(name = "VOTE", nullable = false)
	private Short	vote;
	/** the user */
	@ManyToOne
	@JoinColumn(name = "choise_fk", nullable = false)
	private User	user;
	
	public Short getVote() {
		return vote;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setVote(Short vote) {
		this.vote = vote;
	}
	
	public Foot getFoot() {
		return foot;
	}
	
	public void setFoot(Foot foot) {
		this.foot = foot;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "UserChoise [id=" + id + ", foot=" + foot + ", vote=" + vote + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foot == null) ? 0 : foot.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((vote == null) ? 0 : vote.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserChoise))
			return false;
		UserChoise other = (UserChoise) obj;
		if (foot == null) {
			if (other.foot != null)
				return false;
		} else if (!foot.equals(other.foot))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (vote == null) {
			if (other.vote != null)
				return false;
		} else if (!vote.equals(other.vote))
			return false;
		return true;
	}
	
}
