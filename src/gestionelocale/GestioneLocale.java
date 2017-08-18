package gestionelocale;

import java.util.*;
import java.util.Calendar;
import java.io.*;

/**
 * Classe GestioneLocale:
 * questa classe descrive un oggetto che contiene:
 * 1) Variabili d'Istanza
 * 2) Costruttore della classe
 * 3) Metodi atti alla gestione degli oggetti Prenotazione in tutta la loro gerarchia.
 * 
 * La classe GestioneLocale costituisce il meccanismo logico principale di tutta la specifica:
 * in essa vi sono contenuti metodi che permettono:
 * 1) di istanziare oggetti "Prenotazione" e di aggiungerli al vettore
 * 2) di visualizzare l'elenco delle prenotazioni filtrandolo su vincoli di impostazione
 * 3) di eliminare prenotazioni secondo determinati parametri
 * 4) di controllare se una data è disponibile dal giorno corrente
 * 5) di visualizzare le prenotazioni di un utente sulla base del nome intero o una porzione di esso
 * 
 * (Ulteriori commenti ai metodi all'inizio di ogni dichiarazione)*/

public class GestioneLocale 
{	
	//VARIABILI D'ISTANZA
	/**1) Vettore contenente una lista di oggetti "Prenotazione" ordinati per data*/
	public Vector<Prenotazione> elencoPrenotazioni; 
	
    /**Variabile boolean flag: assume valore TRUE qualora siano state apportate modifiche al file*/
	public boolean flagMOD = false;
	
	
	
	
	//COSTRUTTORE 
	/**Costruttore della classe GestioneLocale
	 * crea un nuovo vettore vuoto che conterrà l'elenco di tutte le prenotazioni
	 * */
	public GestioneLocale()
	{
		elencoPrenotazioni = new Vector<Prenotazione>();
	}
	
	//METODI GETTER
	
	/**Metodo boolean: restituisce il valore della variabile flagMOD qualora
	 * siano stati effettuati modifiche o salvataggi al file*/
	public boolean getSavedIO()
	{
		return this.flagMOD;
	}
	
	/**Restituisce un elemento del vettore elencoPrenotazioni da indice*/
	public Prenotazione getElement(int idx)
	{
		return this.elencoPrenotazioni.get(idx);
	}
	
	/**Restituisce l'indice di un oggetto data
	 * La scansione del while parte dalla fine dell'elenco perché 
	 * si presuppone che la data cercata sia tra le ultime aggiunte in elenco */
	public int getIndice(Calendar dateIDX)
	{
		int i = this.elencoPrenotazioni.size()-1;
		int index = 0;
		while(index == 0 & i >= 0)
		{
			if (dateIDX.after(this.elencoPrenotazioni.get(i).getData()))
			{
				index = i + 1;
			}
			
			i--;
		}
		return index;
	}
	
	
	//METODI SETTER
	/**Metodo void che setta la variabile FlagMOD, ogniqualvolta viene effettuata una
	   modifica al file*/
	public void setFlag(boolean IO)
	{
		this.flagMOD = IO;
	}
	
	
	//METODI AGGIUNGI 
	/** Permette di istanziare una nuova prenotazione */
	public void Prenota(Calendar date, String cliente, int invitati, String animazione)
	{
		int index;
		index = this.getIndice(date);
		
		/**Prenotazione con affitto semplice*/
		if(invitati == 0)
		{
			Prenotazione nuovaPrenotazione = new Prenotazione(date, cliente);
			this.elencoPrenotazioni.insertElementAt(nuovaPrenotazione, index);
			flagMOD = true;
		}
		
		/**Prenotazione con affitto e catering*/
		else if(animazione.equals(""))
		{
			Prenotazione nuovaPrenotazione = new PrenotazioneC(date, cliente, invitati);
			this.elencoPrenotazioni.insertElementAt(nuovaPrenotazione, index);
			flagMOD = true;
		}
		
		/**Prenotazione con affitto, catering e animazione*/
		else
		{
			Prenotazione nuovaPrenotazione = new PrenotazioneCA(date, cliente, invitati, animazione);
			this.elencoPrenotazioni.insertElementAt(nuovaPrenotazione, index);
			flagMOD = true;
		}	
	}
	
	
	
	
	//METODI VISUALIZZA
	
	/** 
	 * Stampa l'elenco di tutte le prenotazioni tramite il metodo 
	 * getInfo() applicato a tutti gli elementi mediante un foreach
	 * */
	public void Visualizza()
	{
		for (Prenotazione i: this.elencoPrenotazioni)
		{
			i.getInfo();
		}
	}
	
