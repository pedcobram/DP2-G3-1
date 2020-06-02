
package org.springframework.samples.petclinic.UI.nacho;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class EditMatchUI {

	private WebDriver		driver;
	@SuppressWarnings("unused")
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int				port;


	@BeforeEach
	public void setUp() throws Exception {

		//		String pathToGeckoDriver = "C:\\Users\\Nacho\\Desktop\\DP";
		//		System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");

		String path = System.getenv("webdriver.chrome.driver");
		System.setProperty("webdriver.chrome.driver", path);

		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	@Test
	public void test_F16_Positivo() throws Exception {
		this.comoCompetitionAdmin("pedro");
		this.cuandoAccedaAUnPartidoNoFinalizadoDeMisCompeticiones();
		this.entoncesPodreEditarLafecha();

	}
	@Test
	void test_F16_Negativo() throws Exception {
		this.comoCompetitionAdmin("pedro");
		this.cuandoAccedaAUnPartidoFinalizadoDeMisCompeticiones();
		this.entoncesNoPodreEditarLafecha();
	}

	private void comoCompetitionAdmin(final String usuario) {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(usuario);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(usuario);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assertions.assertEquals(usuario.toUpperCase(), this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());

	}

	private void cuandoAccedaAUnPartidoNoFinalizadoDeMisCompeticiones() {
		this.driver.get("www.localhost:" + this.port + "/competition/mylist");
		this.driver.get("www.localhost:" + this.port + "/competitions/3");
		this.driver.findElement(By.linkText("Rondas")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td/a/b")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[4]/td/a/b")).click();

	}
	private void cuandoAccedaAUnPartidoFinalizadoDeMisCompeticiones() {
		this.driver.get("www.localhost:" + this.port + "/competition/mylist");
		this.driver.get("www.localhost:" + this.port + "/competitions/3");
		this.driver.findElement(By.linkText("Rondas")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td/a/b")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td/a/b")).click();

	}
	private void entoncesPodreEditarLafecha() {
		this.driver.findElement(By.id("fecha")).click();
		this.driver.findElement(By.id("matchDate")).click();
		this.driver.findElement(By.id("matchDate")).clear();
		this.driver.findElement(By.id("matchDate")).sendKeys("2021/05/31 20:30");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//tr[2]/td")).click();
		Assertions.assertEquals("Mon May 31 20:30:00 CEST 2021", this.driver.findElement(By.xpath("//tr[2]/td")).getText());

	}
	private void entoncesNoPodreEditarLafecha() {

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.id("fecha"));
		});
	}
	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	@SuppressWarnings("unused")
	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}

}
