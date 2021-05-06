package it.polito.tdp.poweroutages.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Evento {
	LocalDateTime dataInizio;
	LocalDateTime dataFine;
	Integer event_id;
	Integer nerc_id;
	Integer customers_affected;
	
	

public Evento(LocalDateTime dataInizio,LocalDateTime dataFine, Integer event_id, Integer nerc_id, Integer customers_affected) {
		super();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.event_id = event_id;
		this.nerc_id = nerc_id;
		this.customers_affected = customers_affected;
	}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((event_id == null) ? 0 : event_id.hashCode());
	return result;
}



@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Evento other = (Evento) obj;
	if (event_id == null) {
		if (other.event_id != null)
			return false;
	} else if (!event_id.equals(other.event_id))
		return false;
	return true;
}



public LocalDateTime getDataInizio() {
	return dataInizio;
}



public void setDataInizio(LocalDateTime dataInizio) {
	this.dataInizio = dataInizio;
}



public LocalDateTime getDataFine() {
	return dataFine;
}



public void setDataFine(LocalDateTime dataFine) {
	this.dataFine = dataFine;
}



public Integer getEvent_id() {
	return event_id;
}



public void setEvent_id(Integer event_id) {
	this.event_id = event_id;
}



public Integer getNerc_id() {
	return nerc_id;
}



public void setNerc_id(Integer nerc_id) {
	this.nerc_id = nerc_id;
}



public Integer getCustomers_affected() {
	return customers_affected;
}



public void setCustomers_affected(Integer customers_affected) {
	this.customers_affected = customers_affected;
}

	
	
	

	

	
}
