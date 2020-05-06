package br.com.selenium.testes;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.selenium.testes.po.LeiloesPage;
import br.com.selenium.testes.po.NovoLeilaoPage;
import br.com.selenium.testes.po.UsuariosPage;

public class TesteLeiloes {

	private WebDriver driver;
	private LeiloesPage leiloes;

	@Before
	public void inicializa() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\thiago.machado\\eclipse-workspace\\selenium\\libs\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.get("http://localhost:8080/apenas-teste/limpa");
		
		leiloes = new LeiloesPage(driver);

		// Precisa cadastrar um usuário antes de cadastrar um Leilão
		UsuariosPage usuarios = new UsuariosPage(driver);
		usuarios.visita();
		usuarios.novo().cadastra("Paulo Henrique", "paulo_henrique@teste.com");
	}

	@After
	public void finaliza() {
		driver.close();
	}

	@Test
	public void deveCadastrarUmLeilao() {

		String produto = "Geladeira";
		double valor = 500.87;
		String usuario = "Paulo Henrique";
		boolean usado = true;

		leiloes.visita();
		NovoLeilaoPage novoLeilao = leiloes.novo();
		novoLeilao.preenche(produto, valor, usuario, usado);

		assertTrue(leiloes.existe(produto, valor, usuario, usado));

	}

	@Test
	public void deveExibirMensagemErroQuandoNaoPossuiNomeMaisValor() {

		String produto = "";
		double valor = 0;
		String usuario = "Paulo Henrique";
		boolean usado = true;

		leiloes.visita();
		NovoLeilaoPage novoLeilao = leiloes.novo();
		novoLeilao.preenche(produto, valor, usuario, usado);

		assertTrue(novoLeilao.validacaoDeProdutoApareceu());
		assertTrue(novoLeilao.validacaoDeValorApareceu());

	}

}