	/**
	 * Metodo utilizzato nel main come supporto al metodo filtraSelezione(String, char) 
	 * Stampa l'elenco di tutte le prenotazioni in base a un vettore di
	 * indici mandato come parametro. Il vettore viene inviato dal metodo filtraSelezione
	 * e contiene l'insieme degli indici (rappresentati da oggetti Prenotazione) 
	 * su cui è stata effettuata la selezione degli eventi per cliente. 
	 * Il risultato sarà una visualizzazione delle prenotazioni filtrata per il nome
	 * del cliente.
	 */
	public void VisualizzaFiltro(Vector<Prenotazione> ind)
	{
		for (Prenotazione i : ind)
		{
			i.getInfo();
		}
	}
	
	/** Stampa l'elenco solo delle prenotazioni con Catering e Animazione
	 *  tramite il metodo getInfo() applicato a tutti gli elementi mediante un foreach*/
	public void VisualizzaCA()
	{
		for (Prenotazione i: this.elencoPrenotazioni)
		{
			if(i.getTipoPrenotazione().equals("Catering e Animazione"))
			{
				i.getInfo();
			}
		}
	}
	
	//METODI ELIMINA
	/**Per quanto riguarda la funzione Elimina applico la regola del polimorfismo 
	 * per ottenerne due versioni: 
	 * 1) la prima versione viene utilizzata nel main nel momento in cui gli viene passato 
	 * un indice (rappresentato da un intero) come parametro.
	 * 2) la seconda versione prende in input un oggetto Prenotazione che rappresenta
	 * l'indice delle prenotazioni che vogliamo eliminare.  */
	
	public void Elimina(int idx)
	{
		this.elencoPrenotazioni.removeElementAt(idx);
		flagMOD = true; //E' avvenuta una modifica: flagMOD è uguale a TRUE
	}
	
	public void Elimina(Prenotazione idx)
	{
		this.elencoPrenotazioni.removeElement(idx);
		flagMOD = true; //E' avvenuta una modifica: flagMOD è uguale a TRUE
	}	
	


	//GESTORE DELLE ECCEZIONI
	/**
	 * Restituisce un valore booleano a seconda delle eccezioni che si verificano:
	 * TRUE: se il file è caricato correttamente
	 * FALSE: 
	 * 		1) Se il file non è presente
	 * 		2) Se il file contiene un oggetto di tipo errato 
	 * 		3) Se il file è vuoto
	 * 		4) Se ci sono errori di Input/Output
	 * */
	
