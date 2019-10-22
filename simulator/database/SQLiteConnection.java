package com.simulator.database;

import java.sql.Connection;

import org.sqlite.SQLiteDataSource;
import org.sqlite.SQLiteJDBCLoader;

public class SQLiteConnection {
	private static SQLiteConnection INSTANCE = null;
	private final static String URL = "jdbc:sqlite:src/simulator.db";
	private static SQLiteDataSource dataSource = null; 

	public static SQLiteConnection getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SQLiteConnection();
		}
		return INSTANCE;
	}

	private SQLiteConnection() {
		try {
			SQLiteJDBCLoader.initialize();
			dataSource = new SQLiteDataSource();
			dataSource.setUrl(URL);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() {
		try {
			dataSource.getConnection().close();
			return dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		getInstance().getConnection();
		System.out.println("Conectado com sucesso");
	}
}
