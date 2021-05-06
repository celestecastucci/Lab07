package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Evento;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	/**
	 * CREO UN METODO CHE RESTITUISCA GLI EVENTI PER QUEL DATO NERC ID
	 * MI CREO LA CLASSE EVENTO NEL MODEL CHE TENGA TRACCIA DI DATA INIZIO, FINE, ID EVENTO E ID NERC
	 * @param nercId
	 * @return
	 */
	public List<Evento> getListaEventi(Integer nercId){
		 String sql="SELECT customers_affected, date_event_began AS dataInizio, date_event_finished AS dataFine, id AS idEvento "
				+ "FROM poweroutages "
				+ "WHERE nerc_id=? ";
		List<Evento> listaEventi = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,nercId);
			ResultSet res = st.executeQuery();
		
			
//TIMESTAMP PER 
			while (res.next()) {
				Evento e= new Evento(res.getTimestamp("dataInizio").toLocalDateTime(),res.getTimestamp("dataFine").toLocalDateTime(), nercId, res.getInt("idEvento"), res.getInt("customers_affected"));
				listaEventi.add(e);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return listaEventi;
		
	}
}
