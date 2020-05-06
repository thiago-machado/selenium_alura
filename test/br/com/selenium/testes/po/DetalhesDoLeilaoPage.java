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
		 * Precisamos agora submeter o formul�rio. Mas dessa vez n�o vamos submeter pela
		 * caixa de texto. Veja o c�digo-fonte da p�gina! O bot�o n�o submete o
		 * formul�rio, ele � um simples "button" do HTML. Precisamos clicar nele para
		 * que o AJAX funcione.
		 */
		driver.findElement(By.id("btnDarLance")).click();
	}

	public boolean existeLance(String usuario, double valor) {
		WebElement tabela = driver.findElement(By.id("lancesDados"));

		/*
		 * Uma requisi��o Ajax acontece por tr�s dos panos, e pode levar alguns segundos
		 * para terminar. Se invocarmos o m�todo existeLance() e a requisi��o ainda n�o
		 * tiver voltado, o m�todo retornar� falso! Precisamos fazer com que esse m�todo
		 * aguarde at� a requisi��o terminar.
		 * 
		 * Para isso, pediremos ao Selenium para que espere alguns segundos at� que a
		 * requisi��o termine. Isso � conhecido por explicit wait. Para us�-lo,
		 * precisamos fazer uso da classe WebDriverWait. No construtor, ela recebe o
		 * driver e a quantidade m�xima de segundos a esperar. Em seguida, ela recebe a
		 * condi��o que faz esse tempo parar. No nosso caso, a condi��o � se o texto
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
