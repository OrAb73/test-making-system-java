package shift;

import java.sql.Array;
import java.time.LocalDateTime;

public class Shift implements Comparable<Shift> {
	
	private int numOfHairDressers;
	private HairDresser[] currentHairDressers = new HairDresser[numOfHairDressers];
	private LocalDateTime date;
	private int hour;
	private int minutes;
	private int numOfAppointments;
	private Appointment[] scheduledAppointments = new Appointment[numOfAppointments];
	private float profits;
	
	public Shift (HairDresser[] currentHairDressers, LocalDateTime date, int hour, int minutes, Treatment[] scheduledTreatments)  throws Exception {
		this.currentHairDressers = currentHairDressers.clone();
		Array.sort(currentHairDressers, new CompareTreatmentByHour());
		this.numOfHairDressers = currentHairDressers.length;
		this.date = date;
		setMinutes(minutes);
		setHour(hour);
		this.scheduledAppointments = scheduledTreatments.clone();
		this.numOfAppointments = scheduledTreatments.length;
		profits = getProfits();
		
	}
	
	public void setMinutes(int minutes) throws Exception {
		if (minutes < 0 || minutes >= 60)
			throw new Exception("Minutes have to be between 0-59");
		else 
			this.minutes = minutes;
	}
	
	public void setHour(int hour) throws Exception {
		if (hour < 0 || hour >= 24)
			throw new Exception("Hours have to be between 0-23");
		else 
			this.hour = hour;
	}
	
	
	public HairDresser[] getCurrentHairDressers() {
		return currentHairDressers;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public String getTimeOfShift() {
		return hour+":"+minutes;
	}
	
	public Treatment[] getScheduledTreatments() {
		return scheduledTreatments;
	}
	
	public float getProfits() {
		
		float profits = 0; //Adult array abs
		int expenses = 0;
	
		for (int i = 0; i < numOfTreatments; i++) {
			if (scheduledTreatments[i] instanceof coffeeAndPastryable) {
				expenses += 10;
			}
			
			profits += scheduledTreatments[i].getPrice;
		}
		
		for (int i = 0; i < numOfHairDressers; i++) {
			if (currentHairDressers[i].getCoffeeAndPastry == true) {
				expenses += 10;
			}
		}
		
		return profits-expenses;
		
	}
	
	
	
	
	
	

}
