package com.simulator.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.simulator.database.SQLiteConnection;
import com.simulator.dto.InvestorCompanyInvestedDTO;
import com.simulator.dto.InvestorNumberOfShareDTO;
import com.simulator.model.Investor;
import com.simulator.repository.InvestorRepository;

public class InvestorRepositoyImpl implements InvestorRepository {

	private static InvestorRepository INSTANCE = null;

	private SQLiteConnection dataSource;

	/**
	 * Return singleton instance
	 * 
	 * @return INSTANCE
	 */
	public static InvestorRepository getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new InvestorRepositoyImpl();
		}

		return INSTANCE;
	}

	private InvestorRepositoyImpl() {
		dataSource = SQLiteConnection.getInstance();
	}

	public void save(Investor investor) {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection.prepareStatement("insert into investor(name, budget) values(?,?)");
			pstm.setString(1, investor.getName());
			pstm.setInt(2, investor.getBudget());
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Investor> listInvestorWithBudgetAvaible() {
		List<Investor> companies = new ArrayList<Investor>();
		try {
			Connection connection = getConnection();
			String sql = " SELECT i.id, i.name, i.budget from investor i where budget > ifnull((select (c.sharePrice * count(*)) from sale s inner join company c on c.id = s.company_id where s.investor_id = i.id group by s.investor_id), 0)";
			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Investor investor = new Investor();
				investor.setId(rs.getInt("id"));
				investor.setName(rs.getString("name"));
				investor.setBudget(rs.getInt("budget"));
				companies.add(investor);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return companies;
	}

	public List<InvestorNumberOfShareDTO> listHighestNumberOfShare() {
		List<InvestorNumberOfShareDTO> companies = new ArrayList<>();
		try {
			Connection connection = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(
					" select i.id, i.name, budget, (select count(*) from sale where investor_id = i.id) qtd_share from investor i ");
			sql.append(
					" where qtd_share = (select max(qtd) from(select count(*) qtd from sale group by investor_id)) ");

			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				InvestorNumberOfShareDTO investor = new InvestorNumberOfShareDTO();
				investor.setId(rs.getInt("id"));
				investor.setName(rs.getString("name"));
				investor.setBudget(rs.getInt("budget"));
				investor.setNumberOfShares(rs.getInt("qtd_share"));
				companies.add(investor);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return companies;
	}

	public List<InvestorCompanyInvestedDTO> listInvestedInTheMostCompanies() {
		List<InvestorCompanyInvestedDTO> investors = new ArrayList<>();
		try {
			Connection connection = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(
					" select i.id, i.name, budget, (select count(DISTINCT s1.company_id) from sale s1 where investor_id = i.id) qtd_share from investor i ");
			sql.append(
					" where qtd_share = (select max(qtd) from(select count(DISTINCT s2.company_id) qtd from sale s2 group by investor_id)); ");

			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				InvestorCompanyInvestedDTO investor = new InvestorCompanyInvestedDTO();
				investor.setId(rs.getInt("id"));
				investor.setName(rs.getString("name"));
				investor.setBudget(rs.getInt("budget"));
				investor.setNumberOfCompanies(rs.getInt("qtd_share"));
				investors.add(investor);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return investors;
	}

	public List<InvestorCompanyInvestedDTO> listInvestedInTheLeastCompanies() {
		List<InvestorCompanyInvestedDTO> investors = new ArrayList<>();
		try {
			Connection connection = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(
					" select i.id, i.name, budget, (select count(DISTINCT s1.company_id) from sale s1 where investor_id = i.id) qtd_share from investor i ");
			sql.append(
					" where qtd_share = (select min(qtd) from(select count(DISTINCT s2.company_id) qtd from sale s2 group by investor_id)); ");

			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				InvestorCompanyInvestedDTO investor = new InvestorCompanyInvestedDTO();
				investor.setId(rs.getInt("id"));
				investor.setName(rs.getString("name"));
				investor.setBudget(rs.getInt("budget"));
				investor.setNumberOfCompanies(rs.getInt("qtd_share"));
				investors.add(investor);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return investors;
	}

	public List<InvestorNumberOfShareDTO> listLowestNumberOfShare() {
		List<InvestorNumberOfShareDTO> investors = new ArrayList<>();
		try {
			Connection connection = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(
					" select i.id, i.name, budget, (select count(*) from sale where investor_id = i.id) qtd_share from investor i ");
			sql.append(
					" where qtd_share = (select min(qtd) from(select count(*) qtd from sale group by investor_id)) ");

			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				InvestorNumberOfShareDTO investor = new InvestorNumberOfShareDTO();
				investor.setId(rs.getInt("id"));
				investor.setName(rs.getString("name"));
				investor.setBudget(rs.getInt("budget"));
				investor.setNumberOfShares(rs.getInt("qtd_share"));
				investors.add(investor);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return investors;
	}

	public List<Investor> list() {
		List<Investor> companies = new ArrayList<Investor>();
		try {
			Connection connection = getConnection();
			String sql = "select * from investor";
			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Investor investor = new Investor();
				investor.setId(rs.getInt("id"));
				investor.setName(rs.getString("name"));
				investor.setBudget(rs.getInt("budget"));
				companies.add(investor);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return companies;
	}

	@Override
	public boolean hasBudgetAvailable(Integer id) {
		try {
			Connection connection = getConnection();
			String sql = "select 1 from investor i where budget > ifnull((select (count(*)*c.sharePrice) from sale s inner join company c on s.company_id = c.id where s.investor_id = i.id group by s.investor_id ),0) and id = ?";
			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			boolean next = rs.next();
			rs.close();
			pstm.close();
			return next;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteAll() {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection.prepareStatement("delete from investor");
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Connection getConnection() {
		return dataSource.getConnection();
	}
}
