package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	List<Evento>soluzione;
	List<Evento> listaEventiPerNerc;
	//dichiaro una variabile che mi conta le persone coinvolte
	private int numPersone=0;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<Evento> listaEventi(Integer nercId,Integer anni, Integer oreMax){
		return podao.getListaEventi(nercId);
	}
	
	
	//creo metodo ricorsivo alla pressione del bottone worstCaseAnalysis
	
	public List<Evento> worstCaseAnalysis(Integer nercId,Integer anni, Integer oreMax){
		
		
		listaEventiPerNerc= new ArrayList<Evento>();
		this.soluzione= new ArrayList<Evento>();
		//passo la ricorsione solo a chi ha quel nerc id attraverso una nuova lista che "filtra"
		//richiamo il metodo in podao che ha tutta la lista Eventi per quel nercId (è implementato cosi visto che nel where ho il ?
		//perchè scrivo io il nercId
		this.listaEventiPerNerc= new ArrayList<>(podao.getListaEventi(nercId));
		System.out.println(listaEventiPerNerc.size());
		
		//ORDINO PER DATA
		//scrivo new Comparator<Evento>(){} ); e importo la classe
		//ppoi faccio aggiungere add unip impledments.... cosi lavoro direttamente qui
		Collections.sort(this.listaEventiPerNerc, new Comparator<Evento>() {

			@Override
			public int compare(Evento o1, Evento o2) {
				
				return o1.getDataInizio().compareTo(o2.dataInizio);
			}
			
		});
		//cosi non devo mettere nel cerca anche il nercId 
		//nel cerca metto i parametri che variano 
		cerca(new ArrayList<Evento>(), anni,oreMax);
		return soluzione;
		
	}

	private void cerca(List<Evento> parziale,Integer anni, Integer oreMax) {
		
		
		//CASO TERMINALE --> anni e ore sono stati raggiunti  
		//creo due funzioni apposta per evitare di svolgere tutti i controlli nell'if
		
		//if(calcoloMassimoAnni(parziale,anni) && calcoloMassimoOre(parziale,oreMax)) {
			//se sono vere essendo due funzioni booleane allora ho finito e mi fermo
			
		//return;
		
		//}
		// controllo su quante persone sono coinvolte nel blackout
		
		if(calcoloPersoneCoinvolte(parziale)>this.numPersone) {
			//se le persone coinvolte superano il massimo
			//aggiorniamo anche la soluzione a parziale se 
			this.numPersone = calcoloPersoneCoinvolte(parziale);
			this.soluzione= new ArrayList<>(parziale);
			
		}
		
			
		
		for(Evento e: this.listaEventiPerNerc) {
			
	
			//CONTROLLO CHE NON CI SIANO EVENTI DUPLICATI --> la sol parziale non deve contenere due eventi uguali
				//altrimenti ho stesso evento piu volte con diversi svolgimenti dell'algoritmo
			if(!parziale.contains(e)) {
			     //a parziale aggiungi l'elemento e poi tolgo quell'elemento

					parziale.add(e);
			     
		    if(calcoloMassimoAnni(parziale,anni) && calcoloMassimoOre(parziale,oreMax)) {
			     cerca(parziale,anni,oreMax);
			  }
			  parziale.remove(e);
		
            	}
		  
			}
	
}

	public int calcoloPersoneCoinvolte(List<Evento> parziale) {
		
		int conta=0;
		
		for(Evento e: parziale) {
			conta+=e.getCustomers_affected();
			
		}
		
		return conta;
	}


	private boolean calcoloMassimoAnni(List<Evento> parziale, Integer anni) {
		//prendo l'anno del primo e dell'ultimo elemento di parziale per vedere 
		// se la loro differenza è maggiore o minore del massimo
	
		//((GET(0))) è IL PRIMO ELEMENTO DI PARZIALE
		
	if(parziale.size()>=2) {  //numero di elementi --> size
	int annoInizio= parziale.get(0).getDataInizio().getYear();
	//ultimo elemento di parziale
	int annoFine= parziale.get(parziale.size()-1).getDataInizio().getYear();
			
	if((annoFine-annoInizio)>anni) {
		return false;
	}
	}
		return true;
	}
	
	private boolean calcoloMassimoOre(List<Evento> parziale, Integer oreMax) {
		//calcolo le ore massime dentro parziale a cui sono arrivato
		//uso il metodo calcola periodo che calcola per un solo evento
		//facnedo il for le ho per tutto
		int somma=0;
		
		for(Evento e: parziale) {
			somma+= calcolaPeriodo(e);
		}
		
		//se sono arrivato alle ore max ritorno true perchè devo uscire dalla ricorsione
		if(somma>oreMax)
			return false;
		
		return true;
	}

	
	public long calcolaPeriodo(Evento e) {
		// calcolo il periodo dell'evento (cioe blackout)
		long periodoOre=0;
		periodoOre=Duration.between(e.getDataInizio(), e.getDataFine()).toHours();
		return periodoOre;
	
	}
	
	//e' analogo al metodo booleano ma int perche cosi posso richiamarlo nel controller per l'output delle ore
	public int calcoloOreOutput(List<Evento>sol) {
		int somma=0;
		for(Evento e : sol) {
			somma+=calcolaPeriodo(e);
		}
		return somma;
	}
}
