package com.simulator.service.impl;

import java.util.List;

import com.simulator.dto.CapitalCompanyDTO;
import com.simulator.model.Company;
import com.simulator.repository.CompanyRepository;
import com.simulator.repository.impl.CompanyRepositoyImpl;
import com.simulator.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {

	private static CompanyService INSTANCE = null;

	/**
	 * Return singleton instance
	 * 
	 * @return INSTANCE
	 */
	public static CompanyService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CompanyServiceImpl();
		}

		return INSTANCE;
	}

	private CompanyServiceImpl() {
		companyRepositoy = CompanyRepositoyImpl.getInstance();
	}

	private CompanyRepository companyRepositoy;

	public void save(Company company) {
		companyRepositoy.save(company);
		System.out.println(company);
	}

	public List<Company> list() {
		return companyRepositoy.list();
	}

	@Override
	public List<Company> listCompaniesWithAvailableShares() {
		return companyRepositoy.listCompaniesWithAvailableShares();
	}

	@Override
	public List<CapitalCompanyDTO> listCompaniesWithHighestCapital() {
		return companyRepositoy.listCompaniesWithHighestCapital();
	}

	@Override
	public List<CapitalCompanyDTO> listCompaniesWithLeastCapital() {
		return companyRepositoy.listCompaniesWithLeastCapital();
	}

	@Override
	public void deleteAll() {
		companyRepositoy.deleteAll();
	}

	@Override
	public boolean hasShareAvailable(Integer id) {
		return companyRepositoy.hasShareAvailable(id);
	}
}
