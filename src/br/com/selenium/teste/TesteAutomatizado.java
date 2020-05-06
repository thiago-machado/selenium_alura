package br.com.selenium.teste;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteAutomatizado {

	public static void main(String[] args) throws MalformedURLException {
		
		// Para baixar o driver: http://chromedriver.storage.googleapis.com/index.html
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\thiago.machado\\eclipse-workspace\\selenium\\libs\\chromedriver.exe");
		
		// abre Chrome
		WebDriver driver = new ChromeDriver();

		// acessa o site do google
		driver.get("https://www.google.com");

		// digita no campo com nome "q" do google
		WebElement campoDeTexto = driver.findElement(By.name("q"));
		campoDeTexto.sendKeys("Caelum");

		// submete o form
		campoDeTexto.submit();

	}

}
