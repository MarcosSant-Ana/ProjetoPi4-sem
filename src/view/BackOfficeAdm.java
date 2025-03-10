package view;

import java.util.Scanner;

import dao.ImagemDao;
import dao.ProdutoDao;
import dao.UsuarioDao;
import model.Imagem;
import model.Produto;
import model.Usuario;

public class BackOfficeAdm {
	static Scanner ler = new Scanner(System.in);

	public static void viewAdmin() {
		do{
			System.out.println("\n\n-------- BackOffice Admin --------\n" + "1) Listar produtos\n"
					+ "2) Listar Usuários\n" + "0) Sair\n" + "Entre com a opção (1,2 ou 0) :");
			String num = ler.nextLine();
			int resp = Integer.parseInt(num);

			switch (resp) {
			case 1:
				viewListarProduto();
				break;
			case 2:
				viewListarUsuarios();
				break;
			case 0:
				System.out.println("Saindo...");
				System.exit(0);
			default:
				System.out.println("Opção inválida");
			}

		}while (true) ;
	}

	public static void viewListarUsuarios() {
		boolean erro = true, sair = false;
		do {
			System.out.println("Listar Usuario\nID  | \t NOME \t | \t EMAIL \t | \t STATUS \t | \t GRUPO");

			for (Usuario u : UsuarioDao.listarUsuarios()) {
				System.out.println(u.toString() + "\n");
			}

			System.out.println("---------------------------------------\n");

			do {
				System.out.println("Entre com o id para editar/ativar/inativar, 0 para voltar e 'i' para incluir =>");
				String r = ler.nextLine().trim();

				if (r.equalsIgnoreCase("i")) {
					viewIncluirUsuario();
					erro = false;
				} else if (r.equals("0")) {
					erro = false;
					sair = true;
				} else if (r.matches("\\d+")) {
					int idPedido = Integer.parseInt(r);
					boolean encontrou = false;

					for (Usuario u : UsuarioDao.listarUsuarios()) {
						if (u.getId() == idPedido) {
							viewEditarUsuario(u);
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
					System.out.println("Entrada inválida! Digite um número válido ou 'i' para incluir.");
				}
			} while (erro);
		} while (!sair);
	}

	private static void viewIncluirUsuario() {
		String nome, cpf, email, cargo;
		boolean valido = true, existe = false;
		Usuario usuario = new Usuario();

		System.out.println("Informe os dados do funcionário:");
		System.out.print("Nome: ");
		nome = ler.nextLine();
		usuario.setNome(nome);
		do {
			System.out.print("CPF: ");
			cpf = ler.nextLine();
			valido = Usuario.validarCpf(cpf);
			if (!valido) {
				System.out.println("CPF invalido!");
				valido = false;
			}
			if (UsuarioDao.cpfExiste(cpf)) {
				valido = false;
				System.out.println("\nCPF já cadastrado!");
			}
		} while (!valido);
		usuario.setCpf(cpf);
		do {
			System.out.print("Email: ");
			email = ler.nextLine();
			if (UsuarioDao.emailExiste(email)) {
				System.out.println("\nEmail ja cadastrado");
				existe = true;
			} else {
				existe = false;
			}

		} while (existe);
		usuario.setEmail(email);
		String senha, repetirSenha;
		do {
			System.out.print("Senha: ");
			senha = ler.nextLine();

			System.out.print("Repetir Senha: ");
			repetirSenha = ler.nextLine();

			if (!senha.equals(repetirSenha)) {
				System.out.println("As senhas não coincidem! Por favor, insira novamente.");
			}
		} while (!senha.equals(repetirSenha));

		String senhaCriptografada = UsuarioDao.criptografarSenha(senha);
		usuario.setSenha(senhaCriptografada);
		do {
			System.out.print("Cargo (Admin/Estoquista): ");
			cargo = ler.nextLine();
			if (cargo.trim().equalsIgnoreCase("Estoquista")) {
				usuario.setCargo("Estoquista");
				valido = true;
			} else if (cargo.trim().equalsIgnoreCase("Admin")) {
				usuario.setCargo("Administrador");
				valido = true;
			} else {
				valido = false;
				System.out.println("Cargo não existe");
			}
		} while (!valido);
		do {
			System.out.print("Salvar (y/n) =>");
			String salvar = (ler.nextLine().trim().toLowerCase());

			if (salvar.equals("y")) {
				try {
					UsuarioDao.incluirUsuario(usuario);
					System.out.println("Funcionário incluído com sucesso!");
				} catch (Exception e) {
					System.out.println("Erro ao incluir usuário!");
				}
			} else if (salvar.equals("n")) {
				return;
			} else {
				System.out.println("Comando não reconhecido");
				valido = false;
			}
		} while (!valido);
	}

	public static void viewEditarUsuario(Usuario usuario) {
		boolean erro = true;
		do {
			System.out.println("\nOpção de edição de usuário\n");
			System.out.println("Id: " + usuario.getId());
			System.out.println("Nome: " + usuario.getNome());
			System.out.println("Cpf: " + usuario.getCpf());
			System.out.println("E-mail: " + usuario.getEmail());
			System.out.println("Status: " + (usuario.isStatus() ? "ativo" : "inativo"));
			System.out.println("Grupo: " + usuario.getCargo());
			System.out.println("---------------------------------------");
			System.out.println("Opções");
			System.out.println("1) Alterar usuário");
			System.out.println("2) Alterar senha");
			System.out.println("3) Ativar/Desativar");
			System.out.println("4) Voltar Listar Usuário");
			System.out.print("Entre com a opção (1,2,3,4) => ");

			int opcao = ler.nextInt();
			ler.nextLine();

			switch (opcao) {
			case 1:
				viewAlterarUsuario(usuario);
				break;
			case 2:
				viewAlterarSenha(usuario);
				break;
			case 3:
				viewAtivarDesativar(usuario);
				break;
			case 4:
				return;
			default:
				System.out.println("Opção inválida!");
			}

			erro = true;

		} while (erro);
	}

	private static void viewAtivarDesativar(Usuario usuario) {
		boolean erro = true;

		do {
			System.out.println("\nOpção de edição de usuário\n");
			System.out.println("Id: " + usuario.getId());
			System.out.println("Nome: " + usuario.getNome());
			System.out.println("Cpf: " + usuario.getCpf());
			System.out.println("E-mail: " + usuario.getEmail());
			System.out.println("Status: " + (usuario.isStatus() ? "ativo" : "inativo"));
			System.out.println("Grupo: " + usuario.getCargo());
			System.out.println("---------------------------------------\n");

			boolean novoStatus = !usuario.isStatus();
			String acao = novoStatus ? "Ativar" : "Desativar";

			System.out.println("<< " + acao + " usuário >>\n");

			System.out.print("Confirmar alteração? (Y/N) => ");
			String confirmar = ler.nextLine().trim().toLowerCase();

			if (confirmar.equalsIgnoreCase("y")) {
				UsuarioDao.alterarAtivo(usuario, novoStatus);
				usuario.setStatus(novoStatus); // Atualiza o objeto localmente
				System.out.println("Status alterado com sucesso!");
				erro = false;
			} else if (confirmar.equalsIgnoreCase("n")) {
				System.out.println("Operação cancelada.");
				erro = false;
			} else {
				System.out.println("Comando não reconhecido, tente novamente.");
			}
		} while (erro);

	}

	private static void viewAlterarSenha(Usuario usuario) {
		String novaSenha, confsenha, opcao;
		System.out.println("\nAlterar usuario\n");
		System.out.println("Id: " + usuario.getId());
		System.out.println("Nome: " + usuario.getNome());
		System.out.println("Cpf: " + usuario.getCpf());
		System.out.println("E-mail: " + usuario.getEmail());
		System.out.println("Status: " + (usuario.isStatus() ? "ativo" : "inativo"));
		System.out.println("Grupo: " + usuario.getCargo());
		System.out.println("---------------------------------------\n");
		do {
			System.out.println("Opções \nNova senha: ");
			novaSenha = ler.nextLine();
			System.out.println("\nConfirme a senha: ");
			confsenha = ler.nextLine();
			if (novaSenha.equals(confsenha)) {
				break;
			}
			System.out.println("Senha não são iguais !");
		} while (true);
		do {
			System.out.println("\nSalvar ? (y/n): ");
			opcao = ler.nextLine();
			if (opcao.equalsIgnoreCase("y")) {
				UsuarioDao.alterarSenha(usuario, UsuarioDao.criptografarSenha(novaSenha));
				break;
			} else if (opcao.equalsIgnoreCase("n")) {
				break;
			} else if (!opcao.equalsIgnoreCase("0")) {
				System.out.println("opção invalida");
			}
		} while (true);

	}

	public static void viewAlterarUsuario(Usuario usuario) {
		boolean valido = false;
		String novoNome, novoCpf = "", cargo, opcao = "";

		System.out.println("\nAlterar usuario\n");
		System.out.println("Id: " + usuario.getId());
		System.out.println("Nome: " + usuario.getNome());
		System.out.println("Cpf: " + usuario.getCpf());
		System.out.println("E-mail: " + usuario.getEmail());
		System.out.println("Status: " + (usuario.isStatus() ? "ativo" : "inativo"));
		System.out.println("Grupo: " + usuario.getCargo());
		System.out.println("---------------------------------------\n");
		System.out.println("Opções \nNome: ");
		novoNome = ler.nextLine();
		do {
			System.out.println("CPF: ");
			String cpf = ler.nextLine();
			valido = Usuario.validarCpf(cpf);
			if (valido) {
				novoCpf = cpf;
			} else {
				System.out.println("CPF invalido!");
			}
			if (UsuarioDao.cpfExiste(novoCpf)) {
				valido = false;
				System.out.println("\nCPF já cadastrado!");
			}
		} while (!valido);
		do {
			System.out.print("Cargo (Admin/Estoquista): ");
			cargo = ler.nextLine();
			if (cargo.trim().equalsIgnoreCase("Estoquista")) {
				usuario.setCargo("Estoquista");
				valido = true;
			} else if (cargo.trim().equalsIgnoreCase("Admin")) {
				usuario.setCargo("Administrador");
				valido = true;

			} else if (cargo.equalsIgnoreCase("")) {
				usuario.setCargo(usuario.getCargo());
			} else {
				valido = false;
				System.out.println("Cargo não existe");
			}
		} while (!valido);
		do {
			System.out.println("\nSalvar ? (y/n): ");
			opcao = ler.nextLine();
			if (opcao.equalsIgnoreCase("y")) {
				if (novoNome == "") {
					novoNome = usuario.getNome();
				}
				if (novoCpf == "") {
					novoCpf = usuario.getCpf();
				}
				usuario.setNome(novoNome);
				usuario.setCpf(novoCpf);
				UsuarioDao.alterarUsuario(usuario);
				break;
			} else if (opcao.equalsIgnoreCase("n")) {
				break;
			} else if (!opcao.equalsIgnoreCase("0")) {
				System.out.println("opção invalida");
			}
		} while (true);

	}

	public static void viewListarProduto() {
		boolean erro = true, sair = false;
		do {
			System.out.println("Listar Produto\nID  | \t NOME \t | \t Quantidade \t | \t Preço \t | \t Status");

			for (Produto p : ProdutoDao.listarProdutos()) {

				System.out.println(p.toString());
			}

			System.out.println("---------------------------------------\n");

			do {
				System.out.println("Entre com o id para editar/ativar/inativar, 0 para voltar e 'i' para incluir =>");
				String r = ler.nextLine().trim();

				if (r.equalsIgnoreCase("i")) {
					viewIncluirProduto();
					erro = false;
				} else if (r.equals("0")) {
					viewAdmin();
					erro = false;
					sair =true;
				} else if (r.matches("\\d+")) {
					int idPedido = Integer.parseInt(r);
					boolean encontrou = false;

					for (Produto p : ProdutoDao.listarProdutos()) {
						if (p.getId() == idPedido) {
							viewEditarProduto(p);
							break;
						}
					}

					if (!encontrou) {
						System.out.println("Id não encontrado!");
						erro = true;
					}
				} else {
					System.out.println("Entrada inválida! Digite um Id válido ou 'i' para incluir.");
				}
			} while (erro);
		} while (!sair);
	}

	private static void viewEditarProduto(Produto p) {
		boolean erro = true;
		do {
			System.out.println("\nOpção de edição de Produto\n");
			System.out.println("Id: " + p.getId());
			System.out.println("Nome do Produto: " + p.getNome());
			System.out.println("preço: " + p.getPreco());
			System.out.println("Qtd.Estoque: " + p.getQtd());
			System.out.println("Avaliação: " + p.getAvaliacao());
			System.out.println("Status: " + (p.isAtivo() ? "ativo" : "inativo"));
			System.out.println("---------------------------------------");
			System.out.println("Opções");
			System.out.println("1) Alterar Produto");
			System.out.println("2) Listar/Alterar imagens do produto");
			System.out.println("3) Ativar/Desativar produto");
			System.out.println("4) Voltar Listar Produto");
			System.out.print("Entre com a opção (1,2,3,4) => ");

			int opcao = ler.nextInt();
			ler.nextLine();

			switch (opcao) {
			case 1:
				viewAlterarProduto(p);
				break;
			case 2:
				viewListarImagem(p);
				break;
			case 3:
				viewAtivarDesativarProduto(p);
				break;
			case 4:
				return;
			default:
				System.out.println("Opção inválida!");
			}

			erro = true;

		} while (erro);
	}

	private static void viewAtivarDesativarProduto(Produto p) {
		boolean erro = true;

		do {
			System.out.println("\nOpção de edição de Produto\n");
			System.out.println("Id: " + p.getId());
			System.out.println("Nome do Produto: " + p.getNome());
			System.out.println("preço: " + p.getPreco());
			System.out.println("Qtd.Estoque: " + p.getQtd());
			System.out.println("Avaliação: " + p.getAvaliacao());
			System.out.println("Status: " + (p.isAtivo() ? "ativo" : "inativo"));
			System.out.println("---------------------------------------");

			boolean novoStatus = !p.isAtivo();
			String acao = novoStatus ? "Ativar" : "Desativar";

			System.out.println("<< " + acao + " Produto >>\n");

			System.out.print("Confirmar alteração? (Y/N) => ");
			String confirmar = ler.nextLine().trim().toLowerCase();

			if (confirmar.equalsIgnoreCase("y")) {
				ProdutoDao.alterarAtivo(p, novoStatus);
				p.setAtivo(novoStatus);
				System.out.println("Status alterado com sucesso!");
				erro = false;
			} else if (confirmar.equalsIgnoreCase("n")) {
				System.out.println("Operação cancelada.");
				erro = false;
			} else {
				System.out.println("Comando não reconhecido, tente novamente.");
			}
		} while (erro);

	}

	private static void viewListarImagem(Produto p) {
		boolean erro = true, sair = false;
		do {
			System.out.println("Listar Produto\nID  | \t NOME IMAGEM \t | \t DIRETÓRIO \t | \t PRINCIPAL");

			for (Imagem i : ImagemDao.listarImagem(p.getId())) {

				System.out.println(i.toString());
			}

			System.out.println("---------------------------------------");
			do {
				System.out.println("Entre com o id para REMOVER a imagem, 0 para voltar e 'i' para incluir =>");
				String r = ler.nextLine().trim();

				if (r.equalsIgnoreCase("i")) {
					viewIncluirImagem(p.getId());
					erro = false;
				} else if (r.equals("0")) {
					erro = false;
					sair = true;
				} else if (r.matches("\\d+")) {
					int idImagem = Integer.parseInt(r);
					boolean encontrou = false;

					for (Imagem i : ImagemDao.listarImagem(p.getId())) {
						if (i.getId() == idImagem) {
							do {
								System.out.println("\nDeseja remover o id:" + idImagem + " ? (y/n): ");
								String opcao = ler.nextLine();
								if (opcao.equalsIgnoreCase("y")) {
									ImagemDao.removerImagem(idImagem);
									System.out.println("Removido com sucesso !");
									break;
								} else if (opcao.equalsIgnoreCase("n")) {
									System.out.println("Operação cancelada!");
									break;
								} else {
									System.out.println("opção invalida");
								}
							} while (true);

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
					System.out.println("Entrada inválida! Digite um Id válido ou 'i' para incluir.");
				}
			} while (erro);
		} while (!sair);

	}

	private static void viewIncluirImagem(int idProduto) {
		String nome, diretorio, resposta;
		boolean sair=false;
		Imagem imagem = new Imagem();
		imagem.setIdProduto(idProduto);
		System.out.println("Incluir Imagem");
		System.out.println("Nome do Arquivo: ");
		nome = ler.nextLine();
		imagem.setNome(nome);

		System.out.println("Diretório Origem Imagem: ");
		diretorio = ler.nextLine();
		imagem.setDiretorio(diretorio);

		do {
			System.out.println("Principal(s/n): ");
			resposta = ler.nextLine();
			if (resposta.equalsIgnoreCase("s")) {
				imagem.setPrincipal(true);
				break;
			} else if (resposta.equalsIgnoreCase("n")) {
				imagem.setPrincipal(false);
				break;
			} else {
				System.out.println("opção invalida");
			}
		} while (true);
		do {
			System.out.println("Opções");
			System.out.println("1) Salvar e incluir +1 produto");
			System.out.println("2) Salvar e finalizar");
			System.out.println("3) Não salvar e finalizar");
			System.out.print("Entre com a opção (1,2,3) => ");

			int opcao = ler.nextInt();
			ler.nextLine();

			switch (opcao) {
			case 1:
				ImagemDao.incluirImagem(imagem);
				System.out.println("incluido com sucesso !");
				viewIncluirProduto();
				sair=true;
				break;
			case 2:
				ImagemDao.incluirImagem(imagem);
				System.out.println("incluido com sucesso !");
				viewListarProduto();
				sair=true;
				break;
			case 3:
				viewListarProduto();
				sair=true;
				break;
			default:
				System.out.println("Opção inválida!");
				sair=false;
			}
		} while (!sair);
	}

	private static void viewAlterarProduto(Produto p) {
		String nome, descricao, opcao, qtd;
		double preco, avaliacao;

		System.out.println("\nOpção de edição de Produto\n");
		System.out.println("Id: " + p.getId());
		System.out.println("Nome do Produto: " + p.getNome());
		System.out.println("preço: " + p.getPreco());
		System.out.println("Qtd.Estoque: " + p.getQtd());
		System.out.println("Avaliação: " + p.getAvaliacao());
		System.out.println("Status: " + (p.isAtivo() ? "ativo" : "inativo"));
		System.out.println("---------------------------------------");
		System.out.println("Opções \n");
		System.out.println("Nome do produto: ");
		nome = ler.nextLine();
		if (nome == "") {
			nome = p.getNome();
		}
		p.setNome(nome);

		System.out.println("Preço(ex:10.50): ");
		String num = ler.nextLine();

		if (num.equals("")) {
			preco = p.getPreco();
		} else {
			preco = Double.parseDouble(num);
		}
		p.setPreco(preco);

		System.out.println("Quantidade: ");
		qtd = ler.nextLine();
		if (qtd.equals("")) {
			p.setQtd(p.getQtd());
		} else {
			p.setQtd(Integer.parseInt(qtd));
		}

		ler.nextLine();

		System.out.println("Descrição detalhada: ");
		descricao = ler.nextLine();
		if (descricao.equals("")) {
			descricao = p.getDescricao();
		}
		p.setDescricao(descricao);

		System.out.println("Avaliação(Ex: 5.50): ");
		num = ler.nextLine();

		if (num.equals("")) {
			avaliacao = p.getAvaliacao();
		} else {
			avaliacao = Double.parseDouble(num);
		}
		p.setAvaliacao(avaliacao);
		do {
			System.out.println("\nSalvar ? (y/n): ");
			opcao = ler.nextLine();
			if (opcao.equalsIgnoreCase("y")) {
				ProdutoDao.alterarProduto(p);
				System.out.println("Editado com sucesso !");
				break;
			} else if (opcao.equalsIgnoreCase("n")) {
				System.out.println("Operação cancelada!");
				break;
			} else {
				System.out.println("opção invalida");
			}
		} while (true);

	}

	private static void viewIncluirProduto() {
		String nome, descricao;
		double preco, avaliacao;
		int qtd;
		boolean valido = true;
		Produto produto = new Produto();

		System.out.println("Incluir Produtos");
		System.out.println("Nome do produto: ");
		nome = ler.nextLine();
		produto.setNome(nome);

		System.out.println("Preço(ex:10.50): ");
		preco = Double.parseDouble(ler.nextLine());
		produto.setPreco(preco);

		System.out.println("Quantidade: ");
		qtd = ler.nextInt();
		ler.nextLine();
		produto.setQtd(qtd);

		System.out.println("Descrição detalhada: ");
		descricao = ler.nextLine();
		produto.setDescricao(descricao);

		System.out.println("Avaliação(Ex: 5.50): ");
		avaliacao = Double.parseDouble(ler.nextLine());
		produto.setAvaliacao(avaliacao);

		do {
			System.out.println("Salvar (y/n) =>");
			String salvar = (ler.nextLine().trim().toLowerCase());

			if (salvar.equals("y")) {
				try {
					ProdutoDao.incluirProduto(produto);
					System.out.println("Produto incluído com sucesso!");
					viewIncluirImagem(ProdutoDao.ultimoIdAdd());
					return;
				} catch (Exception e) {
					System.out.println("Erro ao incluir Produto!");
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
