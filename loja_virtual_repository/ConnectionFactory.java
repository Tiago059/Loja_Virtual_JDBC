package loja_virtual_repository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private String url = "jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "sonichyper";
	
	public DataSource dataSource;
	
	public ConnectionFactory() {
		ComboPooledDataSource comboPoolDataSource = new ComboPooledDataSource();
		comboPoolDataSource.setJdbcUrl(url);
		comboPoolDataSource.setUser(user);
		comboPoolDataSource.setPassword(password);
		
		comboPoolDataSource.setMaxPoolSize(15);
		
		this.dataSource = comboPoolDataSource;
	}
	
	
	
	public Connection recuperarConexao() throws SQLException {
		return dataSource.getConnection();
	}
	
}