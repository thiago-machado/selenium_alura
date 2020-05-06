package br.com.selenium.testes.po;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LeiloesPage {

	private WebDriver driver;

	public LeiloesPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("http://localhost:8080/leiloes");
	}

	public DetalhesDoLeilaoPage detalhes(int posicao) {
		/*
		 * Vamos pegar os detalhes do 1º item da lista. Precisamos pedir ao Selenium
		 * para achar todos os elementos da página com o texto Exibir, e clicar no
		 * primeiro deles.
		 */
		List<WebElement> elementos = driver.findElements(By.linkText("exibir"));
		elementos.get(posicao - 1).click(); // acessando o elemento da lista pela sua posição
		return new DetalhesDoLeilaoPage(driver);
	}

	public NovoLeilaoPage novo() {
		// clica no link de novo leilao
		driver.findElement(By.linkText("Novo Leilão")).click();
		// retorna a classe que representa a nova pagina
		return new NovoLeilaoPage(driver);
	}

	public boolean existe(String produto, double valor, String usuario, boolean usado) {

		return driver.getPageSource().contains(produto) && driver.getPageSource().contains(String.valueOf(valor))
				&& driver.getPageSource().contains(usado ? "Sim" : "Não");

	}
}