	//CARICAMENTO
	public boolean caricaFile(String nuovoFile)
	{
		// File caricato correttamente
		try
		{
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nuovoFile)));
			this.elencoPrenotazioni = (Vector <Prenotazione>) file_input.readObject();
			file_input.close();
			return true;
		} 
		
		//CASO: File non presente
		catch (FileNotFoundException e) 
		{
			System.out.println("ATTENZIONE: Il file " + nuovoFile + " non esiste");
			return false;
		} 
		// CASO: File contenente un oggetto di tipo errato
		catch (ClassCastException e) 
		{ 
			System.out.println("ERRORE: Il file contiene un oggetto di tipo errato"); 
			System.out.println(e); 
			return false;
		} 
		// CASO: File vuoto
		catch (ClassNotFoundException e) 
		{
			System.out.println("ERRORE di lettura");
			System.out.println(e);
			return false;
		} 
		// CASO: Errori di I/O
		catch (IOException e) 
		{
			System.out.println("ERRORE di I/O");
			System.out.println(e);
			return false;
		}
	}
	
	// SALVATAGGIO
	/**
	 * Restituisce TRUE se il salvataggio è stato eseguito correttamente
	 * Restituisce FALSE se ci sono stati problemi di Input/Output
	 * Altrimenti, se non ci sono state modifiche restituisce TRUE */
	public boolean salvaFile(String nuovoFile) 
	{
		/**Se sono stati effettuati salvataggi esegui le seguenti eccezioni*/
		if (getSavedIO()) 
		{ 
			try 
			{
				ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nuovoFile)));
					
				//Salva l'oggetto vettore nel file
				file_output.writeObject(this.elencoPrenotazioni); 
				file_output.close();
					
				//File salvato, reinizializzo la variabile "flagMOD" a false
				flagMOD = false; 
				return true;
			} 
			catch (IOException e) 
			{
				System.out.println("ERRORE di I/O");
				System.out.println(e);
				return false;
			}		
		} 
		else
		{
			System.out.println();
			System.out.println("Non ci sono modifiche da salvare");
			return true;
		}	
	}

	//METODI per RICERCA DATA DISPONIBILE
				
	/**Metodo che verifica la PRIMA data disponibile rispetto a un'altra data ricevuta come parametro
	 * (si suppone che sia il giorno corrente).
	 * Viene utilizzato il metodo "roll" della classe Calendar che aumenta di un giorno la data corrente 
	 * finché verifica che la una data non è stata prenotata
	 */
	
	public Calendar firstDate(Calendar dateF)
	{
		int index;
		do
		{
			index = this.binSearch(dateF);
			if(index != -1)
			{
				dateF.roll(Calendar.DAY_OF_YEAR, 1); //giorno successivo...
			}
		}
		while(index != -1);
		
		return dateF;
	}
	
	/** Metodo che verifica se una data ha già una prenotazione. */
	public boolean dateAvailable(Calendar dateAV)
	{
		int index = this.binSearch(dateAV);
		
		/**Data SENZA prenotazione*/
		if (index == -1) 
		{
			return false;
		}
		else 
		{
			return true;
		}
	}	
	

	
	/*METODI SEARCH*/
	/**Il primo metodo è la classica ricerca binaria
	 * Il secondo metodo è una ricerca lineare
	 * Entrambi i metodi vengono applicati sul vettore contenente tutte le prenotazioni
	 */

	/**RICERCA BINARIA specifica per le date:
	 * prende in input un oggetto "data" (Calendar), 
	 * restituisce un intero che rappresenta l'indice nel vettore 
	 * dell'oggetto Prenotazione in relazione alla data "dateBS"
	 */
	public int binSearch (Calendar dateBS) 
	{
		int index;
		int sx, dx, cx; 
		
		sx = 0; 
		dx = this.elencoPrenotazioni.size()-1; 
		index = -1;
		
		while (index == -1 && sx <= dx) 
		{
			cx = (sx+dx)/2;
			
			if (dateBS.equals(this.elencoPrenotazioni.get(cx).getData()))
			{ 
				// trovato 
				index = cx; 
			} 
			else 
			{ 
				
				if (dateBS.after(this.elencoPrenotazioni.get(cx).getData())) 
				{
					// continua a destra 
					sx = cx + 1; 
				}
				else 
				{   
					// continua a sinistra 
					dx = cx - 1; 
				}
			}
		}
		return index;
	}
	
	
	/**RICERCA LINEARE del vettore "elencoPrenotazioni":
	   Parametri: il primo è la stringa che rappresenta il nome del cliente
	   che intende effettuare la prenotazione, il secondo parametro è il modo in cui si vuole 
	   effettuare la ricerca: 't' inserendo il nome per intero(se lo si conosce 
	   per intero a priori) oppure 'p', se lo si conosce parzialmente. Le due tipologie
	   di ricerca avranno come metodi utilizzati per le stringhe rispettivamente equals() 
	   e contains().
	   La funzione resituisce un vettore di oggetti Prenotazioni che hanno soddisfatto i vincoli del filtro nella selezione. */
	
	public Vector<Prenotazione> filtraSelezione(String nomeCliente, char mode)
	{
		Vector<Prenotazione> ind = new Vector<Prenotazione>();
		switch(mode)
		{

		    /**Caso in cui si conosce per intero il nome del cliente specifico */
			case 't':
				for(int i = 0; i < this.elencoPrenotazioni.size(); i++)
				{
					/**Preparo due valori String per rendere chiari i passaggi
					 * "cliente1": per ogni indice del vettore elencoPrenotazioni, 
					 * 			   recupera il nome del cliente
					 * "cliente2": applica il metodo toLowerCase(), ad ogni nomeCliente
					 * 			   in modo da rendere più semplice l'elaborazione delle stringhe.
					 * */
					
					String cliente1 = this.elencoPrenotazioni.get(i).nomeCliente;
					String cliente2 = cliente1.toLowerCase();
					
					/**Valore boolean: TRUE se */
					boolean clienteEq = cliente2.equals(nomeCliente.toLowerCase());
					
					if(clienteEq)
					{
						ind.add(this.elencoPrenotazioni.elementAt(i));
					}	
				}
				break;
				
			/**Caso in cui si conosce in parte il nome del cliente specifico */
			case 'p': 
				for(int i = 0; i < this.elencoPrenotazioni.size(); i++)
				{
					String cliente1 = this.elencoPrenotazioni.get(i).nomeCliente;
					String cliente2 = cliente1.toLowerCase();
					
					boolean clienteEq = cliente2.contains(nomeCliente.toLowerCase());
					
					if(clienteEq)
					{
						ind.add(this.elencoPrenotazioni.elementAt(i));
					}
				}
				break;
		}
		return ind;
	}
}
