package gestionelocale;

import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.text.ParseException;

/**Classe GestisciMain:
 * in questa classe è contenuto il main.
 * La classe implemente un'interfaccia testuale che permette di eseguire varie operazioni
 * per quanto riguarda la gestione delle prenotazioni. 
 * Tra le più importanti:
 * [A]ggiungi prenotazione: 
   [V]isualizza tutte le prenotazioni: 
   [E]limina prenotazione: 
   [C]ontrolla se una data è disponibile: 
   [L]ista prenotazioni per utente: 
   */
public class GestisciMain
{
	public static void main(String[] args)
	{
		/**Crea un nuovo oggetto Scanner per l'I/O*/
		Scanner input = new Scanner(System.in);
		
		/**Crea un nuovo oggetto per la gestione del locale, quindi un nuovo elenco
		 * delle prenotazioni*/
		
		GestioneLocale gestione = new GestioneLocale();
		
		/**Crea una stringa che contine il nome del file*/
		String nomeFile = new String();
		
		/**Crea un formato per l'I/O delle date*/
		SimpleDateFormat formatoSemplice = new SimpleDateFormat ("dd/MM/yyyy");


		boolean okFlag = false;

		while(!okFlag)
		{
			//DUE SCELTE:
			/**1) Si inserisce il nome del file,
			 * 2) Si crea un nuovo file digitando NEW
			 */
			
			System.out.println("Inserisci il nome del file, oppure creane uno [N]uovo: ");
			nomeFile = input.nextLine().toUpperCase();
			
			/**Se si crea un nuovo file*/
			if(nomeFile.equals("N"))
			{
				System.out.println("Digita il nome del nuovo file: ");
				nomeFile = input.nextLine();
				File fileNuovo = new File(nomeFile);
				
				/**Se il file è già esistente*/
				if(fileNuovo.exists())
				{
					char sn;
					do
					{
						System.out.println("Il file è già esistente. Sovrascrivere? [S/N]:");
						sn = input.nextLine().toUpperCase().charAt(0);
						if(sn == 'S')
						{
							gestione.salvaFile(nomeFile);
							gestione.setFlag(true);
							okFlag = true;	
						}
					}
					while(sn != 'S' && sn != 'N');	
				}
				else
				{
					gestione.setFlag(true);
					okFlag = true;
				}
			}
			
			/**Se invece viene fornito il nome, si carica il file*/
			else
			{
				okFlag = gestione.caricaFile(nomeFile);	
			}
		}
		
		// MENU' 
		
		/**Inizializzo su quattro tipologie di dato, le seguenti variabili: */
		
		String cliente, data, annulla, scelta;
		char choose, tipoPrenotazione, tipoAnimazione;
		int invitati = 0;
		boolean dataFlag, invitatiFlag;
		
		do
		{
		  System.out.println(); //riga bianca
		  System.out.println("Scegli operazione: ");
		  System.out.println(); //riga bianca

		  System.out.println("[A]ggiungi prenotazione: "); 
		  System.out.println("[V]isualizza tutte le prenotazioni: "); 
		  System.out.println("[P]renotazioni con Catering e Animazione: "); 
		  System.out.println("[E]limina prenotazione: "); 
		  System.out.println("[C]ontrolla data disponibile: "); 
		  System.out.println("[L]ista prenotazioni per utente: "); 
		  System.out.println("[S]alva: "); 
		  System.out.println("[U]scita: "); 

		  /**Prima che diventi Character, finchè è ancora String
		  maiuscoleggio la variabile "choose" per evitare ambiguità tra l'utente e l'interfaccia*/
		  
	      choose = input.next().toUpperCase().charAt(0);

		  switch(choose)
		  {
		  
		  
		  //CASO "Aggiungi prenotazione"
			case 'A':
				
				/*Richiesta del nome del cliente*/
				
				System.out.println("Inserisci il nome del cliente che desidera effettuare la prenotazione, ");
				System.out.println("oppure [A]nnulla");
				input.nextLine();
				cliente = input.nextLine();
				
				/**Viene creata un'istanza della data*/
				Calendar dataInstance = Calendar.getInstance();
				
				/**Il metodo clear() setta tutti i valori del calendario come
				 * "undefined". Successivamente verranno attivati in input dall'utente solo i valori 
				 * relativi a giorno, mese, anno*/
				dataInstance.clear();
				
				/**Uscita nel caso l'utente scelga 'A' */
				if(cliente.equals("A"))
				{
				  break;
				}
	
				do
				{	
					dataFlag = false;
					System.out.println("Inserisci la data in cui vuoi prenotare il locale [gg/mm/aaaa]");
					System.out.println("oppure [A]nnulla:");
					data = input.nextLine();
					
					if(data.equals("A"))
					{
					  break;
					}
					
					
					//ECCEZIONI
					try
					{
						/**Dopo aver parsato la stringa "data" mediante il metodo parse(), 
						 * che mi restituisce un valore Date, setto il valore del tempo
						 * del calendario con la data ricevuta come parametro.
						 *  */
						dataInstance.setTime(formatoSemplice.parse(data));
						
						/**Preparo due variabili boolean per rendere chiari i controlli successivi
						 * 
						 * 1) dataCorretta: controlla che il formato Stringa della data(metodo format)
						 *    sia uguale alla stringa inserita dall'utente*/
						boolean dataCorretta = formatoSemplice.format(dataInstance.getTime()).equals(data);
						
						/**2) dataDisponibile: controlla che la data sia disponibile mediante il metodo
						 *    dateAvailable(date) definito in GestioneLocale*/
						boolean dataDisponibile = gestione.dateAvailable(dataInstance);

						if(!dataCorretta)
						{
							System.out.println("Data inesistente");
						}
						
						else if (dataDisponibile)
						{
							System.out.println("Data già prenotata!");
						}
						/**Se la data esiste e non è stata prenotata, allora 
						 * si setta la variabile boolean dataFlag TRUE*/
						else
						{
							dataFlag = true;
						}
					}
					catch(ParseException exc)
					{
						System.out.println("Data in formato NON corretto");
					}
					
				}
				while(!dataFlag);
		
				
				if(!dataFlag)
				{
					break;
				}
				
				/**Informazioni aggiuntive relative al tipo di prenotazione, saranno richiesti:
				 * Tipo di affitto
				 * Numero di invitati(qualora venga scelto di disporre del catering)
				 * Tipo di animazione(qualora venga scelto di disporre di animazione e catering) */
				do
				{
					System.out.println("Scegli tipo di affitto: ");
					System.out.println(); //riga bianca
			        System.out.println("[1] Affitto semplice: "); 
					System.out.println("[2] Catering: "); 
				    System.out.println("[3] Animazione e Catering: "); 
					System.out.println("[4] Annulla operazione: "); 
					
					System.out.println("Inserisci la tua scelta: "); 
					
					scelta = input.nextLine();
					tipoPrenotazione = scelta.toUpperCase().charAt(0);
					
					switch(tipoPrenotazione)
					{
					      //Crea PRENOTAZIONE SEMPLICE
					  case '1':
						  /**Crea la prenotazione*/
						  gestione.Prenota(dataInstance, cliente, 0, "");
						  
						  /**Stampa la prenotazione appena effettuata*/
						  int p = gestione.getIndice(dataInstance);
						  Prenotazione pr = gestione.getElement(p);
						  pr.getInfo();

						  break;
					
						  //Crea PRENOTAZIONE con CATERING
					  case '2': 
						  
						  do
						  {
							  invitatiFlag = false;
							  System.out.println("Inserisici il numero degli invitati: ");
							  try
							  {
								  
								  invitati = input.nextInt();
								  
								  if(invitati > 0)
									  invitatiFlag = true;
								  else
									  System.out.println("Deve esserci almeno un invitato!: ");
							  }
							  catch (InputMismatchException e)
							  {
								  annulla = input.next();
								  char annullato = annulla.charAt(0);
								  if(annullato == 'A')
									  System.out.println("Devi inserire un intero: ");  
							  }
						  }
						  while(!invitatiFlag);
						  
						  /**Crea la prenotazione*/
						  gestione.Prenota(dataInstance, cliente, invitati, "");
						  
						  /**Stampa la prenotazione appena effettuata*/
						  int pC = gestione.getIndice(dataInstance);
						  Prenotazione prC = gestione.getElement(pC);
						  prC.getInfo();

						  
						  break;
							  
						  //Crea Prenotazione con CATERING E ANIMAZIONE
					  case '3':
						  do
						  {
							  invitatiFlag = false;
							  System.out.println("Inserisici il numero degli invitati oppure [A]nnulla : ");
							  try
							  {
								  invitati = input.nextInt();
								  if(invitati > 0)
									  invitatiFlag = true;
								  else
									  System.out.println("Deve esserci almeno un invitato!: ");
							  }
							  catch (InputMismatchException e)
							  {
								  annulla = input.nextLine().toUpperCase();
								  char annullato = annulla.charAt(0);
								  if(annullato == 'A')
									  break;
									  System.out.println("Devi inserire un intero: ");  
							  }
						  }
						  while(!invitatiFlag);
						  
					      if(!invitatiFlag)
					      {	  
					    	  break;
					      }
						  if(invitati > 0)
						  {	  
							  do
							  {
								  System.out.println(); //riga bianca
								  System.out.println("Scegli tipo di animazione: ");
								  System.out.println(); //riga bianca
							      System.out.println("[1] Organizzazione di giochi: "); 
								  System.out.println("[2] Spettacolo di magia: "); 
								  System.out.println("[3] Spettacolo di Burattini: "); 
								  
								  System.out.println("Inserisci la tua scelta: "); 
							
								  tipoAnimazione = input.next().charAt(0);
								  input.nextLine();
								  
								  switch(tipoAnimazione)
								  {
								    case '1':
								    	gestione.Prenota(dataInstance, cliente, invitati, "Organizzazione di giochi");
								    	break;
								    	
								    case '2':
								    	gestione.Prenota(dataInstance, cliente, invitati, "Spettacolo di magia");
								    	break;
								    	
								    case '3':
								    	gestione.Prenota(dataInstance, cliente, invitati, "Spettacolo di Burattini");
								    	break;
								  }   
							  }
							  while(tipoAnimazione != '1' && tipoAnimazione != '2' && tipoAnimazione != '3');
						  }
						  /**Stampa la prenotazione appena effettuata*/
						  int pCA = gestione.getIndice(dataInstance);
						  Prenotazione prCA = gestione.getElement(pCA);
						  prCA.getInfo();
						  break;
					} 
				}
				while(tipoPrenotazione != '1' && tipoPrenotazione != '2' && tipoPrenotazione != '3' && tipoPrenotazione != '4' );			
				
				break; //CHIUDE il caso 'A'

			// CASO "Visualizza tutte le prenotazioni"
			case 'V':
				gestione.Visualizza();
				if(gestione.elencoPrenotazioni.isEmpty())
				{
					System.out.println("Nessuna prenotazione in questo file! ");
					System.out.println("Premi [A] per prenotare");
				}
				break;
				
			// CASO "Visualizza solo le prenotazioni con Catering e animazione"
			case 'P':
				gestione.VisualizzaCA();
				if(gestione.elencoPrenotazioni.isEmpty())
				{
					System.out.println("Nessuna prenotazione con Catering e animazione! ");
					System.out.println("Premi [A] per prenotare");
				}
				break;
				
			// CASO "Elimina una prenotazione"
			case 'E':
				
				int el = 0;
				Vector<Prenotazione> indici = new Vector<Prenotazione>();
				
				boolean deleteFlag;
			    char tipo, canc;
				
				do
				{
					System.out.println(); 
					System.out.println("Cancella per:  "); 
					System.out.println(); 
					System.out.println("[1] Data"); 
					System.out.println("[2] Nome cliente"); 
				    System.out.println("[3] Annulla operazione: "); 
				   

				    tipo = input.next().charAt(0);
				    input.nextLine();
				    
				    Calendar dateInstanceD = Calendar.getInstance();
					dateInstanceD.clear();
					
				    switch(tipo)
				    {
				    	/**Elimina per data: 
				    	 * viene applicato la prima versione di Elimina(int idx)
				    	 * in questo caso viene preso come parametro un intero che rappresenta 
				    	 * l'indice dell'oggetto prenotazione che si intende eliminare.
				    	 * Questo per rendere più semplice la procedura 
				    	 * dovendo applicare il metodo a casi specifici cioè alla singola data.
				    	 */
				    	case '1':
				    		do
							{
				    			String preD;
								deleteFlag = false;
								
								System.out.println("Inserisci la data in formato [gg/mm/aaaa] oppure [A]nnulla: ");
								preD = input.nextLine();
								
								if(preD.equals("A"))
								{
									break;
								}
								
								try
								{
									dateInstanceD.setTime(formatoSemplice.parse(preD));	
									/**dataCorretta: controlla che il formato Stringa della data(metodo format)
									 * (lo metto in una variabile per renderne più chiara la lettura)*/
									boolean dataCorretta = formatoSemplice.format(dateInstanceD.getTime()).equals(preD);
									
									if(!dataCorretta)
									{
										System.out.println("Data non esistente! ");
									}
									else 
									{
										el = gestione.binSearch(dateInstanceD);
										
										/**Se viene trovata la data*/
										if(el != -1)
										{
											deleteFlag = true;
										} 
										else 
										{
											System.out.println("Nessuna prenotazione a questa data");
										}
									}
								}	
								catch (ParseException e)
								{
									System.out.println("Data in formato non corretto! ");
								}	
								
							}
				    		while(!deleteFlag);	
				    		
				    		if(!deleteFlag)
				    		{
				    			break;
				    		}
				    		
				    		do
				    		{
				    			System.out.println("Eliminare la prenotazione effettuata da: " + 
				    									gestione.getElement(el).getNomeCliente() + " per data " + 
				    									gestione.getElement(el).getDataStringa());
				    			System.out.println("[S/N] ?");
				    			
				    			canc = input.next().toUpperCase().charAt(0);
				    			if(canc == 'S') gestione.Elimina(el);
				    			System.out.println("Prenotazione Cancellata!");
				    			break;
				    		}
				    		while(canc != 'S' && canc != 'N');
				    		break;
				    		
				    	case '2':	
				    		
				    		/**Il secondo caso utilizza la seconda versione di Elimina:
				    		 * si elimina una porzione del vettore delle prenotazioni
				    		 * (porzione filtrata sul nome di chi prenota). In questo caso
				    		 * il parametro è Elimina(Prenotazione), quindi per ogni elemento
				    		 * del Vettore "indici" verrà applicato la seconda versione del 
				    		 * metodo Elimina.
				    		 */
							String nomeD;
				    		System.out.println("Inserisci il nome del cliente di cui vuoi eliminare la prenotazione");
				    		System.out.println("oppure [A]nnulla");
				    		nomeD = input.nextLine();
				    		
				    		if(nomeD.equals("A"))
				    		{
				    			break;
				    		}
				    		
				    		indici = gestione.filtraSelezione(nomeD, 't');
				    		if(indici.size() == 0)
				    		{
				    			System.out.println("Nessuna prenotazione a questo nome");
				    		}
				    		else
				    		{
				    			do
				    			{
				    				System.out.println("Vuoi eliminare tutte le prenotazioni effettuate da "+ nomeD + " [S/N]?");
				    				canc = input.nextLine().toUpperCase().charAt(0);
				    				if(canc == 'S')
				    				{
				    					for(Prenotazione i : indici)
				    					{
				    						gestione.Elimina(i);
				    					}
				    				}
				    			}
				    			while(canc != 'S' && canc != 'N');
				    			System.out.println("Prenotazioni di: " + nomeD + " ELIMINATE ");
				    		}
				    }   
				    break;
				}
				while(tipo != '1' && tipo != '2' && tipo != '3');
				
				break;
			
			// CASO "Controlla la prima data disponibile"
			case 'C':
				
				/**Preparo due variabili per */
				String dataD = "";
				
				/**Valore booleano: assume valore TRUE qualora ci sia una una data disponibile*/
				boolean dataDFlag;
				
				/**Creo una nuova instanza della data come variabile per la verifica della disponibilità
				 * applico poi il metodo clear() per settare come "undefined" ogni campo dell'oggetto data*/
				Calendar dataInstanceD = Calendar.getInstance();
				dataInstanceD.clear();
				
				do
				{
					dataDFlag = false;
					System.out.println("Inserisci il giorno corrente [gg/mm/aaaa] oppure [A]nnulla : ");
					input.nextLine();
					dataD = input.nextLine();
					
					if(dataD.equals("A"))
					{
						break;
					}
					
					try
					{
						dataInstanceD.setTime(formatoSemplice.parse(dataD));	
						
						/**dataCorretta: controlla che il formato Stringa della data(metodo format)
						 * (lo metto in una variabile per renderne più chiara la lettura)*/
						boolean dataCorretta = formatoSemplice.format(dataInstanceD.getTime()).equals(dataD);
						
						if(!dataCorretta)
						{
							System.out.println("Data non esistente! ");
						}
						else
						{
							/**Verifico prima di tutto se una data è diponibile, mediante il
							 * metodo dateAvailable(Calendar)*/
							boolean disp = gestione.dateAvailable(dataInstanceD);

							/**Creo un'istanza Calendar e un'istanza Date*/
							Calendar disp2 = Calendar.getInstance();
							Date d = new Date();
							
							SimpleDateFormat formatoDataAV = new SimpleDateFormat ("dd/MM/yyyy");

							if(disp)
							{
								System.out.println("Data già con prenotazione!");
								
								/**Applico il metodo per la prima data disponibile*/
								disp2 = gestione.firstDate(dataInstanceD);
								
								/**Visualizza la data in formato stringa*/
								d = disp2.getTime();
								String primaDataAV = formatoDataAV.format(d);
								
								System.out.println("La prima data disponibile dal giorno corrente è: ");
								System.out.println(primaDataAV);
								
								dataDFlag = true;
							}
							else if(!disp)
							{
								System.out.println("Data disponibile!");
								dataDFlag = true;
							}
						}
					}
					catch (ParseException e)
					{
						System.out.println("Data in formato non corretto! ");
					}	
				}
				while(!dataDFlag);		
				break;	
				
			//CASO lista delle prenotazione filtrate per nome del cliente	
			case 'L':
				Vector<Prenotazione> idxFiltro = new Vector<Prenotazione>();
				System.out.println("Inserisci il nome del cliente per visualizzare le relative prenotazioni: ");
				System.out.println("(Inserisci [A] per annullare)");
				String clienteF = input.next();
				if(clienteF.equals("A"))
				{
					break;
				}
				
				idxFiltro = gestione.filtraSelezione(clienteF, 'p');
				
				/**Se il vettore è vuoto*/
				if(idxFiltro.isEmpty())
				{
					System.out.println("Nessuna prenotazione effettuata da questo cliente");
				}
				/**Per ogni indice applica il metodo void VisualizzaFiltro()*/
				else
				{
					gestione.VisualizzaFiltro(idxFiltro);
				}
				
			//CASO salva il file	
			case 'S':
				gestione.salvaFile(nomeFile);
				break;
				
			//CASO uscita	
			case 'U':
				if(gestione.getSavedIO())
				{
					char risp;
					System.out.println("Vuoi salvare le modifiche? [S/N]");
					risp = input.next().toUpperCase().charAt(0);
					try
					{
						if(risp == 'S')
					    {
							gestione.salvaFile(nomeFile);
							System.out.println("Modifiche salvate!");
					    }
					    else if (risp == 'N')
					    {
					    	break;
					    }
					}
					catch(InputMismatchException e)
					{
						System.out.println("Digita 'S' se vuoi salvare, 'N' altrimenti");
					}
				}  
			}
		}
		while(choose != 'U');
		System.out.println("Arrivederci! ");
		
	
	} //Parentesi che chiude il "main"
} //Parentesi che chiude la classe GestisciMain

	
	
	
	
	
	
	
	
	
	

