
package org.springframework.samples.petclinic.UI.rafa;

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
public class PresidentUITestRafa {

	private WebDriver		driver;
	@SuppressWarnings("unused")
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int				port;


	@BeforeEach
	public void setUp() throws Exception {

		String pathToGeckoDriver = "C:\\Users\\arkke\\Downloads";
		System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");

		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void R10Positivo_PresidenteRegistrarClub() throws Exception {
		this.comoPresidente("rafa");
		this.cuandoAccedaAlFormularioDeRegistrarClubYLoComplete();
		this.entoncesSeHabraRegistradoUnClub();
	}

	@Test
	public void R10Positivo_PresidenteEditarClub() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeEditarClubYLoCambie();
		this.entoncesSeHabraEditadoUnClub();
	}

	@Test
	public void R10Positivo_PresidentePublicarClub() throws Exception {
		this.comoPresidente("owner7");
		this.cuandoAccedaAlFormularioDeEditarClubYLoPublique();
		this.entoncesSeHabraPublicadoUnClub();
	}

	@Test
	public void R10Negativo_PresidenteRegistrarClub_CampoEnBlanco() throws Exception {
		this.comoPresidente("rafa");
		this.cuandoAccedaAlFormularioDeRegistrarClubYDejeAlgunCampoLibre();
		this.entoncesSeMostraraUnErrorIndicandoQueHayQueIntroducirElCampo();
	}

	@Test
	public void R10Negativo_PresidenteRegistrarClub_FechaFutura() throws Exception {
		this.comoPresidente("rafa");
		this.cuandoAccedaAlFormularioDeRegistrarClubYPongaFechaFutura();
		this.entoncesSeMostraraUnErrorIndicandoQueLaFechaNoEsValida();
	}

	@Test
	public void R10Negativo_PresidenteRegistrarClub_OtroClub() throws Exception {
		//this.comoPresidente("presidente1");
		this.seMostraraUnMensajeDeErrorSiIntentoCrearOtroTeniendoUnClub();
	}

