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
 * Link de download da aplicação utilizada para teste:
 * http://s3.amazonaws.com/caelum-online-public/PM-74/leiloes.zip
 * 
 * Acessar o diretório da aplicação baixada. Usando o terminal, digitar a
 * seguinte instrução: <b>ant jetty.run</b>
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
		 * Nossos testes podem falhar de acordo com os dados que já existem em nossa
		 * aplicação. O ideal é sempre termos nossa aplicação limpa, sem nenhum dado.
		 * Dessa forma, um teste nunca afetará o outro.
		 * 
		 * Você pode fazer isso de diferentes formas. A melhor delas é sempre conversar
		 * com a equipe de desenvolvimento e ver como você pode limpar a base de testes.
		 * 
		 * Em nossa aplicação, temos uma URL específica para isso. Se você acessar
		 * http://localhost:8080/apenas-teste/limpa, o sistema limpará nosso banco de
		 * dados.
		 */
		driver.get("http://localhost:8080/apenas-teste/limpa");
		usuariosPage = new UsuariosPage(driver);
	}

	/*
	 * Veja só como nossos testes ficaram mais claros! E o melhor, eles não conhecem
	 * a implementação por baixo de cada uma das páginas! Essa implementação está
	 * escondida em cada uma das classes específicas!
	 * 
	 * Sabemos que nosso HTML muda frequentemente. Se tivermos classes que
	 * representam as nossas várias páginas do sistema, no momento de uma mudança de
	 * HTML, basta que mudemos na classe correta, e os testes não serão afetados!
	 * Esse é o poder do encapsulamento, um dos grandes princípios da programação
	 * orientada a objetos, bem utilizada em nossos códigos de teste.
	 * 
	 * A idéia de escondermos a manipulação de cada uma das nossas páginas em
	 * classes específicas é inclusive um padrão de projetos. Esse padrão é
	 * conhecido por Page Object. Pense em escrever Page Objects em seus testes.
	 * Eles garantirão que seus testes serão de fácil manutenção por muito tempo.
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
		 * Uma outra alternativa mais simples ainda é "submeter" qualquer uma das caixas
		 * de texto! O Selenium automaticamente procurará o form na qual a caixa de
		 * texto está contida e o submeterá!
		 */
		nome.submit();

		/*
		 * Selenium nos dá o código-fonte HTML inteiro da página atual, através do
		 * método driver.getPageSource().
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
		driver.findElement(By.linkText("Novo Usuário")).click();

		// Verificando se fui redirecionado para a a página /usuarios/new
		assertEquals("http://localhost:8080/usuarios/new", driver.getCurrentUrl());
	}

	@Test
	public void deveDeletarUmUsuario() {

		String usuarioCadastro = "Moisés";
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
