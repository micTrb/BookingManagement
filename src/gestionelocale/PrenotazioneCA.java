package gestionelocale;

import java.io.Serializable;
import java.util.Calendar;

/**Classe PrenotazioneCA: 
 * descrive un oggetto prenotazione con catering e animazione.
 * Questa classe eredita dalla classe PrenotazioneC che a sua volta eredita dalla classe Prenotazione
 * In questa classe vi sono contenute:
 * 1) Variabili d'Istanza
 * 2) Costruttore della classe
 * 3) Metodi Getter (atti a restituire i vari valori delle variabili d'istanza)
 * 4) Metodi Printer (atti a stampare in output il riassunto di una prenotazione istanziata) 
 * 
 * La classe PrenotazioneCA è l'ultima del ramo ereditario delle Prenotazioni 
 * (Commenti ulteriori nei relativi file delle altre classi.)*/

public class PrenotazioneCA extends PrenotazioneC implements Serializable
{
	
	static final long serialVersionUID = 1;
	
	//VARIABILI D'ISTANZA
	/**Variabile Stringa che descrive il  tipo di animazione che può essere:
	   1) Organizzazione di giochi
	   2) Spettacolo di magia
	   3) Spettacolo di burattini 
	*/
	private String tipoAnimazione; 

	
	//COSTRUTTORE
	/**Instanzia un oggetto "Prenotazione affitto con catering"
	 * Le informazioni relative alla data, al nome del cliente e al numero degli invitati
	 * vengono ereditate dalla classe Prenotazione e Prenotazione C
	 */
	public PrenotazioneCA(Calendar data, String cliente, int invitati, String tipoAnimazione)
	{
		super(data, cliente, invitati);
		this.tipo = "Catering e Animazione";
		this.tipoAnimazione = tipoAnimazione;		
	}
	
	
	//METODI GETter
	/**Restituisce il tipo di animazione*/

	public String getAnimazione()
	{
		return tipoAnimazione;
	}
	
	/**Info riassuntive di una prenotazione con catering e animazione*/
	public void getInfo()
	{
		System.out.println();
		System.out.println("Data: " + this.getDataStringa());
		System.out.println("Prenotato da: " + this.getNomeCliente());
		System.out.println("Tipo prenotazione: " + this.getTipoPrenotazione());
		System.out.println("Numero invitati: " + this.getInvitati());	
		System.out.println("Tipo Animazione: " + this.getAnimazione());
	}
	

}
