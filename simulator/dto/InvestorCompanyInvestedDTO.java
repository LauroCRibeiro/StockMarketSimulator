package com.simulator.dto;

public class InvestorCompanyInvestedDTO {
	private Integer id;
	private String name;
	private Integer budget;
	private Integer numberOfCompanies;

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

	public Integer getNumberOfCompanies() {
		return numberOfCompanies;
	}

	public void setNumberOfCompanies(Integer numberOfCompanies) {
		this.numberOfCompanies = numberOfCompanies;
	}

	@Override
	public String toString() {
		return "Investor [id=" + id + ", name=" + name + ", budget=" + budget + ", numberOfCompanies="
				+ numberOfCompanies + "]";
	}

}
