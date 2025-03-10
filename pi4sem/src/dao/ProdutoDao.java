package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Produto;



public class ProdutoDao {
    private static  ConexaoBD conexao = new ConexaoBD();
    private static  Connection conn = conexao.getConnection();

    public static List<Produto> listarUsuarios() {
          List<Produto> produtos = new ArrayList<>();
          String sql = "SELECT * FROM Produtos ORDER BY id;";
          try {
          PreparedStatement sentenca = conn.prepareStatement(sql);
          ResultSet resultado = sentenca.executeQuery();
          while(resultado.next()) {
          Produto produto = new Produto();
          produto.setId(resultado.getInt("id"));
          produto.setNome(resultado.getString("nome"));
          produto.setQtd(resultado.getInt("qtd"));
          produto.setPreco(resultado.getDouble("preco"));
          produto.setDescricao(resultado.getString("descricao"));
          produto.setAtivo(resultado.getBoolean("ativo"));
          produto.setAvaliacao(resultado.getDouble("avaliacao"));
          produtos.add(produto);
          }
          resultado.close();
          sentenca.close();
          }catch(SQLException e) {
              e.printStackTrace();
          }
          return produtos;
        }
}