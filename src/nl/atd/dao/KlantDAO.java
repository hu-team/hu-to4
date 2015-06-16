package nl.atd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import nl.atd.helper.DatabaseHelper;
import nl.atd.model.Auto;
import nl.atd.model.Klant;

public class KlantDAO {
	
	/**
	 * Get klanten
	 * @param query
	 * @param metAutos
	 * @return array met klanten
	 */
	private ArrayList<Klant> getKlanten(String query, boolean metAutos) {
		ArrayList<Klant> klanten = new ArrayList<Klant>();
		
		try {
			Connection connection = DatabaseHelper.getDatabaseConnection();
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(query);
			
			while(set.next()) {
				Klant klant = new Klant(set.getString("naam"));
				klant.setGebruikersnaam(set.getString("gebruikersnaam"));
				klant.setWachtwoord(set.getString("wachtwoord"));
				
				Calendar laatst = Calendar.getInstance();
				
				try {
					Timestamp ts = set.getTimestamp("laatste_bezoek");
					if(ts != null) {
						laatst.setTimeInMillis(ts.getTime());
						klant.setLaatsteBezoek(laatst);
					}else{
						klant.setLaatsteBezoek(null);
					}
				}catch(NumberFormatException | SQLException | NullPointerException e) {
					klant.setLaatsteBezoek(null);
				}
				
				klant.setEmail(set.getString("email"));
				
				/**
				 * @since 16-06-2015, sprint 3
				 */
				klant.setAdres(set.getString("adres"));
				klant.setPostcode(set.getString("postcode"));
				klant.setWoonplaats(set.getString("woonplaats"));
				klant.setTelefoonnummer(set.getString("telefoonnummer"));
				
				if(metAutos){
					AutoDAO autoDAO = new AutoDAO();
					ArrayList<Auto> klantAutos = autoDAO.getAutosVanKlant(set.getString("gebruikersnaam"));
					klant.getAutos().addAll(klantAutos);
				}
				
				klanten.add(klant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return klanten;
	}
	
	/**
	 * Get alle klanten in database
	 * @param autos boolean, moeten autos ook uit db gehaald worden?
	 * @return array met klanten
	 */
	public ArrayList<Klant> getAlleKlanten(boolean autos) {
		return this.getKlanten("SELECT * FROM klant", autos);
	} 
	
	/**
	 * Get klant
	 * @param gebruikersnaam
	 * @return klant of null
	 */
	public Klant getKlant(String gebruikersnaam) {
		ArrayList<Klant> klanten = this.getKlanten("SELECT * FROM klant WHERE gebruikersnaam LIKE '" + gebruikersnaam + "'", true);
		if(klanten.size() >= 1) {
			return klanten.get(0);
		}
		return null;
	}
	
	/**
	 * Add Klant
	 * @param klant
	 * @return gelukt?
	 */
	public boolean addKlant(Klant klant){
		
		try{
			Connection connection = DatabaseHelper.getDatabaseConnection();
			PreparedStatement st = connection.prepareCall("INSERT INTO klant(gebruikersnaam, wachtwoord, email, naam, adres, postcode, woonplaats, telefoonnummer) values(?, ?, ?, ?, ?, ?, ?, ?)");
			
			st.setString(1, klant.getGebruikersnaam());
			st.setString(2, klant.getWachtwoord());
			st.setString(3, klant.getEmail());
			st.setString(4, klant.getNaam());
			st.setString(5, klant.getAdres());
			st.setString(6, klant.getPostcode());
			st.setString(7, klant.getWoonplaats());
			st.setString(8, klant.getTelefoonnummer());
			
			st.execute();
			
			connection.close();
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * Verwijder alle data in tabel, let op!! Alleen voor testen!
	 */
	public void deleteAlles() {
		try {
			Connection connection = DatabaseHelper.getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
			statement.executeUpdate("TRUNCATE klant;");
			statement.executeUpdate("SET FOREIGN_KEY_CHECKS=1;");

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
