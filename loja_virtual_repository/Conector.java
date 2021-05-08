package loja_virtual_repository;

import java.sql.Connection;
import java.sql.SQLException;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import modelo.Categoria;

@SuppressWarnings("unused")
public class Conector {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory conp = new ConnectionFactory();

		Connection con = conp.recuperarConexao();
		con.setAutoCommit(false);

		ProdutoDAO produtoDao = new ProdutoDAO(con);
		CategoriaDAO categoriaDao = new CategoriaDAO(con);

		try {

			// Select all
			//produtoDao.recuperarProdutos().forEach(System.out::println);
			
			produtoDao.mostrarProdutosPelasCategorias();
			
			//produtoDao.recuperarProdutosPorCategoria(new Categoria(5, "fodase")).forEach(System.out::println);
			
			// produtoDao.simpleSelectAll();
			
			//categoriaDao.recuperarCategorias().forEach(System.out::println);

			// produtoDao.delete(2);

			//produtoDao.persistirProduto(new Produto("geladeira", "geladeira cu"));
			//produtoDao.persistirProduto(new Produto("cômoda", "Cômoda merda"));

			//produtoDao.recuperarProdutos().forEach(System.out::println);

			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ROLLBACK");
			con.rollback();
		} finally {
			System.out.println("fechando conexão");
			con.close();
		}

	}

}
