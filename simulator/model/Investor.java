package com.simulator.model;

public class Investor {

	private Integer id;
	private String name;
	private Integer budget;

	public Investor(Integer id, String name, Integer budget) {
		super();
		this.id = id;
		this.name = name;
		this.budget = budget;
	}

	public Investor() {

	}

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

	@Override
	public String toString() {
		return "Investor [id=" + id + ", name=" + name + ", budget=" + budget + "]";
	}

}