	@Test
	public void R10Negativo_PresidenteEditarClub_CampoEnBlanco() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeEditarClubYDejeAlgunCampoLibre();
		this.entoncesSeMostraraUnErrorIndicandoQueHayQueIntroducirElCampo();
	}

	@Test
	public void R10Negativo_PresidentePublicarClub_SinCumplirRequisitos() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeEditarClubEIntentePublicarloSinCumplirRequisitos();
		this.entoncesSeMostraraUnErrorIndicandoQueHayQueNoCumploRequisitos();
	}

	//R10 Positivo - Registrar Club

	private void comoPresidente(final String presidente) {
		this.driver.get("www.localhost:" + this.port + "/?lang=es");
		this.driver.get("www.localhost:" + this.port + "/login");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(presidente);
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(presidente);
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void cuandoAccedaAlFormularioDeRegistrarClubYLoComplete() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("CREA UN CLUB DE FÚTBOL")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Alberta SC");
		this.driver.findElement(By.id("stadium")).click();
		this.driver.findElement(By.id("stadium")).clear();
		this.driver.findElement(By.id("stadium")).sendKeys("Alberta Stadium");
		this.driver.findElement(By.id("city")).click();
		this.driver.findElement(By.id("city")).clear();
		this.driver.findElement(By.id("city")).sendKeys("Alberta City");
		this.driver.findElement(By.id("foundationDate")).click();
		this.driver.findElement(By.linkText("15")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraRegistradoUnClub() {
		Assert.assertEquals("Alberta SC", this.driver.findElement(By.xpath("//td")).getText());
		Assert.assertEquals("Alberta City", this.driver.findElement(By.xpath("//tr[2]/td")).getText());
		Assert.assertEquals("Alberta Stadium", this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		Assert.assertEquals("2020-04-15", this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		Assert.assertEquals("Rafael Liébana Fuentes", this.driver.findElement(By.xpath("//tr[7]/td")).getText());
		Assert.assertEquals("100000000 €", this.driver.findElement(By.xpath("//tr[8]/td")).getText());
	}

	//R10 Positivo - Editar Club

	private void cuandoAccedaAlFormularioDeEditarClubYLoCambie() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("Actualizar Club")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Besaid Aurochs Edit");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraEditadoUnClub() {
		Assert.assertEquals("Besaid Aurochs Edit", this.driver.findElement(By.xpath("//b")).getText());
	}

	//R10 Positivo - Publicar Club

	private void cuandoAccedaAlFormularioDeEditarClubYLoPublique() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("Actualizar Club")).click();
		new Select(this.driver.findElement(By.id("status"))).selectByVisibleText("true");
		this.driver.findElement(By.xpath("//option[@value='true']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraPublicadoUnClub() {
		this.driver.findElement(By.linkText("EQUIPOS")).click();
		Assert.assertEquals("Chelsea Football Club", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr[9]/td/a/b")).getText());
	}

	//R10 Negativo - Campo en Blanco

	private void cuandoAccedaAlFormularioDeRegistrarClubYDejeAlgunCampoLibre() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("CREA UN CLUB DE FÚTBOL")).click();
		//		this.driver.findElement(By.id("name")).click();
		//		this.driver.findElement(By.id("name")).clear();
		//		this.driver.findElement(By.id("name")).sendKeys("Alberta SC");
		this.driver.findElement(By.id("stadium")).click();
		this.driver.findElement(By.id("stadium")).clear();
		this.driver.findElement(By.id("stadium")).sendKeys("Alberta Stadium");
		this.driver.findElement(By.id("city")).click();
		this.driver.findElement(By.id("city")).clear();
		this.driver.findElement(By.id("city")).sendKeys("Alberta City");
		this.driver.findElement(By.id("foundationDate")).click();
		this.driver.findElement(By.linkText("15")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnErrorIndicandoQueHayQueIntroducirElCampo() {
		Assert.assertEquals("No puede estar en blanco y la longitud debe estar entre 3 y 50 caracteres.", this.driver.findElement(By.xpath("//form[@id='add-footballClub-form']/div/div/div/span[2]")).getText());
	}

	//R10 Negativo - Fecha Futura

	private void cuandoAccedaAlFormularioDeRegistrarClubYPongaFechaFutura() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("CREA UN CLUB DE FÚTBOL")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Alberta SC");
		this.driver.findElement(By.id("stadium")).click();
		this.driver.findElement(By.id("stadium")).clear();
		this.driver.findElement(By.id("stadium")).sendKeys("Alberta Stadium");
		this.driver.findElement(By.id("city")).click();
		this.driver.findElement(By.id("city")).clear();
		this.driver.findElement(By.id("city")).sendKeys("Alberta City");
		this.driver.findElement(By.id("foundationDate")).click();
		this.driver.findElement(By.linkText("30")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnErrorIndicandoQueLaFechaNoEsValida() {
		Assert.assertEquals("Debe ser una fecha anterior a la actual.", this.driver.findElement(By.xpath("//form[@id='add-footballClub-form']/div/div[5]/div/span[2]")).getText());
	}

	//R10 Negativo - CrearOtroClub

	private void seMostraraUnMensajeDeErrorSiIntentoCrearOtroTeniendoUnClub() {
		this.driver.get("http://localhost:8080/footballClubs/myClub/new");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Acceso Prohibido!", this.driver.findElement(By.xpath("//h2")).getText());
	}

	//R10 Negativo - Editar Club - Campo en Blanco

	private void cuandoAccedaAlFormularioDeEditarClubYDejeAlgunCampoLibre() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("Actualizar Club")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	//R10 Negativo - Publicar Club - Sin Cumplir Requisitos

	private void cuandoAccedaAlFormularioDeEditarClubEIntentePublicarloSinCumplirRequisitos() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("Actualizar Club")).click();
		new Select(this.driver.findElement(By.id("status"))).selectByVisibleText("true");
		this.driver.findElement(By.xpath("//option[@value='true']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnErrorIndicandoQueHayQueNoCumploRequisitos() {
		Assert.assertEquals("Para poder publicar tu club debes tener al menos cinco jugadores y un entrenador.", this.driver.findElement(By.xpath("//form[@id='add-footballClub-form']/div/div[7]/div/div/span[2]")).getText());
	}

}
