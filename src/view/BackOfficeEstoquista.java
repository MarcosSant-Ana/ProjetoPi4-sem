package view;

import java.util.Scanner;

public class BackOfficeEstoquista {
	static Scanner ler = new Scanner(System.in);

	public static void viewEstoquista() {
		while (true) {
			System.out.println("\n-------- BackOffice Estoquista --------");
			System.out.println("1) Listar Produtos");
			System.out.println("0) Sair");
			System.out.print("Escolha uma opção: ");
			int resp = ler.nextInt();

			switch (resp) {
			case 1:
				viewListarProduto();
				break;
			case 0:
				System.out.println("Saindo...");
				return;
			default:
				System.out.println("Opção inválida!");
			}
		}
	}

	private static void viewListarProduto() {
		
		
	}  

}
