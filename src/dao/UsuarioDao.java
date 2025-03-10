package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDao {
	private static  ConexaoBD conexao = new ConexaoBD();
	private static  Connection conn = conexao.getConnection();

	public static String verificarlogin(String email, String senha) {
		String cargo="";
		int id=0;
		senha = criptografarSenha(senha);
		List<Usuario> listaUsuario = listarUsuarios();
		for (Usuario u : listaUsuario) {
			if (u.getEmail().equals(email.trim()) && u.getSenha().equals(senha.trim())) {
				id = u.getId();
				cargo= u.getCargo();
				break;
			}
		}
		if(id!=0) {
			return cargo;
		}else {
		return "não encontrado";
		}
	}
	public static Boolean cpfExiste(String cpf) {
		List<Usuario> listaUsuario = listarUsuarios();
		for (Usuario u : listaUsuario) {
			if(Usuario.limpar(u.getCpf()).equals(cpf)) {
				return true;
			}
		}
		return false;
	}
	public static Boolean emailExiste(String email) {
		List<Usuario> listaUsuario = listarUsuarios();
		for (Usuario u : listaUsuario) {
			if(u.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	public static String criptografarSenha(String senha) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(senha.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro ao criptografar a senha", e);
		}
	}

	public static void alterarSenha(Usuario u,String senha) {
		try {
		String sql = "UPDATE Usuario SET senha = ? WHERE id = ?";
		PreparedStatement sentenca = conn.prepareStatement(sql);
		sentenca = conn.prepareStatement(sql);
		sentenca.setString(1,senha);
		sentenca.setInt(2, u.getId()); 
		sentenca.execute();
		sentenca.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void alterarAtivo(Usuario u,boolean ativo) {
		try {
		String sql = "UPDATE Usuario SET ativo = ? WHERE id = ?";
		PreparedStatement sentenca = conn.prepareStatement(sql);
		sentenca = conn.prepareStatement(sql);
		sentenca.setBoolean(1,ativo);
		sentenca.setInt(2, u.getId()); 
		sentenca.execute();
		sentenca.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void incluirUsuario(Usuario usuario) {
		try {
	String sql = "insert into Usuario (nome,cpf,email,senha,ativo,cargo) values (?,?,?,?,?,?);";
	PreparedStatement sentenca = conn.prepareStatement(sql);
	sentenca = conn.prepareStatement(sql);
	sentenca.setString(1, usuario.getNome());
	sentenca.setString(2, usuario.getCpf());
	sentenca.setString(3, usuario.getEmail());
	sentenca.setString(4, usuario.getSenha());
	sentenca.setBoolean(5, usuario.isStatus());
	sentenca.setString(6, usuario.getCargo());
	sentenca.execute();
	sentenca.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<Usuario> listarUsuarios() {
	  List<Usuario> usuarios = new ArrayList<>();
	  String sql = "SELECT * FROM Usuario ORDER BY id;";
	  try {
	  PreparedStatement sentenca = conn.prepareStatement(sql);
	  ResultSet resultado = sentenca.executeQuery();
	  while(resultado.next()) {
	  Usuario usuario = new Usuario();
	  usuario.setId(resultado.getInt("id"));
	  usuario.setNome(resultado.getString("nome"));
	  usuario.setEmail(resultado.getString("email"));
	  usuario.setSenha(resultado.getString("senha"));
	  usuario.setCpf(resultado.getString("cpf"));
	  usuario.setStatus(resultado.getBoolean("ativo"));
	  usuario.setCargo(resultado.getString("cargo"));
	  usuarios.add(usuario);
	  }
	  resultado.close();
	  sentenca.close();
	  }catch(SQLException e) {
		  e.printStackTrace();
	  }
	  return usuarios;
	}
	public static Usuario buscarUsuario(int id) {
		Usuario u = new Usuario();
		for(Usuario usu : listarUsuarios()) {
			if(usu.getId()==id){	
				return usu;
			}
			}
		return u;
	}
	

	public static void alterarUsuario(Usuario usuario) {
		try {
		String sql = "UPDATE usuario SET nome=?,cpf=?, email=?, senha=?, ativo=?,cargo=? WHERE id=?";
		PreparedStatement sentenca = conn.prepareStatement(sql);
		sentenca = conn.prepareStatement(sql);
		sentenca.setString(1, usuario.getNome());
		sentenca.setString(2, usuario.getCpf());
		sentenca.setString(3, usuario.getEmail());
		sentenca.setString(4, usuario.getSenha());
		sentenca.setBoolean(5, usuario.isStatus());
		sentenca.setString(6, usuario.getCargo());
		sentenca.setInt(7, usuario.getId());
		sentenca.execute();
		sentenca.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}