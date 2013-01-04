package com.fhs.mensa.network.messages;

public class FootContent implements MensaMessage {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1348089935630246781L;
	
	/**
	 * 
	 */
	private Long	          id;
	

	
	@Override
    public String toString() {
	    return "FootContent [id=" + id + ", name=" + name + ", mean=" + mean + ", tagSum=" + tagSum + "]";
    }

	private String	name;
	private Float	mean;
	private Short	tagSum;
	
	public FootContent(Long id, String name, Float mean, Short tagSum) {
		super();
		this.id = id;
		this.name = name;
		this.mean = mean;
		this.tagSum = tagSum;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Float getMean() {
		return mean;
	}
	
	public Short getTagSum() {
		return tagSum;
	}
	
}