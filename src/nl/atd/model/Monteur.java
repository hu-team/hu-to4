package nl.atd.model;

/**
 * Monteur object
 * @author ATD Developers
 *
 */
public class Monteur extends Persoon {
	private int salarisnummer;
	
	public Monteur(String nm, int slnr) {
		super(nm);
		this.salarisnummer = slnr;
	}
	
	/**
	 * Get Salaris nummer van monteur
	 * @return Salarisnummer van monteur
	 */
	public int getSalarisnummer() {
		return this.salarisnummer;
	}
}
