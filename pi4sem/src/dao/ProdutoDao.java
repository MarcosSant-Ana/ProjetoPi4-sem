package dao;

import java.sql.Connection;

public class ProdutoDao {
	private static  ConexaoBD conexao = new ConexaoBD();
	private static  Connection conn = conexao.getConnection();
}
