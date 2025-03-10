package view;

import java.util.Scanner;

import dao.UsuarioDao;

public class Login {
	
	public static String login() {
		Scanner ler = new Scanner(System.in);
		boolean erro= false;
		do {
		System.out.println("--------------------------------------------------------------------------------------------------------\n"
				+ "Login\n"
				+ "Email:");
		String email = ler.nextLine();
		System.out.println("Senha:");
		String senha = ler.nextLine();
		String qTipo= UsuarioDao.verificarlogin(email,senha);
		if(!qTipo.equals("não encontrado")) {
			return qTipo;
		}else {
			erro=true;
		}
		
		
		if(erro==true) {
			System.out.println("\nEmail ou senha incorreto\n");
			
		}
		}while(erro==true);
		System.out.println("--------------------------------------------------------------------------------------------------------\n");
		return "não encontrado";
		
	}
}
