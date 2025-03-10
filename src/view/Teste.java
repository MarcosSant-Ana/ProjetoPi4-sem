package view;

public class Teste {
	public static void main(String[] args) {
		String qUsuario=Login.login();
		if (qUsuario.equals("Administrador")) {
			BackOfficeAdm.viewAdmin();
		} else if (qUsuario.equals("Estoquista")) {
			BackOfficeEstoquista.viewEstoquista();
		}

	}

	
}
