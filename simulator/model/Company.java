package com.simulator.model;

import java.util.List;

public class Company {
	private Integer id;
	private String name;
	private Integer numberOfShares;
	private Integer sharePrice;
	private List<Sale> sales;

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

	public Integer getNumberOfShares() {
		return numberOfShares;
	}

	public void setNumberOfShares(Integer numberOfShares) {
		this.numberOfShares = numberOfShares;
	}

	public Integer getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(Integer sharePrice) {
		this.sharePrice = sharePrice;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", numberOfShares=" + numberOfShares + ", sharePrice="
				+ sharePrice + "]";
	}

}
