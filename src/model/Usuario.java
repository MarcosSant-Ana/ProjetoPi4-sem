package model;

public class Usuario {
	private int id;
	private String nome;
	private String senha;
	private String email;
	private String cpf;
	private boolean status;
	private String cargo;

	public Usuario() {
		status=true;
	}

	public Usuario(int id, String nome,String email, String cpf, boolean status) {
		this.id = id;
		this.nome = nome;
		this.email=email;
		this.cpf = cpf;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	@Override
	public String toString() {
		 return id + " | \t" + nome + "\t | \t"  +email+ " \t | \t" + status + "\t | \t"+cargo +"\n";
	}
	

	public static boolean validarCpf(String cpf) {
		int[] digitos = new int[11], multiplicador = { 10, 9, 8, 7, 6, 5, 4, 3, 2 },
				multiplicador2 = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
		int multiplicado, multiplicado2, mod, mod2, digitoVerificador, digitoVerificador2, soma = 0, soma2 = 0;
		cpf = limpar(cpf);
		for (int i = 0; i < cpf.length(); i++) {
			digitos[i] = Character.getNumericValue(cpf.charAt(i));
		}
		for (int i = 0; i < multiplicador.length; i++) {
			multiplicado = digitos[i] * multiplicador[i];
			soma += multiplicado;
		}
		for (int i = 0; i < multiplicador2.length; i++) {
			multiplicado2 = digitos[i] * multiplicador2[i];
			soma2 += multiplicado2;
		}
		mod = soma % 11;
		if (mod < 2) {
			digitoVerificador = 0;
		} else {
			digitoVerificador = 11 - mod;
		}

		mod2 = soma2 % 11;
		if (mod2 < 2) {
			digitoVerificador2 = 0;
		} else {
			digitoVerificador2 = 11 - mod2;
		}
		if (digitoVerificador == digitos[9] && digitoVerificador2 == digitos[10]) {
			return true;
		} else {
			return false;
		}

	}
	public static String limpar(String documento) {
		if(documento==null) {
			return "";		
		}
		String documentoLimpo = "";
		for (int i = 0; i < documento.length(); i++) {
			if (documento.charAt(i) == '.' || documento.charAt(i) == '/' || documento.charAt(i) == '-') {
				continue;
			} else {
				documentoLimpo += documento.charAt(i);
			}
		}
		return documentoLimpo;
	}

}
