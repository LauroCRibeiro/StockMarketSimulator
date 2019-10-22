package com.simulator.app;

import java.util.List;

import com.simulator.dto.CapitalCompanyDTO;
import com.simulator.dto.InvestorCompanyInvestedDTO;
import com.simulator.dto.InvestorNumberOfShareDTO;
import com.simulator.model.Company;
import com.simulator.model.Investor;
import com.simulator.model.Sale;
import com.simulator.service.CompanyService;
import com.simulator.service.InvestorService;
import com.simulator.service.SaleService;
import com.simulator.service.impl.CompanyServiceImpl;
import com.simulator.service.impl.InvestorServiceImpl;
import com.simulator.service.impl.SaleServiceImpl;
import com.simulator.util.Utils;

public class Main {

	private static CompanyService companyService = CompanyServiceImpl.getInstance();

	private static InvestorService investorService = InvestorServiceImpl.getInstance();

	private static SaleService saleService = SaleServiceImpl.getInstance();

	public static void main(String[] args) {

		printHeaderLine();
		System.out.println("Initiazing the simulator...");
		initialize();

		printHeaderLine();
		System.out.println("Creating companies...");
		createCompanies();

		printHeaderLine();
		System.out.println("Creating investors...");
		createInvestors();

		printHeaderLine();
		System.out.println("Runing trading day...");
		runTradingDay();

		printHeaderLine();
		System.out.println("The simulation has been finished with success");
		printResult();

	}

	private static void printResult() {
		printCompaniesWithHighestCapital();
		printCompaniesWithLeastCapital();
		printHighestNumberOfShare();
		printLowestNumberOfShare();
		printInvestedInTheMostCompanies();
		printInvestedInTheLeastCompanies();
	}

	private static void printInvestedInTheLeastCompanies() {
		printHeaderLine();
		System.out.println("Investor that has invested in the least number of companies");

		List<InvestorCompanyInvestedDTO> listInvestedInTheLeastCompanies = investorService
				.listInvestedInTheLeastCompanies();

		listInvestedInTheLeastCompanies.forEach(System.out::println);
	}

	private static void printInvestedInTheMostCompanies() {
		printHeaderLine();
		System.out.println("Investor that has invested in the most companies");

		List<InvestorCompanyInvestedDTO> listInvestedInTheMostCompanies = investorService
				.listInvestedInTheMostCompanies();

		listInvestedInTheMostCompanies.forEach(System.out::println);
	}

	private static void printLowestNumberOfShare() {
		printHeaderLine();
		System.out.println("Investor with the lowest number of shares");

		List<InvestorNumberOfShareDTO> listLowestNumberOfShare = investorService.listLowestNumberOfShare();

		listLowestNumberOfShare.forEach(System.out::println);
	}

	private static void printHighestNumberOfShare() {
		printHeaderLine();
		System.out.println("Investor with the highest number of shares");

		List<InvestorNumberOfShareDTO> listHighestNumberOfShare = investorService.listHighestNumberOfShare();

		listHighestNumberOfShare.forEach(System.out::println);
	}

	private static void printCompaniesWithLeastCapital() {
		printHeaderLine();
		System.out.println("Company with the lowest capital (number of shares times share price)");

		List<CapitalCompanyDTO> listCompaniesWithLeastCapital = companyService.listCompaniesWithLeastCapital();

		listCompaniesWithLeastCapital.forEach(System.out::println);
	}

	private static void printCompaniesWithHighestCapital() {
		printHeaderLine();
		System.out.println("Company with the highest capital (number of shares times share price)");

		List<CapitalCompanyDTO> listCompaniesWithHighestCapital = companyService.listCompaniesWithHighestCapital();

		listCompaniesWithHighestCapital.forEach(System.out::println);
	}

	private static void printHeaderLine() {
		System.out.println(
				"===================================================================================================");
	}

	private static void initialize() {
		companyService.deleteAll();
		investorService.deleteAll();
		saleService.deleteAll();
	}

	public static void runTradingDay() {

		List<Investor> investors = investorService.list();
		List<Company> companies = companyService.list();

		while (true) {

			if (investors.isEmpty()) {
				break;
			}

			if (companies.isEmpty()) {
				break;
			}

			Integer randomIndexInvestor = Utils.getRandom(0, (investors.size() - 1));
			Investor investor = investors.get(randomIndexInvestor);

			Integer random = Utils.getRandom(1, 1000);

			for (int i = 0; i <= random; i++) {
				boolean hasBudgetAvailable = investorService.hasBudgetAvailable(investor.getId());

				if (!hasBudgetAvailable) {
					investors.remove(investor);
					break;
				}

				Integer randomIndexCompany = Utils.getRandom(0, (companies.size() - 1));
				Company company = companies.get(randomIndexCompany);

				boolean hasShareAvailable = companyService.hasShareAvailable(company.getId());
				if (!hasShareAvailable) {
					companies.remove(company);
					break;
				}

				Sale sale = new Sale(investor, company);
				saleService.save(sale);
			}

		}

	}

	private static void createCompanies() {
		for (int i = 1; i <= 100; i++) {
			createCompany(i);

		}
	}

	private static void createCompany(Integer id) {
		Integer numberOfShares = Utils.getRandom(500, 1000);
		Integer sharePrice = Utils.getRandom(10, 100);
		Company company = new Company();
		company.setId(id);
		company.setName("Company_" + id);
		company.setNumberOfShares(numberOfShares);
		company.setSharePrice(sharePrice);
		companyService.save(company);
	}

	private static void createInvestors() {
		for (int i = 1; i <= 100; i++) {
			createInvetor(i);

		}
	}

	private static void createInvetor(Integer id) {
		Integer budget = Utils.getRandom(1000, 10000);
		String name = "Investor_" + id;
		Investor investor = new Investor(id, name, budget);

		investorService.save(investor);
	}

}
