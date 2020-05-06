package br.com.selenium.testes.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class NovoLeilaoPage {

	private WebDriver driver;

	public NovoLeilaoPage(WebDriver driver) {
		this.driver = driver;
	}

	public void preenche(String nome, double valor, String usuario, boolean usado) {

		WebElement txtNome = driver.findElement(By.name("leilao.nome"));
		WebElement txtValor = driver.findElement(By.name("leilao.valorInicial"));

		txtNome.sendKeys(nome);
		txtValor.sendKeys(String.valueOf(valor)); // sendKey aceita somente String, por isso o cast

		// Pegando o ComboBox
		Select combo = new Select(driver.findElement(By.name("leilao.usuario.id")));
		
		// Escolhendo a opção do "combo" pelo valor exibido no mesmo (no caso, é o nome do usuário)
		combo.selectByVisibleText(usuario);

		// Caso usado, pegamos o elemento e executamos um "click", marcando o mesmo como TRUE
		if (usado) {
			WebElement ckUsado = driver.findElement(By.name("leilao.usado"));
			ckUsado.click();
		}

		txtNome.submit();
	}
	
	public boolean validacaoDeProdutoApareceu() {
		return driver.getPageSource().contains("Nome obrigatorio!");
	}

	public boolean validacaoDeValorApareceu() {
		return driver.getPageSource().contains("Valor inicial deve ser maior que zero!");
	}

}
