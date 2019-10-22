package com.simulator.service;

import java.util.List;

import com.simulator.dto.InvestorCompanyInvestedDTO;
import com.simulator.dto.InvestorNumberOfShareDTO;
import com.simulator.model.Investor;

public interface InvestorService {
	public void save(Investor investor);

	public List<Investor> list();

	public List<InvestorCompanyInvestedDTO> listInvestedInTheMostCompanies();

	public List<InvestorCompanyInvestedDTO> listInvestedInTheLeastCompanies();

	public List<InvestorNumberOfShareDTO> listHighestNumberOfShare();

	public List<InvestorNumberOfShareDTO> listLowestNumberOfShare();

	public List<Investor> listInvestorWithBudgetAvaible();

	public void deleteAll();

	public boolean hasBudgetAvailable(Integer id);

}