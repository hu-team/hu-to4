package nl.atd.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import nl.atd.dao.KlantDAO;
import nl.atd.helper.AuthHelper;
import nl.atd.model.Klant;
import nl.atd.service.KlantService;
import nl.atd.service.ServiceProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class KlantServiceTest {
	KlantService kservice = ServiceProvider.getKlantService();
	private Klant k1, k2, k3, k4;

	private ArrayList<Klant> klanten = new ArrayList<Klant>();
	private ArrayList<Klant> actualKlanten = new ArrayList<Klant>();

	@Before
	public void setUp() throws Exception {
		k1 = new Klant("Max van Kuik");
		k1.setEmail("kuikvanmax@hotmail.com");
		k1.setGebruikersnaam("maxiiemaxx");
		k1.setWachtwoord(AuthHelper.encryptWachtwoord("123"));
		k1.setLaatsteBezoek(null);
		kservice.addKlant(k1);
		klanten.add(k1);

		k2 = new Klant("Tom Valk");
		k2.setEmail("tomvalk@hotmail.com");
		k2.setGebruikersnaam("tomvalk");
		k2.setWachtwoord(AuthHelper.encryptWachtwoord("456"));
		k2.setLaatsteBezoek(null);
		kservice.addKlant(k2);
		klanten.add(k2);
		
		actualKlanten = kservice.getAlleKlanten();
		k3 = actualKlanten.get(0);
		k4 = actualKlanten.get(1);
	}

	@Test
	public void testGetAlleKlanten() {
		// Klant k1 ( aangemaakt in de setUp() ) zou gelijk moeten zijn aan Klant k3 ( Uit Database gehaald ) 
		// Klant k2 ( aangemaakt in de setUp() ) zou gelijk moeten zijn aan Klant k4 ( Uit Database gehaald )

		assertEquals(k1, k3);
		assertEquals(k2, k4);
	}

	@After
	public void tearDown() throws Exception {
		k1 = null;
		k2 = null;
		k3 = null;
		k4 = null;
		
		klanten = null;
		actualKlanten = null;
	}


}