package gestionelocale;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**Classe Prenotazione: 
 * descrive un oggetto prenotazione basilare, con affitto semplice.
 * Questa classe è la radice principale del ramo ereditario delle prenotazioni.
 * In essa vi sono contenute:
 * 1) Variabili d'Istanza
 * 2) Costruttore della classe
 * 3) Metodi Getter (atti a restituire i vari valori delle variabili d'istanza)
 * 4) Metodi Printer (atti a stampare in output il riassunto di una prenotazione istanziata) 
 * 
 * La classe Prenotazione è estesa da PrenotazioneC, prenotazione con Catering, che viene estesa
 * a sua volta da PrenotazioneCA, con Catering e animazione. 
 * (Commenti ulteriori nei relativi file delle classi.)*/



public class Prenotazione implements Serializable
{
	
	static final long serialVersionUID = 1;
	
	//VARIABILI D'ISTANZA
	
	/**
	 * Il modificatore è "protected" per rendere visibili le variabili
	 * Calendar dt, String nomeCliente, String tipo all'interno del package "gestionelocale".
	 * Questo livello di visibilità permette di applicare il metodo
	 * getInfo() su tutta la gerarchia "Prenotazione" 
	 * - Calendar: descrive un oggetto data
	 * - nomeCliente:  stringa che definisce il nome di chi prenota
	 * - Stringa che definisce il tipo di prenotazione:
	 *   1) Affitto semplice
	 *   2) Affitto con Catering
	 *   3) Affitto con Catering e Animazione
	 * */

	protected Calendar dt;	
	protected String nomeCliente;
	protected String tipo;

	
	//COSTRUTTORE
	/**Istanzia un oggetto Prenotazione con affitto semplice*/
	public Prenotazione(Calendar data, String cliente)
	{
		this.dt = data;
		this.nomeCliente = cliente;
		this.tipo = "Affitto semplice";
	}
	
	
	//METODI GETTER
	
	/**Restituisce il nome del cliente che ha effettuato la prenotazione*/
	
	public String getNomeCliente()
	{
	  return this.nomeCliente;	
	}
	
    /**Restituisce il tipo della prenotazione che ha effettuato il cliente*/
	
	public String getTipoPrenotazione()
	{
	  return this.tipo;	
	}
	
	
	/**Restituisce la data della prenotazione come oggetto Calendar*/

	public Calendar getData()
	{
		return this.dt;
	}
	
	/**Restituisce la data in formato Stringa*/
	
	public String getDataStringa()
	{
		SimpleDateFormat DataInStringa = new SimpleDateFormat("dd/MM/yyyy");
		return DataInStringa.format(this.dt.getTime());
	}
	

	//METODI PRINTER
	/**Info riassuntive di una prenotazione*/
	
	public void getInfo()
	{
		System.out.println();
		System.out.println("Data: " + this.getDataStringa());
		System.out.println("Prenotato da: " + this.getNomeCliente());
		System.out.println("Tipo prenotazione: " + this.getTipoPrenotazione());
	}
}
