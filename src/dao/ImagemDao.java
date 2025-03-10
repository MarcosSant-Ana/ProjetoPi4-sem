package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Imagem;
import model.Usuario;

public class ImagemDao {
	private static ConexaoBD conexao = new ConexaoBD();
	private static Connection conn = conexao.getConnection();

	public static List<Imagem> listarImagem(int id) {
		List<Imagem> imagens = new ArrayList<>();
		String sql = "SELECT * FROM Imagem where produto_id= ? ORDER BY id;";
		try {
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca.setInt(1, id);
			ResultSet resultado = sentenca.executeQuery();
			while (resultado.next()) {
				Imagem imagem = new Imagem();
				imagem.setId(resultado.getInt("id"));
				imagem.setNome(resultado.getString("nome"));
				imagem.setDiretorio(resultado.getString("diretorio"));
				imagem.setIdProduto(resultado.getInt("produto_id"));
				imagem.setPrincipal(resultado.getBoolean("principal"));
				imagens.add(imagem);
			}
			resultado.close();
			sentenca.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return imagens;
	}

	public static void incluirImagem(Imagem imagem) {
		if(imagem.isPrincipal()) {
			nenhumaPrincipal(imagem.getIdProduto());
		}
		try {
			String sql = "insert into imagem (nome,diretorio,produto_id,principal) values (?,?,?,?);";
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca = conn.prepareStatement(sql);
			sentenca.setString(1, imagem.getNome());
			sentenca.setString(2, imagem.getDiretorio());
			sentenca.setInt(3, imagem.getIdProduto());
			sentenca.setBoolean(4, imagem.isPrincipal());
			sentenca.execute();
			sentenca.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static void nenhumaPrincipal(int idProduto) {
		try {
		String sql = "UPDATE imagem SET principal = FALSE where produto_id =?;";
		PreparedStatement sentenca = conn.prepareStatement(sql);
		sentenca = conn.prepareStatement(sql);
		sentenca.setInt(1, idProduto);
		sentenca.execute();
		sentenca.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void removerImagem(int idImagem) {
		try {
			String sql = "DELETE FROM imagem WHERE id = ?";
			PreparedStatement sentenca = conn.prepareStatement(sql);
			sentenca.setInt(1, idImagem);
            sentenca.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}