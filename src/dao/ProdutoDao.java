package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Produto;

public class ProdutoDao {
	private static ConexaoBD conexao = new ConexaoBD();
	private static Connection conn = conexao.getConnection();

	public static List<Produto> listarProdutos() {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM Produto ORDER BY id desc;";
		try {
			PreparedStatement sentenca = conn.prepareStatement(sql);
			ResultSet resultado = sentenca.executeQuery();
			while (resultado.next()) {
				Produto produto = new Produto();
				produto.setId(resultado.getInt("id"));
				produto.setNome(resultado.getString("nome"));
				produto.setPreco(resultado.getDouble("preco"));
				produto.setQtd(resultado.getInt("qtd"));
				produto.setAvaliacao(resultado.getDouble("avaliacao"));
				produto.setDescricao(resultado.getString("descricao"));
				produto.setAtivo(resultado.getBoolean("ativo"));
				produtos.add(produto);
			}
			resultado.close();
			sentenca.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produtos;
	}

	public static void incluirProduto(Produto produto) {

		try {
			String sql = "insert into produto (nome,preco,qtd,avaliacao,ativo,descricao) values (?,?,?,?,?,?);";
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca = conn.prepareStatement(sql);
			sentenca.setString(1, produto.getNome());
			sentenca.setDouble(2, produto.getPreco());
			sentenca.setInt(3, produto.getQtd());
			sentenca.setDouble(4, produto.getAvaliacao());
			sentenca.setBoolean(5, produto.isAtivo());
			sentenca.setString(6, produto.getDescricao());
			sentenca.execute();
			sentenca.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void alterarAtivo(Produto p, boolean novoStatus) {
		try {
			String sql = "UPDATE Produto SET ativo = ? WHERE id = ?";
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca = conn.prepareStatement(sql);
			sentenca.setBoolean(1,novoStatus);
			sentenca.setInt(2, p.getId()); 
			sentenca.execute();
			sentenca.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		
	}
	public static void alterarProduto(Produto produto) {
        try {
            String sql = "update produto set nome = ?, preco = ?, qtd = ?, avaliacao = ?, ativo = ?, descricao = ? where id = ?;";
            PreparedStatement sentenca = conn.prepareStatement(sql);
            sentenca.setString(1, produto.getNome());
            sentenca.setDouble(2, produto.getPreco());
            sentenca.setInt(3, produto.getQtd());
            sentenca.setDouble(4, produto.getAvaliacao());
            sentenca.setBoolean(5, produto.isAtivo());
            sentenca.setString(6, produto.getDescricao());
            sentenca.setInt(7, produto.getId());
            sentenca.execute();
            sentenca.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarProdutoEstoquista(Produto produto) {
        try {
            String sql = "update produto set qtd = ? where id = ?;";
            PreparedStatement sentenca = conn.prepareStatement(sql);
            sentenca.setInt(1, produto.getQtd());
            sentenca.setInt(2, produto.getId());
            sentenca.execute();
            sentenca.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static int ultimoIdAdd() {
		int id=0;
		try {
		String sql = "SELECT LAST_INSERT_ID() as id;";
        PreparedStatement sentenca = conn.prepareStatement(sql);
        ResultSet resultado = sentenca.executeQuery();
        if (resultado.next()) {
        id = resultado.getInt("id");
        }
        resultado.close();
		sentenca.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
}
