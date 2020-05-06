package br.com.selenium.testes;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.selenium.testes.po.DetalhesDoLeilaoPage;
import br.com.selenium.testes.po.LeiloesPage;
import br.com.selenium.testes.po.UsuariosPage;

public class TesteLances {

	private WebDriver driver;
	private LeiloesPage leiloes;

	@Before
	public void inicializa() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\thiago.machado\\eclipse-workspace\\selenium\\libs\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.get("http://localhost:8080/apenas-teste/limpa");

		/*
		 * Para que o teste de um lance funcione, � necess�rio criar dois usu�rios (um
		 * que criar� o leil�o e outro para dar o lance) e um leil�o.
		 */
		UsuariosPage usuarios = new UsuariosPage(driver);
		usuarios.visita();
		usuarios.novo().cadastra("Paulo Henrique", "paulo@henrique.com");
		usuarios.novo().cadastra("Jos� Alberto", "jose@alberto.com");

		leiloes = new LeiloesPage(driver);
		leiloes.visita();
		leiloes.novo().preenche("Geladeira", 100, "Paulo Henrique", false);
	}

	@After
	public void finaliza() {
		driver.close();
	}

	/*
	 * Via Web, veja o que acontece quando damos um lance. A p�gina n�o "pisca", mas
	 * � atualizada! Isso acontece porque nossa aplica��o web fez uso do que
	 * chamamos de Ajax. Ou seja, a requisi��o aconteceu, mas ela foi por baixo dos
	 * panos, sem o usu�rio ver. Precisamos testar essa a��o, e precisamos levar em
	 * conta que ela � executada via Ajax.
	 */
	@Test
	public void deveFazerUmLance() {
		DetalhesDoLeilaoPage lances = leiloes.detalhes(1);
		lances.lance("Jos� Alberto", 150);
		assertTrue(lances.existeLance("Jos� Alberto", 150));
	}
}
