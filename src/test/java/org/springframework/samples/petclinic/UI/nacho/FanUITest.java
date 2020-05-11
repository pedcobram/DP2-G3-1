
package org.springframework.samples.petclinic.UI.nacho;

import java.util.NoSuchElementException;
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
public class FanUITest {

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
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test_F1_Positivo() throws Exception {
		this.comoAuthenticated("manuel");
		this.cuandoAccedaAUnClubYPulseEnSeguir();
		this.entoncesPodreVerNoticiasRelevantesDelClub();

	}
	@Test
	public void test_F1_Positivo1() throws Exception {
		this.comoAuthenticated("ignacio");
		this.cuandoVayaAInicioyPulseSerFanVip();
		this.entoncesIntroducireUnaTarjetaYPodreSerFanVipClub();

	}
	@Test
	public void test_F1_Negativo() throws Exception {
		this.comoAuthenticated("ignacio");
		this.cuandoAccedaAUnClubYPulseEnSeguir();
		this.entoncesMeMostraraMensajeYaSoyFan();

	}
	@Test
	public void test_F1_Negativo1() throws Exception {
		this.comoAnonimo();
		this.cuandoAccedaAUnClub();
		this.entoncesMeMandaraALogin();

	}
	private void comoAnonimo() {
		this.driver.get("www.localhost:" + this.port);
	}
	private void comoAuthenticated(final String usuario) {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(usuario);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(usuario);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assertions.assertEquals(usuario.toUpperCase(), this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());

	}
	private void cuandoAccedaAUnClubYPulseEnSeguir() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td/a/b")).click();
		this.driver.findElement(By.linkText("Seguir")).click();
	}
	private void entoncesPodreVerNoticiasRelevantesDelClub() {
		this.driver.findElement(By.linkText("No, quiero ser fan normal")).click();
		Assertions.assertEquals("Sevilla Fútbol Club", this.driver.findElement(By.xpath("//a[@id='urlClub']/b")).getText());
		Assertions.assertEquals("Dejar de seguir a este club", this.driver.findElement(By.id("deleteFan")).getText());
	}

	private void cuandoVayaAInicioyPulseSerFanVip() {
		this.driver.findElement(By.linkText("Ser Fan Vip")).click();

	}

	private void entoncesIntroducireUnaTarjetaYPodreSerFanVipClub() {
		this.driver.findElement(By.id("creditCard.creditCardNumber")).clear();
		this.driver.findElement(By.id("creditCard.creditCardNumber")).sendKeys("7894561237894561");
		this.driver.findElement(By.id("creditCard.expirationDate")).clear();
		this.driver.findElement(By.id("creditCard.expirationDate")).sendKeys("11/23");
		this.driver.findElement(By.id("creditCard.cvv")).clear();
		this.driver.findElement(By.id("creditCard.cvv")).sendKeys("879");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

	}
	private void entoncesMeMostraraMensajeYaSoyFan() {
		Assertions.assertEquals("Ya eres seguir de un club", this.driver.findElement(By.id("existFan")).getText());
	}

	private void cuandoAccedaAUnClub() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();

	}
	private void entoncesMeMandaraALogin() {
		Assertions.assertEquals("Please sign in", this.driver.findElement(By.xpath("//h2")).getText());
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
