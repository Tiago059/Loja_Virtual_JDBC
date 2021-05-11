package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Categoria;
import modelo.Produto;

public class ProdutoDAO {

	private Connection con;

	public ProdutoDAO(Connection con) {
		this.con = con;
	}

	public void persistirProduto(Produto produto) throws SQLException {

		String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES (?, ?)";

		PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stm.setString(1, produto.getNome());
		stm.setString(2, produto.getDescricao());

		stm.executeUpdate();

		ResultSet rst = stm.getGeneratedKeys();
		while (rst.next()) {
			produto.setId(rst.getInt(1));
			System.out.println("Produto inserido:\n" + produto.toString());
		}

	}

	public List<Produto> recuperarProdutos() throws SQLException {
		List<Produto> produtos = new ArrayList<>();

		String sql = "SELECT PRODUTO.id, PRODUTO.nome, descricao, CATEGORIA_ID, CATEGORIA.NOME "
				+ "FROM PRODUTO, CATEGORIA " + "WHERE PRODUTO.CATEGORIA_ID = CATEGORIA.ID;";

		PreparedStatement stm = con.prepareStatement(sql);

		stm.executeQuery();

		ResultSet rst = stm.getResultSet();
		while (rst.next()) {
			Categoria c = new Categoria(rst.getInt("CATEGORIA_ID"), rst.getString("CATEGORIA.NOME"));
			produtos.add(new Produto(rst.getInt("ID"), rst.getString("NOME"), rst.getString("DESCRICAO"), c));
		}

		return produtos;
	}

	public List<Produto> filtrarProdutosPorCategoria(Categoria categoria) throws SQLException {
		List<Produto> produtos = new ArrayList<>();

		String sql = "SELECT PRODUTO.id, PRODUTO.nome, descricao, CATEGORIA_ID, CATEGORIA.NOME "
				+ "FROM PRODUTO, CATEGORIA " + "WHERE PRODUTO.CATEGORIA_ID = CATEGORIA.ID "
				+ "AND PRODUTO.CATEGORIA_ID = ?";

		PreparedStatement stm = con.prepareStatement(sql);
		stm.setInt(1, categoria.getId());

		stm.executeQuery();

		ResultSet rst = stm.getResultSet();
		while (rst.next()) {
			Categoria c = new Categoria(rst.getInt("CATEGORIA_ID"), rst.getString("CATEGORIA.NOME"));
			produtos.add(new Produto(rst.getInt("ID"), rst.getString("NOME"), rst.getString("DESCRICAO"), c));
		}

		return produtos;
	}

	public void mostrarProdutosPelasCategorias() throws SQLException {
		String sql = "SELECT PRODUTO.id, PRODUTO.nome, descricao, CATEGORIA_ID, CATEGORIA.NOME "
				   + "FROM PRODUTO "
				   + "INNER JOIN CATEGORIA "
				   + "ON PRODUTO.CATEGORIA_ID = CATEGORIA.ID;";

		PreparedStatement stm = con.prepareStatement(sql);

		stm.executeQuery();

		ResultSet rst = stm.getResultSet();
		while (rst.next()) {
			System.out.println("id: " + rst.getInt("ID") + "\tnome: " + rst.getString("NOME") + "\tdescricao: "
					+ rst.getString("DESCRICAO") + "\tcategoria_id: " + rst.getInt("CATEGORIA_ID"));
			
		}

	}

	public void delete(int id) throws SQLException {
		// Delete

		String sql = "DELETE FROM PRODUTO WHERE ID > ?";

		PreparedStatement stm = con.prepareStatement(sql);

		stm.setInt(1, id);

		stm.execute();

		System.out.println("Linhas modificadas: " + stm.getUpdateCount());

	}

	public void simpleSelectAll() throws SQLException {

		String sql = "SELECT * FROM PRODUTO";

		PreparedStatement stm = con.prepareStatement(sql);

		stm.executeQuery();

		ResultSet rst = stm.getResultSet();
		while (rst.next()) {
			System.out.println("id: " + rst.getInt("ID") + "\tnome: " + rst.getString("NOME") + "\tdescricao: "
					+ rst.getString("DESCRICAO") + "\tcategoria_id: " + rst.getInt("CATEGORIA_ID"));
		}

	}

}