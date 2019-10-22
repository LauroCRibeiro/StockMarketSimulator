package com.simulator.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.simulator.database.SQLiteConnection;
import com.simulator.model.Sale;
import com.simulator.repository.SaleRepository;

public class SaleRepositoyImpl implements SaleRepository {

	private static SaleRepository INSTANCE = null;

	private SQLiteConnection dataSource;

	/**
	 * Return singleton instance
	 * 
	 * @return INSTANCE
	 */
	public static SaleRepository getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SaleRepositoyImpl();
		}

		return INSTANCE;
	}

	private SaleRepositoyImpl() {
		dataSource = SQLiteConnection.getInstance();
	}

	private List<Sale> sales = new ArrayList<>();

	public void save(Sale sale) {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection
					.prepareStatement("insert into sale(investor_id, company_id) values(?,?)");
			pstm.setInt(1, sale.getInvestor().getId());
			pstm.setInt(2, sale.getCompany().getId());
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Sale> list() {
		return sales;
	}

	@Override
	public void deleteAll() {
		try {
			Connection connection = getConnection();
			PreparedStatement pstm = connection.prepareStatement("delete from sale");
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Connection getConnection() {
		return dataSource.getConnection();
	}

	@Override
	public Integer countSaleShare(Integer company_id) {
		try {
			Connection connection = getConnection();
			String sql = "select count(*) as total from sale s where s.company_id =? group by s.company_id";
			PreparedStatement pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, company_id);
			ResultSet rs = pstm.executeQuery();
			Integer total = 0;
			if (rs.next()) {
				total = rs.getInt("total");
			}
			rs.close();
			pstm.close();
			return total;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
