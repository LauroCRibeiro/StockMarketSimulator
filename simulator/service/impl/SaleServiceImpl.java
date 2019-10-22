package com.simulator.service.impl;

import java.util.List;

import com.simulator.model.Sale;
import com.simulator.repository.CompanyRepository;
import com.simulator.repository.impl.CompanyRepositoyImpl;
import com.simulator.repository.SaleRepository;
import com.simulator.repository.impl.SaleRepositoyImpl;
import com.simulator.service.SaleService;

public class SaleServiceImpl implements SaleService {

	private static SaleService INSTANCE = null;

	/**
	 * Return singleton instance
	 * 
	 * @return INSTANCE
	 */
	public static SaleService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SaleServiceImpl();
		}

		return INSTANCE;
	}

	private SaleServiceImpl() {
		saleRepositoy = SaleRepositoyImpl.getInstance();
		companyRepository = CompanyRepositoyImpl.getInstance();
	}

	private SaleRepository saleRepositoy;
	private CompanyRepository companyRepository;

	public void save(Sale sale) {
		Integer countSaleShare = saleRepositoy.countSaleShare(sale.getCompany().getId());
		if (countSaleShare == 10) {
			System.out.println("=============================================================================");
			System.out.println("The company " + sale.getCompany().getName() + " has sold 10 shares");
			System.out.println("Incresing " + sale.getCompany().getName() + "'s share price by double");
			companyRepository.updateSharePriceByDouble(sale.getCompany().getId());
			System.out.println("Reducing share price by 2% for any company who hasn't sold any share");
			companyRepository.reduceSharePriceCompanyWithoutSale();
			System.out.println("=============================================================================");
		}
		saleRepositoy.save(sale);
		System.out.println(sale);
	}

	public List<Sale> list() {
		return saleRepositoy.list();
	}

	@Override
	public void deleteAll() {
		saleRepositoy.deleteAll();
	}

}
