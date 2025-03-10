package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testesql {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/pi4sem?useSSL=false&serverTimezone=UTC";
		String user = "root";
		String password = "1christyan";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("Conectado com sucesso!");
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Driver não encontrado!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco!");
			e.printStackTrace();
		}
	}
}
