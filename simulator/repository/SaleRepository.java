package com.simulator.repository;

import java.util.List;

import com.simulator.model.Sale;

public interface SaleRepository {
	public void save(Sale sale);

	public List<Sale> list();

	public void deleteAll();
	
	public Integer countSaleShare(Integer company_id);
}