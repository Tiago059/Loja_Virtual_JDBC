package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Categoria;

public class CategoriaDAO {

	private Connection con;

	public CategoriaDAO(Connection con) {
		this.con = con;
	}

	public void persistirCategoria(Categoria categoria) throws SQLException {

		String sql = "INSERT INTO CATEGORIA (NOME) VALUES (?)";

		PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stm.setString(1, categoria.getNome());

		stm.executeUpdate();

		ResultSet rst = stm.getGeneratedKeys();
		while (rst.next()) {
			categoria.setId(rst.getInt(1));
			System.out.println("Categoria inserida:\n" + categoria.toString());
		}

	}

	public List<Categoria> recuperarCategorias() throws SQLException {
		List<Categoria> categoria = new ArrayList<>();

		String sql = "SELECT * FROM CATEGORIA";

		PreparedStatement stm = con.prepareStatement(sql);

		stm.executeQuery();

		ResultSet rst = stm.getResultSet();
		while (rst.next()) {
			categoria.add(new Categoria(rst.getInt("ID"), rst.getString("NOME")));
		}

		return categoria;
	}
	
	public void delete(int id) throws SQLException {
		// Delete

		String sql = "DELETE FROM CATEGORIA WHERE ID > ?";

		PreparedStatement stm = con.prepareStatement(sql);

		stm.setInt(1, id);

		stm.execute();

		System.out.println("Linhas modificadas: " + stm.getUpdateCount());

	}

}