package com.fhs.mensa.datastorage.dataclasses;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** the foot class */
@Entity
@Table(name = "TBLFOOD")
public class Foot {
	
	/** the id */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long	 id;
	/** name of the foot */
	@Column(name = "NAME")
	private String	 name;
	
	/** the tags */
	@OneToMany
	@JoinColumn(name = "fk_tag")
	private Set<Tag>	tags;
	
	/** the day, when its up to date */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVE_DAY", nullable = false)
	private Calendar	activeDay;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Foot [id=" + id + ", name=" + name + "]";
	}
	
	public Set<Tag> getTags() {
		return tags;
	}
	
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	public void setActiveDay(Calendar activeDay) {
		this.activeDay = activeDay;
	}
	
	public Calendar getActiveDay() {
		return activeDay;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((activeDay == null) ? 0 : activeDay.hashCode());
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    result = prime * result + ((tags == null) ? 0 : tags.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    Foot other = (Foot) obj;
	    if (activeDay == null) {
		    if (other.activeDay != null)
			    return false;
	    } else if (!activeDay.equals(other.activeDay))
		    return false;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    if (name == null) {
		    if (other.name != null)
			    return false;
	    } else if (!name.equals(other.name))
		    return false;
	    if (tags == null) {
		    if (other.tags != null)
			    return false;
	    } else if (!tags.equals(other.tags))
		    return false;
	    return true;
    }
	
	
	
}
