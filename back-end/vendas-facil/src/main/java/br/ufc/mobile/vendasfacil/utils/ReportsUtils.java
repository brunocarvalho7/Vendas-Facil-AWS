package br.ufc.mobile.vendasfacil.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsUtils {
	
	public static Date getDataAtual() {
		return new Date();
	}
	
	public static Date getInicioMes() {
		LocalDate aux = LocalDate.now().withDayOfMonth(1);
		
		return Date.from(aux.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static List<Date> getSemanaAnterior(){
		LocalDate aux = LocalDate.now().minusDays(7);
		
		List<Date> dates = new ArrayList<>();
		
		for(int i=0;i<=7;i++) {
			dates.add(Date.from(aux.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			aux = aux.plusDays(1);
		}
		
		return dates;
	}

}
