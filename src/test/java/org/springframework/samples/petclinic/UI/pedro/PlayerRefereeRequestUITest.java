
package org.springframework.samples.petclinic.UI.pedro;

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
public class PlayerRefereeRequestUITest {

	private WebDriver		driver;
	@SuppressWarnings("unused")
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int				port;


	@BeforeEach
	public void setUp() throws Exception {

		//String pathToGeckoDriver = "C:\\Users\\pedro\\Desktop";
		//System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");

		String path = System.getenv("webdriver.chrome.driver");
		System.setProperty("webdriver.chrome.driver", path);

		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	//

	@Test
	public void R21Positivo_FicharJugadoresDeOtroEquipo() throws Exception {
		this.comoPresidente("presidente2");
		this.puedoSolicitarUnTraspasoDeJugadorAOtroPresidenteYQueLoAcepte("presidente1");
		this.paraAñadirloAMiEquipoYMerjorarMiPlantilla();
	}

	@Test
	public void R21Negativo_FicharJugadorDeOtroEquipo() throws Exception {
		this.comoPresidente("presidente2");
		this.puedoSolicitarUnTraspasoAlPresidenteDeOtroEquipoConCantidadInferiorAUnDecimoDeSuValor();
		this.entoncesSeMostraraUnMensajeDeErrorIndicandolo();
	}

	private void comoPresidente(final String presidente) {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(presidente);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(presidente);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void puedoSolicitarUnTraspasoDeJugadorAOtroPresidenteYQueLoAcepte(final String presidente) {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("(//input[@type='button'])[5]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[3]/td[6]/a/b")).click();
		this.driver.findElement(By.id("offer")).click();
		this.driver.findElement(By.id("offer")).clear();
		this.driver.findElement(By.id("offer")).sendKeys("700000");
		this.driver.findElement(By.id("contractTime")).click();
		this.driver.findElement(By.id("contractTime")).clear();
		this.driver.findElement(By.id("contractTime")).sendKeys("2");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li[2]/a/span[2]")).click();
		this.driver.findElement(By.linkText("Desconectarse")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(presidente);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(presidente);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='JUGADORES - TRASPASOS RECIBIDOS']")).click();
		this.driver.findElement(By.xpath("(//a[contains(text(),'Aceptar')])[2]")).click();
		this.driver.switchTo().alert().accept();
		this.driver.findElement(By.linkText("PRESIDENTE1")).click();
		this.driver.findElement(By.linkText("Desconectarse")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void paraAñadirloAMiEquipoYMerjorarMiPlantilla() {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente2");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente2");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("MIS JUGADORES")).click();
		Assertions.assertEquals("Fernando Reges", this.driver.findElement(By.linkText("Fernando Reges")).getText());
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		Assertions.assertEquals("Real Madrid Club de Fútbol", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).getText());
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[4]")).click();
		Assertions.assertEquals("7000000 €", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[4]")).getText());
	}

	private void puedoSolicitarUnTraspasoAlPresidenteDeOtroEquipoConCantidadInferiorAUnDecimoDeSuValor() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("(//input[@type='button'])[5]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[3]/td[6]/a/b")).click();
		this.driver.findElement(By.id("offer")).click();
		this.driver.findElement(By.id("offer")).clear();
		this.driver.findElement(By.id("offer")).sendKeys("70000");
		this.driver.findElement(By.id("contractTime")).click();
		this.driver.findElement(By.id("contractTime")).clear();
		this.driver.findElement(By.id("contractTime")).sendKeys("2");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnMensajeDeErrorIndicandolo() {
		this.driver.findElement(By.xpath("//form[@id='add-referee-form']/div/div/div")).click();
		Assertions.assertEquals("El salario debe ser inferior al valor total del jugador y mayor que un décimo de su valor", this.driver.findElement(By.xpath("//form[@id='add-referee-form']/div/div/div/span[2]")).getText());
	}

	//

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
