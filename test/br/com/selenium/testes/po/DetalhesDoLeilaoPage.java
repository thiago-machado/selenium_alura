package br.com.selenium.testes.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DetalhesDoLeilaoPage {

	private WebDriver driver;

	public DetalhesDoLeilaoPage(WebDriver driver) {
		this.driver = driver;
	}

	public void lance(String usuario, double valor) {
		WebElement txtValor = driver.findElement(By.name("lance.valor"));
		Select combo = new Select(driver.findElement(By.name("lance.usuario.id")));

		combo.selectByVisibleText(usuario);
		txtValor.sendKeys(String.valueOf(valor));

		/*
		 * Precisamos agora submeter o formulário. Mas dessa vez não vamos submeter pela
		 * caixa de texto. Veja o código-fonte da página! O botão não submete o
		 * formulário, ele é um simples "button" do HTML. Precisamos clicar nele para
		 * que o AJAX funcione.
		 */
		driver.findElement(By.id("btnDarLance")).click();
	}

	public boolean existeLance(String usuario, double valor) {
		WebElement tabela = driver.findElement(By.id("lancesDados"));

		/*
		 * Uma requisição Ajax acontece por trás dos panos, e pode levar alguns segundos
		 * para terminar. Se invocarmos o método existeLance() e a requisição ainda não
		 * tiver voltado, o método retornará falso! Precisamos fazer com que esse método
		 * aguarde até a requisição terminar.
		 * 
		 * Para isso, pediremos ao Selenium para que espere alguns segundos até que a
		 * requisição termine. Isso é conhecido por explicit wait. Para usá-lo,
		 * precisamos fazer uso da classe WebDriverWait. No construtor, ela recebe o
		 * driver e a quantidade máxima de segundos a esperar. Em seguida, ela recebe a
		 * condição que faz esse tempo parar. No nosso caso, a condição é se o texto
		 * aparecer. Para isso, faremos uso de uma outra classe, a ExpectedConditions, e
		 * passaremos a expectativa correta
		 */
		Boolean temUsuario = new WebDriverWait(driver, 10)
				.until(ExpectedConditions.textToBePresentInElement(tabela, usuario));

		// Caso "temUsuario" seja TRUE, verifica se existe o "valor" na tela
		if (temUsuario)
			return driver.getPageSource().contains(String.valueOf(valor));
		return false;
	}
}
