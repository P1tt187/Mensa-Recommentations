package com.fhs.mensa.network.messages;



public class Vote implements MensaMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5899591431063466166L;
	private String userId;
	private Short value;
	private Long footId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((footId == null) ? 0 : footId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Vote other = (Vote) obj;
		if (footId == null) {
			if (other.footId != null)
				return false;
		} else if (!footId.equals(other.footId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public Vote(String userId, Short value, Long footId) {
		super();
		this.userId = userId;
		this.value = value;
		this.footId = footId;
	}

	public String getUserId() {
		return userId;
	}

	public Short getValue() {
		return value;
	}

	public Long getFootId() {
		return footId;
	}

}