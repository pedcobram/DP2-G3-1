
package org.springframework.samples.petclinic.UI;

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
public class MatchDateChangeRequestUITest {

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
	public void R5Positivo_PresidenteSolicitarCambioDeHorarioAUnPartido() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoHagaClickEnLaPestañaParaGestionarLosPartidosAunPorJugar();
		this.entoncesPodreSolicitarUnCambioDeHorarioDelPartido();
	}

	@Test
	public void R5Negativo_PresidenteSolicitarCambioDeHorarioAUnPartido() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoHagaClickEnUnPartidoAunPorJugarQueYaTengaUnaPeticionRealizada();
		this.entoncesSeMostraraUnErrorIndicandolo();
	}

	//

	private void comoPresidente(final String presidente) {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(presidente);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(presidente);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void cuandoHagaClickEnUnPartidoAunPorJugarQueYaTengaUnaPeticionRealizada() {
		this.driver.findElement(By.linkText("LISTA DE PARTIDOS")).click();
		this.driver.findElement(By.linkText("Solicitar cambio de fecha")).click();
		this.driver.findElement(By.id("title")).click();
		this.driver.findElement(By.id("title")).clear();
		this.driver.findElement(By.id("title")).sendKeys("Match title 0");
		this.driver.findElement(By.id("new_date")).click();
		this.driver.findElement(By.id("new_date")).click();
		this.driver.findElement(By.id("new_date")).clear();
		this.driver.findElement(By.id("new_date")).sendKeys("2023/05/11 20:30");
		this.driver.findElement(By.id("reason")).click();
		this.driver.findElement(By.id("reason")).clear();
		this.driver.findElement(By.id("reason")).sendKeys("testreason");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void cuandoHagaClickEnLaPestañaParaGestionarLosPartidosAunPorJugar() {
		this.driver.findElement(By.linkText("LISTA DE PARTIDOS")).click();
	}

	private void entoncesSeMostraraUnErrorIndicandolo() {
		this.driver.findElement(By.xpath("//form[@id='add-referee-form']/div/div/div")).click();
		Assert.assertEquals("There's already one request open or completed for this match", this.driver.findElement(By.xpath("//form[@id='add-referee-form']/div/div/div/span[2]")).getText());
	}

	private void entoncesPodreSolicitarUnCambioDeHorarioDelPartido() {
		this.driver.findElement(By.xpath("(//a[contains(text(),'Solicitar cambio de fecha')])[2]")).click();
		this.driver.findElement(By.id("title")).click();
		this.driver.findElement(By.id("title")).clear();
		this.driver.findElement(By.id("title")).sendKeys("Match title 1 - test");
		this.driver.findElement(By.id("new_date")).click();
		this.driver.findElement(By.id("new_date")).clear();
		this.driver.findElement(By.id("new_date")).sendKeys("2021/05/11 20:30");
		this.driver.findElement(By.id("reason")).click();
		this.driver.findElement(By.id("reason")).clear();
		this.driver.findElement(By.id("reason")).sendKeys("testreason");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("LISTA DE PETICIÓN DE CAMBIO DE HORARIO")).click();
		this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr[2]/td")).click();
		Assertions.assertEquals("Match title 1 - test", this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr[2]/td")).getText());
		this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr[2]/td[2]")).click();
		Assertions.assertEquals("2021-05-11 20:30:00.0", this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr[2]/td[2]")).getText());
		this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr[2]/td[3]")).click();
		Assertions.assertEquals("testreason", this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr[2]/td[3]")).getText());
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
