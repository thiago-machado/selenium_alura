package br.com.selenium.testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.selenium.testes.po.UsuariosPage;

/**
 * Link de download da aplica��o utilizada para teste:
 * http://s3.amazonaws.com/caelum-online-public/PM-74/leiloes.zip
 * 
 * Acessar o diret�rio da aplica��o baixada. Usando o terminal, digitar a
 * seguinte instru��o: <b>ant jetty.run</b>
 */
public class TesteUsuario {

	private WebDriver driver;
	private UsuariosPage usuariosPage;

	@Before
	public void configuracaoInicial() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\thiago.machado\\eclipse-workspace\\selenium\\libs\\chromedriver.exe");
		driver = new ChromeDriver();

		/*
		 * Nossos testes podem falhar de acordo com os dados que j� existem em nossa
		 * aplica��o. O ideal � sempre termos nossa aplica��o limpa, sem nenhum dado.
		 * Dessa forma, um teste nunca afetar� o outro.
		 * 
		 * Voc� pode fazer isso de diferentes formas. A melhor delas � sempre conversar
		 * com a equipe de desenvolvimento e ver como voc� pode limpar a base de testes.
		 * 
		 * Em nossa aplica��o, temos uma URL espec�fica para isso. Se voc� acessar
		 * http://localhost:8080/apenas-teste/limpa, o sistema limpar� nosso banco de
		 * dados.
		 */
		driver.get("http://localhost:8080/apenas-teste/limpa");
		usuariosPage = new UsuariosPage(driver);
	}

	/*
	 * Veja s� como nossos testes ficaram mais claros! E o melhor, eles n�o conhecem
	 * a implementa��o por baixo de cada uma das p�ginas! Essa implementa��o est�
	 * escondida em cada uma das classes espec�ficas!
	 * 
	 * Sabemos que nosso HTML muda frequentemente. Se tivermos classes que
	 * representam as nossas v�rias p�ginas do sistema, no momento de uma mudan�a de
	 * HTML, basta que mudemos na classe correta, e os testes n�o ser�o afetados!
	 * Esse � o poder do encapsulamento, um dos grandes princ�pios da programa��o
	 * orientada a objetos, bem utilizada em nossos c�digos de teste.
	 * 
	 * A id�ia de escondermos a manipula��o de cada uma das nossas p�ginas em
	 * classes espec�ficas � inclusive um padr�o de projetos. Esse padr�o �
	 * conhecido por Page Object. Pense em escrever Page Objects em seus testes.
	 * Eles garantir�o que seus testes ser�o de f�cil manuten��o por muito tempo.
	 */
	@Test
	public void deveAdicionarUmUsuarioUsandoPadraoProjetoPageObject() {
		String usuario = "Reginaldo Rossi";
		String email = "reginaldo@terra.com.br";

		usuariosPage.visita();
		usuariosPage.novo().cadastra(usuario, email);
		assertTrue(usuariosPage.existeNaListagem(usuario, email));
	}

	@Test
	public void deveAdicionarUmUsuario() {

		String usuarioCadastro = "Elaine Cavalcante";
		String emailCadastro = "elaine@teste.com.br";

		driver.get("http://localhost:8080/usuarios/new");

		WebElement nome = driver.findElement(By.name("usuario.nome"));
		WebElement email = driver.findElement(By.name("usuario.email"));

		nome.sendKeys(usuarioCadastro);
		email.sendKeys(emailCadastro);

		/*
		 * Funciona de modo igual ao submit abaixo.
		 */
		// WebElement botaoSalvar =driver.findElement(By.id("btnSalvar"));
		// botaoSalvar.click();

		/*
		 * Uma outra alternativa mais simples ainda � "submeter" qualquer uma das caixas
		 * de texto! O Selenium automaticamente procurar� o form na qual a caixa de
		 * texto est� contida e o submeter�!
		 */
		nome.submit();

		/*
		 * Selenium nos d� o c�digo-fonte HTML inteiro da p�gina atual, atrav�s do
		 * m�todo driver.getPageSource().
		 */
		assertTrue(driver.getPageSource().contains(usuarioCadastro));
		assertTrue(driver.getPageSource().contains(emailCadastro));
	}

	@Test
	public void naoDeveAdicionarUmUsuarioSemNome() {

		driver.get("http://localhost:8080/usuarios/new");

		WebElement email = driver.findElement(By.name("usuario.email"));

		email.sendKeys("ronaldo2009@terra.com.br");
		email.submit();

		assertTrue(driver.getPageSource().contains("Nome obrigatorio!"));
	}

	@Test
	public void naoDeveAdicionarUmUsuarioSemNomeOuEmail() {

		driver.get("http://localhost:8080/usuarios/new");

		WebElement email = driver.findElement(By.name("usuario.email"));
		email.submit();

		assertTrue(driver.getPageSource().contains("Nome obrigatorio!"));
		assertTrue(driver.getPageSource().contains("E-mail obrigatorio!"));
	}

	@Test
	public void verificandoQueLinkNovoUsuarioFunciona() {
		driver.get("http://localhost:8080/usuarios");
		driver.findElement(By.linkText("Novo Usu�rio")).click();

		// Verificando se fui redirecionado para a a p�gina /usuarios/new
		assertEquals("http://localhost:8080/usuarios/new", driver.getCurrentUrl());
	}

	@Test
	public void deveDeletarUmUsuario() {

		String usuarioCadastro = "Mois�s";
		String emailCadastro = "moises@teste.com.br";

		usuariosPage.visita();
		usuariosPage.novo().cadastra(usuarioCadastro, emailCadastro);
		assertTrue(usuariosPage.existeNaListagem(usuarioCadastro, emailCadastro));

		usuariosPage.deletaUsuarioNaPosicao(1);
		assertFalse(usuariosPage.existeNaListagem(usuarioCadastro, emailCadastro));
	}

	@Test
	public void deveAlterarUmUsuario() {

		String usuarioAntigo = "Luciano Marques";
		String usuarioNovo = "Luis Silva";
		String emailAntigo = "luciano@teste.com.br";
		String emailNovo = "luis@silva.com";

		usuariosPage.visita();
		usuariosPage.novo().cadastra(usuarioAntigo, emailAntigo);
		usuariosPage.altera(1).para(usuarioNovo, emailNovo);

		assertFalse(usuariosPage.existeNaListagem(usuarioAntigo, emailAntigo));
		assertTrue(usuariosPage.existeNaListagem(usuarioNovo, emailNovo));
	}

	@After
	public void encerra() {
		driver.close();
	}
}
