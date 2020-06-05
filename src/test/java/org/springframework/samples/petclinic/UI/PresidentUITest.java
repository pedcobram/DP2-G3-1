
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
import org.openqa.selenium.support.ui.Select;
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
public class PresidentUITest {

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

	@Test
	public void R2Positivo_PresidenteCreacionPartido() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaAlFormularioDeCreacionDePartidoAmistosoYLoComplete();
		this.entoncesSeCrearaUnaPeticionDePartidoAmistoso();
	}

	@Test
	public void R2Negativo_PresidenteCreacionPartido() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaAlFormularioDeCreacionDePartidoAmistosoEIntroduzcaUnaFechaMenorAUnMes();
		this.entoncesSeMostraraUnErrorIndicandoQueLaFechaNoPuedeSerMenorQueUnMes();
	}

	@Test
	public void R10Positivo_PresidenteAceptarPartido() throws Exception {
		this.setupR12();
		this.comoPresidente("presidente2");
		this.podraAceptarORechazarPartidosAmistosos();
	}

	@Test
	public void R10Negativo_PresidenteAceptarPartido() throws Exception {
		this.comoPresidente("presidente1");
		this.NoPodraAceptarInvitacionesAPartidosConMismaFechaQueOtroYaAceptado();
	}

	@Test
	public void R18Positivo_PresidenteVerEstadisticasActaActualizadas() throws Exception {
		this.cuandoUnArbitroCierreElActaDeUnPartido();
		this.comoPresidente("presidente1");
		this.sePodranVisualizarLasEstadisticasDeLosJugadores();
	}

	@Test
	public void R18Negativo_PresidenteVerEstadisticasActaActualizadas() throws Exception {
		this.comoPresidente("presidente1");
		this.siIntentoModificarUnActaDePartidoSeMeRedirigiraALaVistaDeError();
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

	// R2Positivo_PresidenteCreacionPartido

	private void cuandoAccedaAlFormularioDeCreacionDePartidoAmistosoYLoComplete() {
		this.driver.findElement(By.linkText("EQUIPOS")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[2]/td/a/b")).click();
		this.driver.findElement(By.linkText("Solicitar partido amistoso")).click();
		this.driver.findElement(By.id("matchDate")).click();
		this.driver.findElement(By.id("matchDate")).clear();
		this.driver.findElement(By.id("matchDate")).sendKeys("2021/04/12 22:24");
		new Select(this.driver.findElement(By.id("stadium"))).selectByVisibleText("Ramón Sánchez-Pizjuan");
		this.driver.findElement(By.xpath("//option[@value='Ramón Sánchez-Pizjuan']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeCrearaUnaPeticionDePartidoAmistoso() {
		this.driver.findElement(By.linkText("PRESIDENTE1")).click();
		this.driver.findElement(By.linkText("PETICIONES DE PARTIDO ENVIADAS")).click();
	}

	// R2Negativo_PresidenteCreacionPartido

	private void cuandoAccedaAlFormularioDeCreacionDePartidoAmistosoEIntroduzcaUnaFechaMenorAUnMes() {
		this.driver.findElement(By.linkText("EQUIPOS")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[2]/td/a/b")).click();
		this.driver.findElement(By.linkText("Solicitar partido amistoso")).click();
		new Select(this.driver.findElement(By.id("stadium"))).selectByVisibleText("Ramón Sánchez-Pizjuan");
		this.driver.findElement(By.xpath("//option[@value='Ramón Sánchez-Pizjuan']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnErrorIndicandoQueLaFechaNoPuedeSerMenorQueUnMes() {
		Assertions.assertEquals("Match date must be at least one month from now", this.driver.findElement(By.xpath("//form[@id='add-match-request-form']/div/div[3]/div/span[2]")).getText());
	}

	// R12Positivo_PresidenteAceptarPartido

	private void setupR12() {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		//this.driver.findElement(By.linkText("CONECTARSE")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("EQUIPOS")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[2]/td/a/b")).click();
		this.driver.findElement(By.linkText("Solicitar partido amistoso")).click();
		this.driver.findElement(By.id("matchDate")).click();
		this.driver.findElement(By.id("matchDate")).clear();
		this.driver.findElement(By.id("matchDate")).sendKeys("2025/04/13 21:41");
		new Select(this.driver.findElement(By.id("stadium"))).selectByVisibleText("Ramón Sánchez-Pizjuan");
		this.driver.findElement(By.xpath("//option[@value='Ramón Sánchez-Pizjuan']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.get("http://www.localhost:" + this.port + "/logout");
		//this.driver.findElement(By.linkText("presidente1")).click();
		//this.driver.findElement(By.linkText("Desconectarse")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void podraAceptarORechazarPartidosAmistosos() {
		this.driver.get("http://www.localhost:" + this.port + "/matchRequests/received");
		//this.driver.findElement(By.linkText("presidente2")).click();
		//this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li[2]/ul/li[4]/a/span")).click();
		this.driver.findElement(By.linkText("Aceptar")).click();
		this.driver.get("http://www.localhost:" + this.port + "/matches/list");
		//this.driver.findElement(By.linkText("Lista de partidos")).click();
		//this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr/td")).click();
		Assert.assertEquals("Sevilla Fútbol Club vs Real Madrid Club de Fútbol", this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr/td")).getText());
		this.driver.get("http://www.localhost:" + this.port + "/matchRequests/received");
		//this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li[2]/a/strong")).click();
		//this.driver.findElement(By.linkText("Peticiones de partido recibidas")).click();
		//this.driver.findElement(By.xpath("//table[@id='matchRequestsTable']/tbody/tr/td")).click();
		Assert.assertEquals("Sevilla Fútbol Club vs Real Madrid Club de Fútbol", this.driver.findElement(By.xpath("//table[@id='matchRequestsTable']/tbody/tr/td")).getText());
	}

	// R12Negativo_PresidenteAceptarPartido

	private void NoPodraAceptarInvitacionesAPartidosConMismaFechaQueOtroYaAceptado() {
		this.driver.findElement(By.linkText("PRESIDENTE1")).click();
		this.driver.findElement(By.linkText("PRESIDENTE1")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li[2]/a/strong")).click();
		this.driver.findElement(By.linkText("PETICIONES DE PARTIDO RECIBIDAS")).click();
		this.driver.findElement(By.xpath("(//a[contains(text(),'Aceptar')])[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='matchRequestsTable']/tbody/tr[2]/td[3]")).click();
		Assert.assertEquals("REFUSE", this.driver.findElement(By.xpath("//table[@id='matchRequestsTable']/tbody/tr[2]/td[3]")).getText());
	}

	// R21Positivo_PresidenteVerEstadisticasActaActualizadas

	private void cuandoUnArbitroCierreElActaDeUnPartido() {
		this.driver.get("http://localhost:" + this.port);
		this.driver.get("http://localhost:" + this.port + "/login");
		//driver.findElement(By.linkText("Conectarse")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("referee1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("referee1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("LISTA DE PETICIONES DE ÁRBITRO")).click();
		this.driver.findElement(By.linkText("Aceptar")).click();
		this.driver.findElement(By.linkText("REFEREE1")).click();
		this.driver.findElement(By.linkText("MI LISTA DE PARTIDOS")).click();
		this.driver.get("http://localhost:" + this.port + "/matches/matchRecord/3/view");
		//this.driver.findElement(By.xpath("//table[@id='matchesTable']/tbody/tr[2]/td[8]")).click();
		//this.driver.findElement(By.xpath("(//a[contains(text(),'Acta del partido')])[2]")).click();
		this.driver.findElement(By.linkText("+1")).click();
		this.driver.findElement(By.linkText("+1")).click();
		this.driver.findElement(By.xpath("(//a[contains(text(),'+1')])[2]")).click();
		this.driver.findElement(By.linkText("Editar")).click();
		this.driver.findElement(By.id("season_start")).click();
		this.driver.findElement(By.id("season_start")).clear();
		this.driver.findElement(By.id("season_start")).sendKeys("2019");
		this.driver.findElement(By.id("result")).click();
		this.driver.findElement(By.id("result")).clear();
		this.driver.findElement(By.id("result")).sendKeys("Match 3 result");
		new Select(this.driver.findElement(By.id("status"))).selectByVisibleText("PUBLISHED");
		this.driver.findElement(By.xpath("//option[@value='PUBLISHED']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("REFEREE1")).click();
		this.driver.findElement(By.linkText("Desconectarse")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void sePodranVisualizarLasEstadisticasDeLosJugadores() {
		this.driver.findElement(By.linkText("EQUIPOS")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td/a/b")).click();
		this.driver.findElement(By.linkText("Lista de Jugadores")).click();
		this.driver.findElement(By.linkText("Tomas Vaclik")).click();
		this.driver.findElement(By.linkText("Estadísticas")).click();
		this.driver.findElement(By.xpath("//tr[2]/th")).click();
		Assert.assertEquals("Asistencias", this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		this.driver.findElement(By.xpath("//tr[2]/td/b")).click();
		Assert.assertEquals("1", this.driver.findElement(By.xpath("//tr[2]/td/b")).getText());
		this.driver.findElement(By.xpath("//tr[3]/th")).click();
		Assert.assertEquals("Goles", this.driver.findElement(By.xpath("//tr[3]/th")).getText());
		this.driver.findElement(By.xpath("//tr[3]/td")).click();
		Assert.assertEquals("2", this.driver.findElement(By.xpath("//tr[3]/td")).getText());
	}

	private void siIntentoModificarUnActaDePartidoSeMeRedirigiraALaVistaDeError() {
		this.driver.findElement(By.linkText("LISTA DE PARTIDOS")).click();
		this.driver.get("http://localhost:" + this.port + "/matches/matchRecord/2/view");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Acceso Prohibido!", this.driver.findElement(By.xpath("//h2")).getText());
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
