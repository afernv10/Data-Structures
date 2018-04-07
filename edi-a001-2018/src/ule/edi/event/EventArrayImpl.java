package ule.edi.event;

import java.util.Date;

/**
 * 
 * Admite hasta 4 políticas diferentes de descuento.
 * 
 * @author profesor
 *
 */
public class EventArrayImpl implements Event {

	private String name;
	
	private Date date;
	
	private Ticket[] tickets;

	private double baseTicketPrice = 0.0;

	private DiscountPolicy discounts[] = new DiscountPolicy [ 4 ];
	
	public EventArrayImpl(String name, Date date, int ntickets, double baseTicketPrice) {
	
		this.name = name;
		this.date = date;
		this.tickets = new Ticket[ntickets];
		this.baseTicketPrice = baseTicketPrice;
	
	}
	
	
	@Override
	public String getName() {
		
		return name;
	}


	@Override
	public double getBaseTicketPrice() {
		
		return baseTicketPrice;
	}

	@Override
	public Date getDate() {
		
		return date;
	}


	@Override
	public int getSoldTickets() {
		int nSoldTickets = 0;
		
		for(int i = 0; i < tickets.length; i++){
			if(tickets[i] != null){
				nSoldTickets++;
			}
		}
		
		return nSoldTickets;
	}

	@Override
	public int getNumberOfTickets() {
		return tickets.length;	
	}

	@Override
	public int getAvailableTickets() {
		int nAvailableTickets = 0;
		
		for(int i = 0; i < tickets.length; i++){
			if(tickets[i] == null){
				nAvailableTickets++;
			}
		}
		
		return nAvailableTickets;
	}

	@Override
	public void refundTicket(int pos) {
		
		if(tickets[pos-1] != null){
			tickets[pos-1] = null;
		}
		
	}

	@Override
	public boolean sellTicket(int pos, Person p) {
		boolean isSold = false;
		
		if( (pos > 0) && (pos <= tickets.length) && (tickets[pos - 1] == null) ){
			isSold = true;
			tickets[pos - 1] = new Ticket(this, pos, p);
		}
			
		return isSold;
	}


	@Override
	public int getNumberOfAttendingChildren() {
		int n = 0;
		
		for(int i = 0; i < tickets.length; i++){
			if(tickets[i] != null && tickets[i].getHolder().age >= 0 && tickets[i].getHolder().age < Configuration.CHILDREN_EXMAX_AGE){
				n++;
			}
		}
		return n;
	}
	
	@Override
	public int getNumberOfAttendingAdults() {
		int n = 0;
		
		for(int i = 0; i < tickets.length; i++){
			if(tickets[i] != null && tickets[i].getHolder().age >= Configuration.CHILDREN_EXMAX_AGE && tickets[i].getHolder().age < Configuration.ELDERLY_PERSON_INMIN_AGE){
				n++;
			}
		}
		return n;
	}

	@Override
	public int getNumberOfAttendingElderlyPeople() {
		int n = 0;
		
		for(int i = 0; i < tickets.length; i++){
			if(tickets[i] != null && tickets[i].getHolder().age >= Configuration.ELDERLY_PERSON_INMIN_AGE){
				n++;
			}
		}
		return n;
	}

	@Override
	public double getTotalTicketValue() {
		
		double amount = 0.0;
		
		for(int i = 0; i < tickets.length; i++){
			if(tickets[i] != null){
				amount += getPrice(tickets[i]);
			}
		}

		
		return amount;
	}
	
	@Override
	public boolean addDiscountPolicy(DiscountPolicy px) {
		
		for(int i = 0; i < discounts.length; i++){
			if(discounts[i] != null){
				if (discounts[i].equals(px)){
					return false;
				}
			}	
		}
		// llega hasta aqui porque sale del for, quiere decir que no hay niguna igual
		for(int i = 0; i < discounts.length; i++){
			if(discounts[i] == null){
				discounts[i] = px;
				return true;
			}
		}
	
		return false;
	}
	
	public Ticket getTicket(int pos) {
		// metodo creado para acceder a los tickets, ya que estaban en private
		return tickets[pos - 1];
	}
	
	@Override
	public double getPrice(Ticket t) {
		
		double maxDiscount = 0.0;
		for(int i = 0; i < discounts.length; i++){
			if(discounts[i] != null && discounts[i].getDiscount(t) > maxDiscount){
				maxDiscount = discounts[i].getDiscount(t);
			}
		}
		return baseTicketPrice * (1.00 - maxDiscount);
	}


	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("{");
		buffer.append("'Name':'" + getName() + "', 'Date':'" + getDate() + "', 'Ticket':" + getBaseTicketPrice());
		buffer.append(", 'Tickets':");
		
		buffer.append("[");
		
		boolean isEmpty = true;
		for (Ticket t : tickets) {
			if (t != null) {
				isEmpty = false;
				buffer.append(t + ", ");
			}
		}
		if (! isEmpty) {
			buffer.delete(buffer.length() - 2, buffer.length());
		}
		buffer.append("]");
		
		buffer.append("}");
		
		return buffer.toString();
	}

	
}
