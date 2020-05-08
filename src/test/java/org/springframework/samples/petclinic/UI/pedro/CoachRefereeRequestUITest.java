
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
public class CoachRefereeRequestUITest {

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
	public void R21Positivo_FicharEntrenadorDeOtroEquipo() throws Exception {
		this.comoPresidente("presidente1");
		this.puedoSolicitarUnTraspasoDeEntrenadorAlPresidenteDeOtroEquipoYQueLoAcepte();
		this.paraIntercambiarEntrenadoresEntreEquiposPagandoLaClausulaDeCesionYElSalario();
	}

	@Test
	public void R21Negativo_FicharEntrenadorDeOtroEquipo() throws Exception {
		this.comoPresidente("presidente1");
		this.puedoSolicitarUnTraspasoDeEntrenadorAlPresidenteDeOtroEquipo();
		this.siLaCantidadEsInferiorA100000SeMostraraUnError();
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

	private void puedoSolicitarUnTraspasoDeEntrenadorAlPresidenteDeOtroEquipoYQueLoAcepte() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("(//input[@type='button'])[6]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[4]/a/b")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='ENTRENADORES - T. SOLICITADOS']")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[3]")).click();
		Assertions.assertEquals("Zinedine Zidane", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[3]")).getText());
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[4]")).click();
		Assertions.assertEquals("Real Madrid Club de Fútbol", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[4]")).getText());
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[5]")).click();
		Assertions.assertEquals("1000000€", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[5]")).getText());
		this.driver.findElement(By.linkText("PRESIDENTE1")).click();
		this.driver.findElement(By.linkText("Desconectarse")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void paraIntercambiarEntrenadoresEntreEquiposPagandoLaClausulaDeCesionYElSalario() {
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente2");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente2");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='ENTRENADORES - T. RECIBIDOS']")).click();
		this.driver.findElement(By.linkText("Aceptar")).click();
		this.driver.switchTo().alert().accept();
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.xpath("//tr[6]/td")).click();
		Assertions.assertEquals("Julen Lopetegui", this.driver.findElement(By.xpath("//a/b")).getText());
		this.driver.findElement(By.xpath("//tr[8]/td")).click();
		Assertions.assertEquals("596000000 €", this.driver.findElement(By.xpath("//tr[8]/td")).getText());
	}

	private void puedoSolicitarUnTraspasoDeEntrenadorAlPresidenteDeOtroEquipo() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("(//input[@type='button'])[6]")).click();
		this.driver.findElement(By.linkText("Solicitar traspaso")).click();
		this.driver.findElement(By.xpath("//form[@id='add-referee-form']/div/div")).click();
		this.driver.findElement(By.id("offer")).clear();
		this.driver.findElement(By.id("offer")).sendKeys("100");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//form[@id='add-referee-form']/div/div/div")).click();
	}

	private void siLaCantidadEsInferiorA100000SeMostraraUnError() {
		Assertions.assertEquals("tiene que ser mayor o igual que 1000000", this.driver.findElement(By.xpath("//form[@id='add-referee-form']/div/div/div/span[2]")).getText());
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
