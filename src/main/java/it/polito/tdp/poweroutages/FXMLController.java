/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Evento;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	
    	txtResult.clear();
    	//gli passiamo cio che mette l'utente
    	Nerc nerc= cmbNerc.getSelectionModel().getSelectedItem();
    	String anni= txtYears.getText();
    	String oreMax= txtHours.getText();
    	
    	try {
    		
    		if(nerc!=null) {
    		int anniInput= Integer.parseInt(anni);
    		int oreMaxInput=  Integer.parseInt(oreMax);
    		
    		if(anniInput<=0 || oreMaxInput<=0)
    			return;
    		
    		List<Evento> worstCaseAnalysis= new ArrayList<>(this.model.worstCaseAnalysis(nerc.getId(), anniInput, oreMaxInput));
         
    		int numeroPersone= this.model.calcoloPersoneCoinvolte(worstCaseAnalysis);
    		int numeroOre= this.model.calcoloOreOutput(worstCaseAnalysis);
    		//append aggiungo al testo che c'è gia quindi se ho piu righe mi serve append
    		txtResult.appendText("Persone coinvolte: "+numeroPersone+ "\n");
    		txtResult.appendText("Ore totali : "+numeroOre+ "\n");
    		
    		for(Evento e: worstCaseAnalysis) {
    		 txtResult.appendText(e.getDataInizio().getYear()+" "+e.getDataInizio()+" "+e.getDataFine()+" "
    		    +this.model.calcolaPeriodo(e)+" "+e.getCustomers_affected()+" \n");
    		 
    		
    		
    		}
    		}
    		
    		} catch(NumberFormatException ne) {
    			txtResult.setText("ERRORE FORMATI NUMERI");
    			return;
    		
    	}
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Nerc>listaNerc= this.model.getNercList();
    	cmbNerc.getItems().addAll(listaNerc);
    	}
    
}
