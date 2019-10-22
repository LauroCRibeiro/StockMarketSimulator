package com.simulator.service;

import java.util.List;

import com.simulator.model.Sale;

public interface SaleService {
	public void save(Sale sale);

	public List<Sale> list();

	public void deleteAll();

}