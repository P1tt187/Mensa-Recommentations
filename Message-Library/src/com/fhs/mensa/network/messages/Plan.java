package com.fhs.mensa.network.messages;

import java.util.List;

public class Plan implements MensaMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8230674218593827432L;
	private List<FootContent> plan;

	public List<FootContent> getPlan() {
		return plan;
	}

	public Plan(List<FootContent> plan) {
		this.plan = plan;
	}
}