
package org.springframework.samples.petclinic.UI.gonzalo;

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
public class ContractCommercialUITest {

	private WebDriver		driver;
	@SuppressWarnings("unused")
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int				port;


	@BeforeEach
	public void setUp() throws Exception {

		String pathToGeckoDriver = "C:\\Users\\gonzalo\\Desktop";
		System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");

		//String path = System.getenv("webdriver.chrome.driver");
		//System.setProperty("webdriver.chrome.driver", path);

		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void Positivo_PresidenteAdquirirContrato() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaALosDetallesDelContratoComercialPrimero();
		Assert.assertNotNull(this.driver.findElement(By.linkText("Añadir Contrato Publicitario")));
	}

	// Presidente no puede adquirir contrato si ya tiene ese mismo contrato
	@Test
	public void Negativo_PresidenteAdquirirContrato_1() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaALosDetallesDelContratoComercialPrimero();
		this.pulseAñadirContrato();
		this.cuandoAccedaALosDetallesDelContratoComercialPrimero();
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.linkText("Añadir Contrato Publicitario"));
		});
		Assert.assertEquals("Este contrato es ya tuyo. Visita tu club para mas información.", this.driver.findElement(By.xpath("//div/div/p")).getText());
	}

	// Presidente no puede adquirir otro contrato si ya tiene uno
	@Test
	public void Negativo_PresidenteAdquirirContrato_2() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaALosDetallesDelContratoComercialPrimero();
		this.pulseAñadirContrato();
		this.cuandoAccedaALosDetallesDelContratoComercialSegundo();
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.linkText("Añadir Contrato Publicitario"));
		});
		Assert.assertEquals("No puedes tener más de un contrato a la vez.", this.driver.findElement(By.xpath("//div/div/p")).getText());
	}

	@Test
	public void Negativo_PresidenteAdquirirContrato_3() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaALosDetallesDelContratoComercialPrimero();
		this.pulseAñadirContrato();
		this.logOut("presidente1");
		this.comoPresidente("presidente2");
		this.cuandoAccedaALosDetallesDelContratoComercialPrimero();
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.linkText("Añadir Contrato Publicitario"));
		});
		Assert.assertEquals("Este contrato publicitario ya esta comprado", this.driver.findElement(By.xpath("//div/div/p")).getText());
	}

	@Test
	public void Positivo_PresidenteTerminarContrato() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaALosDetallesDelContratoComercialPrimero();
		this.pulseAñadirContrato();
		Assert.assertEquals("150000000 €", this.driver.findElement(By.xpath("//tr[8]/td")).getText());
		this.pulseEliminarContrato();
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.xpath("//h2[2]")).getText();
		});
		Assert.assertNotEquals("150000000 €", this.driver.findElement(By.xpath("//tr[8]/td")).getText());
	}

	@Test
	public void Negativo_PresidenteTerminarContrato() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaALosDetallesDelContratoComercialSUPERCaro();
		this.pulseAñadirContrato();
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.linkText("Eliminar Contrato Publicitario")).getText();
		});
	}

	// HERRAMIENTAS
	private void comoPresidente(final String presidente) {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(presidente);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(presidente);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void logOut(final String presidente) {
		this.driver.findElement(By.linkText(presidente.toUpperCase())).click();
		this.driver.findElement(By.linkText("Desconectarse")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void pulseAñadirContrato() {
		this.driver.findElement(By.linkText("Añadir Contrato Publicitario")).click();
	}

	private void pulseEliminarContrato() {
		this.driver.findElement(By.linkText("Eliminar Contrato Publicitario")).click();
	}

	private void cuandoAccedaALosDetallesDelContratoComercialPrimero() {
		this.driver.findElement(By.linkText("CONTRATOS COMERCIALES")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[1]/td/a/b")).click();
	}

	private void cuandoAccedaALosDetallesDelContratoComercialSUPERCaro() {
		this.driver.findElement(By.linkText("CONTRATOS COMERCIALES")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[5]/td/a/b")).click();
	}

	private void cuandoAccedaALosDetallesDelContratoComercialSegundo() {
		this.driver.findElement(By.linkText("CONTRATOS COMERCIALES")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[2]/td/a/b")).click();
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
