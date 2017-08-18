package gestionelocale;

import java.io.Serializable;
import java.util.Calendar;

/**Classe PrenotazioneC: 
 * descrive un oggetto prenotazione con solo Catering. 
 * Questa classe eredita per prima dalla classe Prenotazione e la estende aggiungendo un 
 * solo dato di Integer, che rappresenta il numero di invitati, come richiesto da specifica
 * per le prenotazioni con catering.
 * In essa vi sono contenute:
 * 1) Variabili d'Istanza
 * 2) Costruttore della classe
 * 3) Metodi Getter (atti a restituire i vari valori delle variabili d'istanza)
 * 4) Metodi Printer (atti a stampare in output il riassunto di una prenotazione istanziata) 
 * 
 * Questa classe viene estesa a sua volta da PrenotazioneCA, con Catering e animazione. 
 * (Commenti ulteriori nei relativi file delle altre classi.)*/

public class PrenotazioneC extends Prenotazione implements Serializable
{
	
	static final long serialVersionUID = 1;
	
	//VARIABILI D'ISTANZA
	/**
	 * La variabile d'istanza "numeroInvitati" è di tipo Integer
	 * ed è l'unica informazione in più rispetto 
	 * alla classe Prenotazione (Solo affitto).
	 */
	private int numeroInvitati; 

	//COSTRUTTORE
	/**Instanzia un oggetto "Prenotazione affitto con catering"
	 * Le informazioni relative alla data e al nome del cliente vengono ereditate
	 * dalla classe Prenotazione
	 * */
	
	public PrenotazioneC(Calendar data, String cliente, int invitati)
	{
		super(data, cliente);
		this.tipo = "Solo Catering";
		this.numeroInvitati = invitati;
	}
	
	
	//METODI Getter
	
	/**Restituisce il numero degli invitati*/
	
	public int getInvitati()
	{
        return numeroInvitati;
	}
	
	//METODI Printer
	
	/**Info riassuntive di una prenotazione con catering*/
	public void getInfo()
	{
		System.out.println();
		System.out.println("Data: " + this.getDataStringa());
		System.out.println("Prenotato da: " + this.getNomeCliente());
		System.out.println("Tipo prenotazione: " + this.getTipoPrenotazione());
		System.out.println("Numero invitati: " + this.getInvitati());

	}

}
