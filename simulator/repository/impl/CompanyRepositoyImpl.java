package com.simulator.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.simulator.database.SQLiteConnection;
import com.simulator.dto.CapitalCompanyDTO;
import com.simulator.model.Company;
import com.simulator.repository.CompanyRepository;

public class CompanyRepositoyImpl implements CompanyRepository {

	private static CompanyRepository INSTANCE = null;

	private SQLiteConnection dataSource;

	/**
	 * Return singleton instance
	 * 
	 * @return INSTANCE
	 */
	public static CompanyRepository getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CompanyRepositoyImpl();
		}

		return INSTANCE;
	}

	private CompanyRepositoyImpl() {
		dataSource = SQLiteConnection.getInstance();
	}

	// private List<Company> companies = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.simulator.repository.CompanyRepository#save(com.simulator.model.Company)
	 */
	@Override
	public void save(Company company) {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection
					.prepareStatement("insert into company(name, numberOfShares, sharePrice) values(?,?,?)");
			pstm.setString(1, company.getName());
			pstm.setInt(2, company.getNumberOfShares());
			pstm.setInt(3, company.getSharePrice());
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private Connection getConnection() {
		return dataSource.getConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.simulator.repository.CompanyRepository#list()
	 */
	@Override
	public List<Company> list() {
		List<Company> companies = new ArrayList<Company>();
		try {
			Connection connection = getConnection();

			String sql = "select * from company";

			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setNumberOfShares(rs.getInt("numberOfShares"));
				company.setSharePrice(rs.getInt("sharePrice"));
				companies.add(company);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return companies;

	}

	@Override
	public List<Company> listCompaniesWithAvailableShares() {
		List<Company> companies = new ArrayList<Company>();
		try {
			Connection connection = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT count(s.id) as qtd_sale, p.id, p.name, p.numberOfShares, sharePrice from company p ");
			sql.append(" left join sale s on s.company_id = p.id ");
			sql.append(" GROUP by  p.id, p.name, p.numberOfShares, sharePrice ");
			sql.append(" having qtd_sale < numberOfShares ");
			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setNumberOfShares(rs.getInt("numberOfShares"));
				company.setSharePrice(rs.getInt("sharePrice"));
				companies.add(company);
			}

			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return companies;
	}

	@Override
	public List<CapitalCompanyDTO> listCompaniesWithHighestCapital() {
		List<CapitalCompanyDTO> companies = new ArrayList<>();
		try {
			Connection connection = getConnection();
			String sql = "select  max(numberOfShares*sharePrice) capital, id, name from company group by id, name having capital = (select  max(numberOfShares*sharePrice) from company);";
			PreparedStatement pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				CapitalCompanyDTO company = new CapitalCompanyDTO();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setCapital(rs.getInt("capital"));
				companies.add(company);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return companies;
	}

	@Override
	public List<CapitalCompanyDTO> listCompaniesWithLeastCapital() {
		List<CapitalCompanyDTO> companies = new ArrayList<>();
		try {
			Connection connection = getConnection();
			String sql = "select  min(numberOfShares*sharePrice) capital, id, name from company group by id, name having capital = (select  min(numberOfShares*sharePrice) from company);";
			PreparedStatement pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				CapitalCompanyDTO company = new CapitalCompanyDTO();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setCapital(rs.getInt("capital"));
				companies.add(company);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return companies;
	}

	@Override
	public boolean hasShareAvailable(Integer id) {
		try {
			Connection connection = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" select 1 from company c ");
			sql.append(" left join sale s ");
			sql.append(" on s.company_id = c.id ");
			sql.append(" where c.id = ? ");
			sql.append(" group by s.company_id having (c.numberOfShares * c.sharePrice) > (count(*)* c.sharePrice) ");
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
	public void updateSharePriceByDouble(Integer company_id) {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection
					.prepareStatement("update company set sharePrice = (sharePrice * 2) where id = ?");
			pstm.setInt(1, company_id);
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void reduceSharePriceCompanyWithoutSale() {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"update company set sharePrice = (sharePrice * 0.98) where id not in(select company_id from sale s group by company_id)");
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deleteAll() {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection.prepareStatement("delete from company");
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
