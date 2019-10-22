package com.simulator.service.impl;

import java.util.Collection;
import java.util.List;

import com.simulator.dto.InvestorCompanyInvestedDTO;
import com.simulator.dto.InvestorNumberOfShareDTO;
import com.simulator.model.Investor;
import com.simulator.repository.InvestorRepository;
import com.simulator.repository.impl.InvestorRepositoyImpl;
import com.simulator.service.InvestorService;

public class InvestorServiceImpl implements InvestorService {

	private static InvestorService INSTANCE = null;

	/**
	 * Return singleton instance
	 * 
	 * @return INSTANCE
	 */
	public static InvestorService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new InvestorServiceImpl();
		}

		return INSTANCE;
	}

	private InvestorServiceImpl() {
		investorRepositoy = InvestorRepositoyImpl.getInstance();
	}

	private InvestorRepository investorRepositoy;

	public void save(Investor investor) {
		investorRepositoy.save(investor);
		System.out.println(investor);
	}

	public List<Investor> list() {
		return investorRepositoy.list();
	}

	@Override
	public List<InvestorCompanyInvestedDTO> listInvestedInTheMostCompanies() {
		return investorRepositoy.listInvestedInTheMostCompanies();
	}

	@Override
	public List<InvestorCompanyInvestedDTO> listInvestedInTheLeastCompanies() {
		return investorRepositoy.listInvestedInTheLeastCompanies();
	}

	@Override
	public List<InvestorNumberOfShareDTO> listHighestNumberOfShare() {
		return investorRepositoy.listHighestNumberOfShare();
	}

	@Override
	public List<InvestorNumberOfShareDTO> listLowestNumberOfShare() {
		return investorRepositoy.listLowestNumberOfShare();
	}

	@Override
	public List<Investor> listInvestorWithBudgetAvaible() {
		return investorRepositoy.listInvestorWithBudgetAvaible();
	}

	@Override
	public void deleteAll() {
		investorRepositoy.deleteAll();
	}

	@Override
	public boolean hasBudgetAvailable(Integer id) {
		return investorRepositoy.hasBudgetAvailable(id);
	}
}
