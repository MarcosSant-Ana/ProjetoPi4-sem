package model;

public class Imagem {
private int id,idProduto;
private String nome,diretorio;
private boolean principal;


public Imagem() {
	this.principal = false;
}

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getIdProduto() {
	return idProduto;
}
public void setIdProduto(int idProduto) {
	this.idProduto = idProduto;
}
public String getDiretorio() {
	return diretorio;
}
public void setDiretorio(String diretorio) {
	this.diretorio = diretorio;
}
public boolean isPrincipal() {
	return principal;
}
public void setPrincipal(boolean principal) {
	this.principal = principal;
}

@Override
public String toString() {
	 return this.id + " | \t" +this.nome+ "\t | \t"  + this.diretorio + "\t | \t"  +this.principal;
}
}
