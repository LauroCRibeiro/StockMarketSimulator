package com.simulator.model;

public class Sale {
	private Integer id;
	private Investor investor;
	private Company company;

	public Sale(Investor investor, Company company) {
		super();
		this.investor = investor;
		this.company = company;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Investor getInvestor() {
		return investor;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Sale [investor=" + investor.getName() + ", company=" + company.getName() + "]";
	}

}
