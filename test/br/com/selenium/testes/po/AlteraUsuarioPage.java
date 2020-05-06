package br.com.selenium.testes.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AlteraUsuarioPage {

	private WebDriver driver;

	public AlteraUsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public UsuariosPage para(String nome, String email) {
		
		WebElement txtNome = driver.findElement(By.name("usuario.nome"));
		WebElement txtEmail = driver.findElement(By.name("usuario.email"));

		// Limpando os campos de nome e email antes de realizar a edição
		txtNome.clear();
		txtEmail.clear();

		// Inserindo o novo nome e e-mail
		txtNome.sendKeys(nome);
		txtEmail.sendKeys(email);

		txtNome.submit();
		return new UsuariosPage(driver);
	}

}
