package com.simulator.dto;

public class InvestorNumberOfShareDTO {
	private Integer id;
	private String name;
	private Integer budget;
	private Integer numberOfShares;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Integer getNumberOfShares() {
		return numberOfShares;
	}

	public void setNumberOfShares(Integer numberOfShares) {
		this.numberOfShares = numberOfShares;
	}

	@Override
	public String toString() {
		return "Investor [id=" + id + ", name=" + name + ", budget=" + budget + ", numberOfShares=" + numberOfShares
				+ "]";
	}
}
