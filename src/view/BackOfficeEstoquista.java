package view;

import java.util.Scanner;

import dao.ProdutoDao;
import model.Produto;

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
				viewListarProdutoEstoquista();
				break;
			case 0:
				System.out.println("Saindo...");
				return;
			default:
				System.out.println("Opção inválida!");
			}
		}
	}

	public static void viewListarProdutoEstoquista() {
		boolean erro = true, sair = false;
		do {
			System.out.println("Listar Produto\nId  | \t Nome \t | \t Quantidade \t | \t Preço \t | \t Status");

			for (Produto p : ProdutoDao.listarProdutos()) {

				System.out.println(p.toString());
			}

			System.out.println("---------------------------------------");
			ler.nextLine();

			do {
				System.out.println("Entre com o id para editar, 0 para voltar =>");
				String r = ler.nextLine().trim();

				if (r.equals("0")) {
					erro = false;
					sair = true;
				} else if (r.matches("\\d+")) {
					int idPedido = Integer.parseInt(r);
					boolean encontrou = false;

					for (Produto p : ProdutoDao.listarProdutos()) {
						if (p.getId() == idPedido) {
							viewAlterarProdutoEstoquista(p);
							erro = false;
							encontrou = true;
							break;
						}
					}

					if (!encontrou) {
						System.out.println("Id não encontrado!");
						erro = true;
					}
				} else {
					System.out.println("Entrada inválida! Digite um Id válido ou 0 para voltar.");
				}
			} while (erro);
		} while (!sair);
	}

	private static void viewAlterarProdutoEstoquista(Produto produto) {
		boolean valido = true;
		System.out.println("\nAlterar Produto Estoquista\n");
		System.out.println("Id: " + produto.getId());
		System.out.println("Nome: " + produto.getNome());
		System.out.println("Preço: " + produto.getPreco());
		System.out.println("Qtd. Estoque: " + produto.getQtd());
		System.out.println("Avaliação: " + produto.getAvaliacao());
		System.out.println("Status => " + (produto.isAtivo() ? "ativo" : "inativo"));
		System.out.println("---------------------------------------");
		System.out.println("Qtd. Estoque =>");
		int qtd = ler.nextInt();
		ler.nextLine();
		produto.setQtd(qtd);
		do {
			System.out.println("Salvar (y/n) =>");
			String salvar = (ler.nextLine().trim().toLowerCase());

			if (salvar.equals("y")) {
				try {
					ProdutoDao.alterarProdutoEstoquista(produto);
					System.out.println("Produto alterado com sucesso!");
					break;
				} catch (Exception e) {
					System.out.println("Erro ao alterar Produto!");
				}
			} else if (salvar.equals("n")) {
				return;
			} else {
				System.out.println("Comando não reconhecido");
				valido = false;
			}
		} while (!valido);
	}

}
