package com.simulator.service;

import java.util.List;

import com.simulator.dto.CapitalCompanyDTO;
import com.simulator.model.Company;

public interface CompanyService {
	public void save(Company company);

	public List<Company> list();

	public List<Company> listCompaniesWithAvailableShares();

	public List<CapitalCompanyDTO> listCompaniesWithHighestCapital();

	public List<CapitalCompanyDTO> listCompaniesWithLeastCapital();

	public void deleteAll();

	public boolean hasShareAvailable(Integer id);
}