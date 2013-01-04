package com.fhs.mensaproductrecomentation.data;

public class FootRecord {
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mean == null) ? 0 : mean.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tagSum == null) ? 0 : tagSum.hashCode());
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FootRecord)) {
			return false;
		}
		FootRecord other = (FootRecord) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (mean == null) {
			if (other.mean != null) {
				return false;
			}
		} else if (!mean.equals(other.mean)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (tagSum == null) {
			if (other.tagSum != null) {
				return false;
			}
		} else if (!tagSum.equals(other.tagSum)) {
			return false;
		}
		return true;
	}
	/**the name of the foot*/
	public String	name;
	
	/** the mean */
	public Float	mean;
	/** the footId */
	public Long	 id;
	/** the tagsum for this food */
	public Short	tagSum;
	
	@Override
	public String toString() {
		return "FootRecord [name=" + name + ", mean=" + mean + ", id=" + id + ", tagSum=" + tagSum + "]";
	}
	
}
