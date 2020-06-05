
package org.springframework.samples.petclinic.UI;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
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
public class CompetitionAdminUITestRafa {

	private WebDriver		driver;
	@SuppressWarnings("unused")
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int				port;


	@BeforeEach
	public void setUp() throws Exception {

		//String pathToGeckoDriver = "C:\\Users\\arkke\\Downloads";
		//System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");

		String path = System.getenv("webdriver.chrome.driver");
		System.setProperty("webdriver.chrome.driver", path);

		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	//R6 -------------------------------------------------------------------------------

	@Test
	public void R6Positivo_AdminCompRegistrarCompeticion() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoAccedaAlFormularioDeRegistrarCompeticionYLoRellene();
		this.entoncesSeHabraCreadoCorrectamente();
	}

	@Test
	public void R6Positivo_AdminCompEditarCompeticion() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoAccedaAlFormularioDeEditarCompeticionYLoRellene();
		this.entoncesSeHabraEditadoCorrectamente();
	}

	@Test
	public void R6Positivo_AdminCompPublicarCompeticion() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoAccedaAlLaVistaDeMiCompeticionYLaPublique();
		this.entoncesSeHabraPublicadoCorrectamente();
	}

	@Test
	public void R6Positivo_AdminCompAñadirEquipoACompeticion() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoAccedaAlLaVistaDeAñadirEquiposYloAñada();
		this.entoncesSeHabraAñadidoCorrectamente();
	}

	@Test
	public void R6Positivo_AdminCompBorrarEquipoACompeticion() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoAccedaAlLaVistaDeBorrarEquiposYloAñada();
		this.entoncesSeHabraBorradoCorrectamente();
	}

	@Test
	public void R6Negativo_AdminCompRegistrarCompeticion_RecompensaMinima() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoAccedaAlFormularioDeRegistrarCompeticionYLoRelleneIncorrectamente();
		this.entoncesSeMostraraUnMensajeDeErrorIndicandolo();
	}

	@Test
	public void R6Negativo_AdminCompAñadirEquipoACompeticion_ConCompPublica() throws Exception {
		//this.comoAdministradorDeCompetición("pedro");
		this.seMostraraUnMensajeDeErrorSiIntentoAñadir();
	}

	@Test
	public void R6Negativo_AdminCompPublicarCompeticion_SinEquiposMinimos() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.yNoTengaLosEquiposMinimos();
		this.cuandoAccedaAlLaVistaDeMiCompeticionYLaPublique();
		this.entoncesSeMostraraUnMensajeIndicandolo();
	}

	//R7 -------------------------------------------------------------------------------

	@Test
	public void R7Positivo_AdminCompVerEstadisticasCompeticion() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoAccedaALasEstadisticas();
		this.loVisualizareCorrectamente();
	}

	//R17 ------------------------------------------------------------------------------

	@Test
	public void R17Positivo_AdminCompBorrarCompeticion() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoIntenteBorrarMiCompeticion();
		this.entoncesSeHabraBorradoCorrectamenteYEstaremosEnElInicio();
	}

	@Test
	public void R17Negativo_AdminCompBorrarCompeticion_PartidoDisputado() throws Exception {
		this.comoAdministradorDeCompetición("pedro");
		this.cuandoIntenteBorrarMiCompeticiónConPartidoDisputado();
		this.entoncesSeMostraraUnMensajeDeError();
	}

	//==================================================================================

	private void comoAdministradorDeCompetición(final String user) {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(user);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(user);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	//R7

	private void cuandoAccedaALasEstadisticas() {
		this.driver.findElement(By.linkText("TORNEOS")).click();
		this.driver.findElement(By.linkText("La Liga")).click();
		this.driver.findElement(By.linkText("Estadísticas")).click();
	}

	private void loVisualizareCorrectamente() {
		Assert.assertEquals("Estadísticas", this.driver.findElement(By.xpath("//h2")).getText());
	}

	//R17

	private void cuandoIntenteBorrarMiCompeticion() {
		this.driver.findElement(By.linkText("MIS COMPETICIONES")).click();
		this.driver.findElement(By.linkText("Premier League")).click();
		this.driver.findElement(By.linkText("Borrar Competición")).click();
	}

	private void cuandoIntenteBorrarMiCompeticiónConPartidoDisputado() {
		this.driver.findElement(By.linkText("MIS COMPETICIONES")).click();
		this.driver.findElement(By.linkText("La Liga")).click();
		this.driver.findElement(By.linkText("Borrar Competición")).click();
	}

	private void entoncesSeHabraBorradoCorrectamenteYEstaremosEnElInicio() {
		Assert.assertEquals("Bienvenido.", this.driver.findElement(By.xpath("//h2")).getText());
	}

	private void entoncesSeMostraraUnMensajeDeError() {
		Assert.assertEquals("No puedes borrar la competición, ya se ha disputado al menos un partido", this.driver.findElement(By.xpath("//div/div/p")).getText());
	}

	//R6

	private void cuandoAccedaAlFormularioDeRegistrarCompeticionYLoRellene() {
		this.driver.findElement(By.linkText("CREAR COMPETICIÓN")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Liga de Prueba");
		this.driver.findElement(By.id("description")).click();
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("Descripcion");
		this.driver.findElement(By.id("reward")).click();
		this.driver.findElement(By.id("reward")).clear();
		this.driver.findElement(By.id("reward")).sendKeys("5000000");
		new Select(this.driver.findElement(By.id("type"))).selectByVisibleText("LEAGUE");
		this.driver.findElement(By.xpath("//option[@value='LEAGUE']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraCreadoCorrectamente() {
		Assert.assertEquals("Liga de Prueba", this.driver.findElement(By.xpath("//b")).getText());
		Assert.assertEquals("pedro", this.driver.findElement(By.xpath("//tr[5]/td")).getText());
	}

	private void cuandoAccedaAlFormularioDeEditarCompeticionYLoRellene() {
		this.driver.findElement(By.linkText("MIS COMPETICIONES")).click();
		this.driver.findElement(By.linkText("Premier League")).click();
		this.driver.findElement(By.linkText("Actualizar")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Premier League Edit");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraEditadoCorrectamente() {
		Assert.assertEquals("Premier League Edit", this.driver.findElement(By.xpath("//b")).getText());
	}

	private void cuandoAccedaAlLaVistaDeMiCompeticionYLaPublique() {
		this.driver.findElement(By.linkText("MIS COMPETICIONES")).click();
		this.driver.findElement(By.linkText("Premier League")).click();
		this.driver.findElement(By.linkText("PUBLICAR COMPETICIÓN Y GENERAR CALENDARIO")).click();
	}

	private void entoncesSeHabraPublicadoCorrectamente() {
		Assert.assertEquals("Calendario", this.driver.findElement(By.linkText("Calendario")).getText());
	}

	private void cuandoAccedaAlLaVistaDeAñadirEquiposYloAñada() {
		this.driver.findElement(By.linkText("MIS COMPETICIONES")).click();
		this.driver.findElement(By.linkText("Premier League")).click();
		Assert.assertEquals("4", this.driver.findElement(By.linkText("4")).getText());
		this.driver.findElement(By.linkText("Añadir Equipo")).click();
		this.driver.findElement(By.xpath("//option[@value='Real Madrid Club de Fútbol']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraAñadidoCorrectamente() {
		Assert.assertEquals("5", this.driver.findElement(By.linkText("5")).getText());
		this.driver.findElement(By.linkText("5")).click();
		this.driver.findElement(By.xpath("//option[@value='Real Madrid Club de Fútbol']")).click();
		Assert.assertEquals("Real Madrid Club de Fútbol", this.driver.findElement(By.xpath("//option[@value='Real Madrid Club de Fútbol']")).getText());
	}

	private void cuandoAccedaAlLaVistaDeBorrarEquiposYloAñada() {
		this.driver.findElement(By.linkText("MIS COMPETICIONES")).click();
		this.driver.findElement(By.linkText("Premier League")).click();
		Assert.assertEquals("4", this.driver.findElement(By.linkText("4")).getText());
		this.driver.findElement(By.linkText("Gestionar Equipos")).click();
		this.driver.findElement(By.xpath("//option[@value='Sevilla Fútbol Club']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraBorradoCorrectamente() {
		Assert.assertEquals("3", this.driver.findElement(By.linkText("3")).getText());
	}

	private void cuandoAccedaAlFormularioDeRegistrarCompeticionYLoRelleneIncorrectamente() {
		this.driver.findElement(By.linkText("CREAR COMPETICIÓN")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Hola");
		this.driver.findElement(By.id("description")).click();
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("Hola");
		this.driver.findElement(By.id("reward")).click();
		this.driver.findElement(By.id("reward")).clear();
		this.driver.findElement(By.id("reward")).sendKeys("5000");
		new Select(this.driver.findElement(By.id("type"))).selectByVisibleText("LEAGUE");
		this.driver.findElement(By.xpath("//option[@value='LEAGUE']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnMensajeDeErrorIndicandolo() {
		Assert.assertEquals("Recompensa mínima: 5.000.000 €", this.driver.findElement(By.xpath("//form[@id='add-competition-form']/div/div[3]/div/span[2]")).getText());
	}

	private void seMostraraUnMensajeDeErrorSiIntentoAñadir() {
		this.driver.get("http://localhost:8080/competition/2/addClubs");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("pedro");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("pedro");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Acceso Prohibido!", this.driver.findElement(By.xpath("//h2")).getText());
	}

	private void yNoTengaLosEquiposMinimos() {
		this.driver.findElement(By.linkText("MIS COMPETICIONES")).click();
		this.driver.findElement(By.linkText("Premier League")).click();
		this.driver.findElement(By.linkText("4")).click();
		this.driver.findElement(By.xpath("//option[@value='Manchester City Football Club']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnMensajeIndicandolo() {
		Assert.assertEquals("En el formato liga deben haber mínimo 4 equipos y deben ser pares (4, 6, 8, 10...)", this.driver.findElement(By.xpath("//div/div/p")).getText());
	}

}
