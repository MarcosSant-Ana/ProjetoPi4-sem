package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private String servidor;
    private String banco;
    private String usuario;
    private String senha;
    private Connection conexao;

    public ConexaoBD() {
        this.servidor = "127.0.0.1";
        this.banco = "empresa";
        this.usuario = "root";
        this.senha = "1christyan";
        conectar();
    }

    public void conectar() {
        try {
            // Carregar o driver JDBC antes de conectar
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://" + this.servidor + ":3306/" + this.banco;
            this.conexao = DriverManager.getConnection(url, this.usuario, this.senha);
    
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado!", e);
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + ex.getMessage(), ex);
        }
    }

    public Connection getConnection() {
        if (conexao == null) {
            throw new IllegalStateException("Conexão não estabelecida. Chame o método conectar() primeiro.");
        }
        return conexao;
    }

    public void fechar() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada com sucesso.");
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão: " + ex.getMessage(), ex);
            }
        }
    }
}
