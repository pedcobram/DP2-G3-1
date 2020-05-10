
package org.springframework.samples.petclinic.UI.rafa;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
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

	//R3 ------------------------------------------------------------------------------

	@Test
	public void R3Positivo_PresidenteFicharAgenteLibre() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaAlFormularioDeFicharAgentesLibres();
		this.entoncesSeHabraFichadoAlJugador();
	}

	@Test
	public void R3Positivo_PresidenteDespedirJugador() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaAlContratoParaDespedirAUnJugador();
		this.entoncesSeHabraDespedidoYestaremosEnlaVentanaInicial();
	}

	@Test
	public void R3Negativo_PresidenteFicharAgenteLibre_DineroInsuficiente() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeFicharAgentesLibresYNoTengaSuficienteDinero();
		this.entoncesSeMostraraUnMensajeDeErrorIndicandoQueNoTengoDinero();
	}

	@Test
	public void R3Negativo_PresidenteDespedirJugador_DineroInsuficiente() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaAlContratoParaDespedirAUnJugadorSinDinero();
		this.entoncesSeMostraraUnMensajeDeErrorIndicandoQueNoPuedo();
	}

	//R4 -------------------------------------------------------------------------------------------

	@Test
	public void R4Positivo_PresidenteFicharEntrenador() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeFicharEntrenadoresAgentesLibres();
		this.entoncesSeHabraFichadoAlEntrenador();
	}

	@Test
	public void R4Positivo_PresidenteDespedirEntrenador() throws Exception {
		this.comoPresidente("presidente1");
		this.cuandoAccedaALaVistaDeMiEntrenadorEIntenteDespedirlo();
		this.entoncesSeHabraDespedidoAlEntrenadorYEstaremosEnLaPaginaDelClub();
	}

	@Test //HACER
	public void R4Negativo_PresidenteFicharEntrenador_DineroInsuficiente() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeFicharEntrenadoresAgentesLibres();
		this.eIntenteFicharAUnEntrenadorSinDineroSuficiente();
	}

	@Test
	public void R4Negativo_PresidenteDespedirEntrenador_DineroInsuficiente() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoIntenteDespedirAMiEntrenadorSinDinero();
		this.entoncesSeMostraraUnMensajeDeErrorIndicandoQueNoPuedo();
	}

	//R8 -------------------------------------------------------------------------------------------

	@Test
	public void R8Positivo_PresidenteRegistrarClub() throws Exception {
		this.comoPresidente("rafa");
		this.cuandoAccedaAlFormularioDeRegistrarClubYLoComplete();
		this.entoncesSeHabraRegistradoUnClub();
	}

	@Test
	public void R8Positivo_PresidenteEditarClub() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeEditarClubYLoCambie();
		this.entoncesSeHabraEditadoUnClub();
	}

	@Test
	public void R8Positivo_PresidentePublicarClub() throws Exception {
		this.comoPresidente("owner7");
		this.cuandoAccedaAlFormularioDeEditarClubYLoPublique();
		this.entoncesSeHabraPublicadoUnClub();
	}

	@Test
	public void R8Negativo_PresidenteRegistrarClub_CampoEnBlanco() throws Exception {
		this.comoPresidente("rafa");
		this.cuandoAccedaAlFormularioDeRegistrarClubYDejeAlgunCampoLibre();
		this.entoncesSeMostraraUnErrorIndicandoQueHayQueIntroducirElCampo();
	}

	@Test
	public void R8Negativo_PresidenteRegistrarClub_FechaFutura() throws Exception {
		this.comoPresidente("rafa");
		this.cuandoAccedaAlFormularioDeRegistrarClubYPongaFechaFutura();
		this.entoncesSeMostraraUnErrorIndicandoQueLaFechaNoEsValida();
	}

	@Test
	public void R8Negativo_PresidenteRegistrarClub_OtroClub() throws Exception {
		//this.comoPresidente("presidente1");
		this.seMostraraUnMensajeDeErrorSiIntentoCrearOtroTeniendoUnClub();
	}

	@Test
	public void R8Negativo_PresidenteEditarClub_CampoEnBlanco() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeEditarClubYDejeAlgunCampoLibre();
		this.entoncesSeMostraraUnErrorIndicandoQueHayQueIntroducirElCampo();
	}

	@Test
	public void R8Negativo_PresidentePublicarClub_SinCumplirRequisitos() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeEditarClubEIntentePublicarloSinCumplirRequisitos();
		this.entoncesSeMostraraUnErrorIndicandoQueHayQueNoCumploRequisitos();
	}

	//R14 -------------------------------------------------------------------------------------------

	@Test
	public void R14Positivo_PresidenteRegistrarJugador() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeRegistrarJugador();
		this.entoncesSeHabraRegistradoUnJugadorCuandoLoRelleneCorrectamente();
	}

	@Test
	public void R14Positivo_PresidenteRegistrarEntrenador() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaAlFormularioDeRegistrarEntrenadorYLoRellene();
		this.entoncesSeHabraRegistradoUnEntrenadorCorrectamente();

	}

	@Test
	public void R14Negativo_PresidenteRegistrarJugador_ClubPublico() throws Exception {
		//	this.comoPresidente("presidente1");
		this.seMostraraUnMensajeDeErrorDeProhibidoSiIntentoRegistrar();
	}

	@Test
	public void R14Negativo_PresidenteRegistrarEntrenador_ClubPublico() throws Exception {
		//	this.comoPresidente("presidente1");
		this.seMostraraUnMensajeDeErrorDeProhibidoSiIntentoRegistrarEntrenador();
	}

	@Test
	public void R14Negativo_PresidenteRegistrarJugador_Con7Jugadores() throws Exception {
		this.comoPresidente("owner7");
		this.cuandoAccedaAlFormularioDeRegistrarJugador();
		this.entoncesSeMostraraUnMensajeDeErrorCuandoLoRelleneCorrectamente();
	}

	@Test
	public void R14Negativo_PresidenteRegistrarEntrenador_ConOtro() throws Exception {
		this.comoPresidente("owner7");
		this.cuandoAccedaAlFormularioDeRegistrarEntrenadorYLoRellene();
		this.entoncesSeMostraraUnMensajeDeError();
	}

	//R15 -------------------------------------------------------------------------------------------

	@Test
	public void R15Positivo_PresidenteBorrarEquipo() throws Exception {
		this.comoPresidente("owner8");
		this.cuandoAccedaALaVistaEintenteBorrarElClub();
		this.seBorraraCorrectamente();
	}

	@Test
	public void R15Negativo_PresidenteBorrarEquipo_ClubPublico() throws Exception {
		//	this.comoPresidente("presidente1");
		this.seMostraraUnMensajeDeErrorDeProhibidoSiIntentoBorrarMiClub();
	}

	//===============================================================================================

	//R8 Positivo - Registrar Club

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
		this.driver.findElement(By.linkText("1")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraRegistradoUnClub() {
		Assert.assertEquals("Alberta SC", this.driver.findElement(By.xpath("//td")).getText());
		Assert.assertEquals("Alberta City", this.driver.findElement(By.xpath("//tr[2]/td")).getText());
		Assert.assertEquals("Alberta Stadium", this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		Assert.assertEquals("2020-05-01", this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		Assert.assertEquals("Rafael Liébana Fuentes", this.driver.findElement(By.xpath("//tr[7]/td")).getText());
		Assert.assertEquals("100000000 €", this.driver.findElement(By.xpath("//tr[8]/td")).getText());
	}

	//R8 Positivo - Editar Club

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

	//R8 Positivo - Publicar Club

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

	//R8 Negativo - Campo en Blanco

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

	//R8 Negativo - Fecha Futura

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

	//R8 Negativo - CrearOtroClub

	private void seMostraraUnMensajeDeErrorSiIntentoCrearOtroTeniendoUnClub() {
		this.driver.get("http://localhost:8080/footballClubs/myClub/new");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Acceso Prohibido!", this.driver.findElement(By.xpath("//h2")).getText());
	}

	//R8 Negativo - Editar Club - Campo en Blanco

	private void cuandoAccedaAlFormularioDeEditarClubYDejeAlgunCampoLibre() {
		this.driver.findElement(By.linkText("MI CLUB")).click();
		this.driver.findElement(By.linkText("Actualizar Club")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	//R8 Negativo - Publicar Club - Sin Cumplir Requisitos

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

	//R3 Positivo - Fichar Jugadores Agentes Libres

	private void cuandoAccedaAlFormularioDeFicharAgentesLibres() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='JUGADORES - AGENTES LIBRES']")).click();
		this.driver.findElement(By.linkText("Jorge Salcedo")).click();
		this.driver.findElement(By.linkText("Contratar")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("1000000");
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.linkText("12")).click();
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.id("endDate")).clear();
		this.driver.findElement(By.id("endDate")).sendKeys("2022/05/30");
		this.driver.findElement(By.xpath("//form[@id='add-contractPlayer-form']/div/div[7]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraFichadoAlJugador() {
		Assert.assertEquals("Jorge Salcedo", this.driver.findElement(By.xpath("//b")).getText());
		Assert.assertEquals("Sevilla Fútbol Club", this.driver.findElement(By.xpath("//tr[2]/td")).getText());
	}

	//R3 Positivo - Despedir Jugadores

	private void cuandoAccedaAlContratoParaDespedirAUnJugador() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		this.driver.findElement(By.linkText("MIS CONTRATOS DE JUGADORES")).click();
		this.driver.findElement(By.linkText("Tomas Vaclik")).click();
		this.acceptNextAlert = true;
		this.driver.findElement(By.linkText("Despedir")).click();
		Assert.assertTrue(this.closeAlertAndGetItsText().matches("^¡ESTÁS A PUNTO DE DESPEDIR A ÉSTE JUGADOR! ¿ESTÁS SEGURO[\\s\\S] $"));
	}

	private void entoncesSeHabraDespedidoYestaremosEnlaVentanaInicial() {
		Assert.assertEquals("MI CLUB DE FÚTBOL", this.driver.findElement(By.xpath("//h2")).getText());
	}

	//R3 Negativo - Fichar Jugadores sin Dinero

	private void cuandoAccedaAlFormularioDeFicharAgentesLibresYNoTengaSuficienteDinero() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='JUGADORES - AGENTES LIBRES']")).click();
		this.driver.findElement(By.linkText("Sergio Molina")).click();
		this.driver.findElement(By.linkText("Contratar")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("9999999");
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.linkText("14")).click();
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.id("endDate")).clear();
		this.driver.findElement(By.id("endDate")).sendKeys("2024/05/14");
		this.driver.findElement(By.xpath("//form[@id='add-contractPlayer-form']/div")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeMostraraUnMensajeDeErrorIndicandoQueNoTengoDinero() {
		Assert.assertEquals("¡Los fondos de tu Club no son suficientes para pagar el salario!", this.driver.findElement(By.xpath("//form[@id='add-contractPlayer-form']/div/div[2]/div/span[2]")).getText());
	}

	//R3 Negativo - Despedir Jugador sin dinero para ello

	private void cuandoAccedaAlContratoParaDespedirAUnJugadorSinDinero() {
		this.driver.findElement(By.linkText("MIS CONTRATOS DE JUGADORES")).click();
		this.driver.findElement(By.linkText("Lucas Ocampos")).click();
		this.acceptNextAlert = true;
		this.driver.findElement(By.linkText("Despedir")).click();
		Assert.assertTrue(this.closeAlertAndGetItsText().matches("^¡ESTÁS A PUNTO DE DESPEDIR A ÉSTE JUGADOR! ¿ESTÁS SEGURO[\\s\\S] $"));
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='JUGADORES - AGENTES LIBRES']")).click();
		this.driver.findElement(By.linkText("Sergio Molina")).click();
		this.driver.findElement(By.linkText("Contratar")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("99999999");
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.linkText("12")).click();
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.id("endDate")).clear();
		this.driver.findElement(By.id("endDate")).sendKeys("2026/05/12");
		this.driver.findElement(By.xpath("//form[@id='add-contractPlayer-form']/div")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.acceptNextAlert = true;
		this.driver.findElement(By.linkText("Despedir")).click();
		Assert.assertTrue(this.closeAlertAndGetItsText().matches("^¡ESTÁS A PUNTO DE DESPEDIR A ÉSTE JUGADOR! ¿ESTÁS SEGURO[\\s\\S] $"));
	}

	private void entoncesSeMostraraUnMensajeDeErrorIndicandoQueNoPuedo() {
		Assert.assertEquals("¡Los fondos de tu Club no son suficientes para despedirlo!", this.driver.findElement(By.xpath("//div/div/p")).getText());

	}

	//RN 4

	private void cuandoAccedaAlFormularioDeFicharEntrenadoresAgentesLibres() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='ENTRENADORES - A. LIBRES']")).click();
	}

	private void entoncesSeHabraFichadoAlEntrenador() {
		this.driver.findElement(By.linkText("Marcelino Garcia Toral")).click();
		this.driver.findElement(By.linkText("Contratar")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("1000000");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Marcelino Garcia Toral", this.driver.findElement(By.xpath("//a/b")).getText());

	}

	private void cuandoIntenteDespedirAMiEntrenadorSinDinero() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='ENTRENADORES - A. LIBRES']")).click();
		this.driver.findElement(By.linkText("Marcelino Garcia Toral")).click();
		this.driver.findElement(By.linkText("Contratar")).click();
		this.driver.findElement(By.xpath("//form[@id='add-coach-form']/div/div[4]/div")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("1000000");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a/b")).click();
		this.acceptNextAlert = true;
		this.driver.findElement(By.linkText("Despedir")).click();
		Assert.assertTrue(this.closeAlertAndGetItsText().matches("^¡ESTÁS A PUNTO DE DESPEDIR A ÉSTE JUGADOR! ¿ESTÁS SEGURO[\\s\\S] $"));
	}

	private void eIntenteFicharAUnEntrenadorSinDineroSuficiente() {
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='JUGADORES - AGENTES LIBRES']")).click();
		this.driver.findElement(By.linkText("Rafael Cantero")).click();
		this.driver.findElement(By.linkText("Contratar")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("1000");
		this.driver.findElement(By.xpath("//form[@id='add-contractPlayer-form']/div/div[3]")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("1100000");
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.linkText("11")).click();
		this.driver.findElement(By.id("endDate")).click();
		this.driver.findElement(By.id("endDate")).clear();
		this.driver.findElement(By.id("endDate")).sendKeys("2023/05/11");
		this.driver.findElement(By.xpath("//form[@id='add-contractPlayer-form']/div/div[7]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("FICHAJES")).click();
		this.driver.findElement(By.xpath("//input[@value='ENTRENADORES - A. LIBRES']")).click();
		this.driver.findElement(By.linkText("Marcelino Garcia Toral")).click();
		this.driver.findElement(By.linkText("Contratar")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("1000000");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Los fondos de tu Club no son suficientes para pagar el salario!", this.driver.findElement(By.id("salary.errors")).getText());
	}

	private void cuandoAccedaALaVistaDeMiEntrenadorEIntenteDespedirlo() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		this.driver.findElement(By.xpath("//a/b")).click();
		this.acceptNextAlert = true;
		this.driver.findElement(By.linkText("Despedir")).click();
		Assert.assertTrue(this.closeAlertAndGetItsText().matches("^¡ESTÁS A PUNTO DE DESPEDIR A ÉSTE JUGADOR! ¿ESTÁS SEGURO[\\s\\S] $"));
	}

	private void entoncesSeHabraDespedidoAlEntrenadorYEstaremosEnLaPaginaDelClub() {
		Assert.assertEquals("MI CLUB DE FÚTBOL", this.driver.findElement(By.xpath("//h2")).getText());
	}

	//RN 14

	private void cuandoAccedaAlFormularioDeRegistrarJugador() {
		this.driver.findElement(By.linkText("MIS JUGADORES")).click();
		this.driver.findElement(By.linkText("Registrar Jugador")).click();
	}

	private void entoncesSeHabraRegistradoUnJugadorCuandoLoRelleneCorrectamente() {
		this.driver.findElement(By.id("firstName")).click();
		this.driver.findElement(By.id("firstName")).clear();
		this.driver.findElement(By.id("firstName")).sendKeys("Hola");
		this.driver.findElement(By.id("lastName")).clear();
		this.driver.findElement(By.id("lastName")).sendKeys("Hola");
		this.driver.findElement(By.id("birthDate")).click();
		this.driver.findElement(By.linkText("12")).click();
		this.driver.findElement(By.id("birthDate")).clear();
		this.driver.findElement(By.id("birthDate")).sendKeys("2000/05/12");
		this.driver.findElement(By.xpath("//form[@id='add-footballPlayer-form']/div")).click();
		new Select(this.driver.findElement(By.id("position"))).selectByVisibleText("MIDFIELDER");
		this.driver.findElement(By.xpath("//option[@value='MIDFIELDER']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Hola Hola", this.driver.findElement(By.linkText("Hola Hola")).getText());
	}

	private void seMostraraUnMensajeDeErrorDeProhibidoSiIntentoRegistrar() {
		this.driver.get("http://localhost:8080/footballPlayer/new");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Acceso Prohibido!", this.driver.findElement(By.xpath("//h2")).getText());
	}

	private void seMostraraUnMensajeDeErrorDeProhibidoSiIntentoRegistrarEntrenador() {
		this.driver.get("http://localhost:8080/coach/new");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Acceso Prohibido!", this.driver.findElement(By.xpath("//h2")).getText());
	}

	private void entoncesSeMostraraUnMensajeDeErrorCuandoLoRelleneCorrectamente() {
		this.driver.findElement(By.id("firstName")).click();
		this.driver.findElement(By.id("firstName")).clear();
		this.driver.findElement(By.id("firstName")).sendKeys("Hola");
		this.driver.findElement(By.id("lastName")).clear();
		this.driver.findElement(By.id("lastName")).sendKeys("Hola");
		this.driver.findElement(By.id("birthDate")).click();
		this.driver.findElement(By.linkText("12")).click();
		this.driver.findElement(By.id("birthDate")).clear();
		this.driver.findElement(By.id("birthDate")).sendKeys("2000/05/12");
		this.driver.findElement(By.xpath("//form[@id='add-footballPlayer-form']/div")).click();
		new Select(this.driver.findElement(By.id("position"))).selectByVisibleText("MIDFIELDER");
		this.driver.findElement(By.xpath("//option[@value='MIDFIELDER']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Solo puedes registrar/fichar a siete jugadores antes de publicar tu Club.", this.driver.findElement(By.xpath("//form[@id='add-footballPlayer-form']/div/div[5]/div/div/span[2]")).getText());
	}

	private void cuandoAccedaAlFormularioDeRegistrarEntrenadorYLoRellene() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		this.driver.findElement(By.linkText("Registrar Entrenador")).click();
		this.driver.findElement(By.id("firstName")).click();
		this.driver.findElement(By.id("firstName")).clear();
		this.driver.findElement(By.id("firstName")).sendKeys("hola");
		this.driver.findElement(By.id("lastName")).clear();
		this.driver.findElement(By.id("lastName")).sendKeys("hola");
		this.driver.findElement(By.id("birthDate")).click();
		this.driver.findElement(By.linkText("5")).click();
		this.driver.findElement(By.id("birthDate")).click();
		this.driver.findElement(By.id("birthDate")).clear();
		this.driver.findElement(By.id("birthDate")).sendKeys("2000/05/05");
		this.driver.findElement(By.xpath("//form[@id='add-coach-form']/div/div[4]/label")).click();
		this.driver.findElement(By.id("salary")).click();
		this.driver.findElement(By.id("salary")).clear();
		this.driver.findElement(By.id("salary")).sendKeys("1000000");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void entoncesSeHabraRegistradoUnEntrenadorCorrectamente() {
		Assert.assertEquals("hola hola", this.driver.findElement(By.xpath("//b")).getText());
	}

	private void entoncesSeMostraraUnMensajeDeError() {
		Assert.assertEquals("Solo puedes tener un entrenador en el equipo. Puedes cambiarlo por un agente libre.", this.driver.findElement(By.xpath("//form[@id='add-coach-form']/div/div/div/span[2]")).getText());
	}

	private void cuandoAccedaALaVistaEintenteBorrarElClub() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		this.acceptNextAlert = true;
		this.driver.findElement(By.linkText("Borrar Club")).click();
		Assert.assertTrue(this.closeAlertAndGetItsText().matches("^¡ESTÁS A PUNTO DE BORRAR TU EQUIPO! ¿ESTÁS SEGURO[\\s\\S] $"));
	}

	private void seBorraraCorrectamente() {
		Assert.assertEquals("¡AÚN NO TIENES UN CLUB!", this.driver.findElement(By.xpath("//h2")).getText());
	}

	private void seMostraraUnMensajeDeErrorDeProhibidoSiIntentoBorrarMiClub() {
		this.driver.get("http://localhost:8080/footballClubs/myClub/presidente1/delete");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("presidente1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("presidente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("¡Acceso Prohibido!", this.driver.findElement(By.xpath("//h2")).getText());

	}

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
