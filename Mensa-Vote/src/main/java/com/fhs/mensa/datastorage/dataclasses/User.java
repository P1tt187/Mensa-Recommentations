package com.fhs.mensa.datastorage.dataclasses;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import javax.persistence.Table;

@Entity
@Table(name = "TBLUSER")
public class User {
	/** the id */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long	            id;
	
	/** the userid */
	@Column(name = "USERID", nullable = false, unique = true)
	private String	            userID;
	
	/** the votes */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	// @JoinColumn(name = "choise_fk")
	private List<UserChoise>	votes;
	
	/**the favoritecount*/
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	// @JoinColumn(name = "favoritecount_fk", nullable = true)
	private List<FavoriteCount>	favoriteCount;
	
	public User() {
		votes = new LinkedList<>();
		favoriteCount = new LinkedList<>();
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public List<UserChoise> getVotes() {
		return votes;
	}
	
	public void setVotes(List<UserChoise> votes) {
		this.votes = votes;
	}
	
	public List<FavoriteCount> getFavoriteCount() {
		return favoriteCount;
	}
	
	public void setFavoriteCount(List<FavoriteCount> favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
	
	
	@Override
    public String toString() {
	    return "User [id=" + id + ", userID=" + userID + ", votes=" + votes + ", favoriteCount=" + favoriteCount + "]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((votes == null) ? 0 : votes.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		if (votes == null) {
			if (other.votes != null)
				return false;
		} else if (!votes.equals(other.votes))
			return false;
		return true;
	}
	
}
