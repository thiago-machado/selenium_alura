package br.com.selenium.testes.po;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsuariosPage {

	private WebDriver driver;

	public UsuariosPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("localhost:8080/usuarios");
	}

	public AlteraUsuarioPage altera(int posicao) {
		driver.findElements(By.linkText("editar")).get(posicao - 1).click();
		return new AlteraUsuarioPage(driver);
	}

	public NovoUsuarioPage novo() {
		// clica no link de novo usuario
		driver.findElement(By.linkText("Novo Usu�rio")).click();

		// retorna a classe que representa a nova pagina
		return new NovoUsuarioPage(driver);
	}

	public boolean existeNaListagem(String nome, String email) {
		// verifica se ambos existem na listagem
		return driver.getPageSource().contains(nome) && driver.getPageSource().contains(email);
	}

	public void deletaUsuarioNaPosicao(int posicao) {
		driver.findElements(By.tagName("button")).get(posicao - 1).click();

		// pega o alert que est� aberto
		Alert alert = driver.switchTo().alert();

		// confirma
		alert.accept();
	}
}
