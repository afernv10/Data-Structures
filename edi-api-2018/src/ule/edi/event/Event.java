package ule.edi.event;

import java.util.Date;

public interface Event {

	public String getName();
	
	public Date getDate();

	
	/**
	 * Calcula el número de tickets vendidos del evento.
	 * 
	 * @return
	 */
	public int getSoldTickets();
	
	/**
	 * Número de tickets disponibles al inicio del evento.
	 * 
	 * @return
	 */
	public int getNumberOfTickets();

	/**
	 * Calcula el número de tickets disponibles (no vendidos).
	 * 
	 * @return
	 */
	public int getAvailableTickets();

	/**
	 * Devuelve el ticket en la posición dada.
	 * 
	 * Las posiciones empiezan en '1'.
	 * 
	 * @param pos
	 * @return
	 * @throws IndexOutOfBoundsException si la posición no es válida.
	 */
	public Ticket getTicket(int pos);
	

	/**
	 * Elimina el ticket de la posición dada. 
	 * 
	 * Las posiciones empiezan en '1'.
	 * 
	 * @param pos
	 * @throws IndexOutOfBoundsException si la posición no es válida.
	 */
	public void refundTicket(int pos);

	/**
	 * 
	 * Si el ticket de esa posición ya está vendido, no hace nada.
	 * 
	 * Las posiciones empiezan en '1'.
	 * 
	 * El valor de retorno indica si se pudo realizar la venta del ticket.
	 * 
	 * @param pos
	 * @param p
	 * @return
	 */
	public boolean sellTicket(int pos, Person p);
	

	/**
	 * Calcula el número de niños asistentes al evento.
	 * 
	 * 	[0, Configuration.CHILDREN_EXMAX_AGE)
	 * 
	 * abierto por la derecha.
	 * 
	 * @return
	 */
	public int getNumberOfAttendingChildren();
	
	/**
	 * Calcula el número de adultos asistentes al evento.
	 * 
	 * 	[Configuration.CHILDREN_EXMAX_AGE, Configuration.ELDERLY_PERSON_INMIN_AGE)
	 * 
	 * abierto por la derecha.
	 * 
	 * @return
	 */
	public int getNumberOfAttendingAdults();

	/**
	 * Calcula el número de ancianos asistentes al evento.
	 * 
	 * 	[Configuration.ELDERLY_PERSON_INMIN_AGE, Integer.MAX_VALUE)
	 * 
	 * abierto por la derecha.
	 *  
	 * @return
	 */
	public int getNumberOfAttendingElderlyPeople();


	/**
	 * Intenta añadir una política de descuento al evento.
	 * 
	 * Si ya está presente una política igual, o no hay sitio, no hace nada.
	 * 
	 * El valor de retorno indica si se modificaron las políticas del evento.
	 * 
	 * @param px
	 * @return <code>true</code> si se modificaron las políticas del evento
	 */
	public boolean addDiscountPolicy(DiscountPolicy px);


	/**
	 * Calcula el valor de todos los tickets vendidos.
	 * @return
	 */
	public double getTotalTicketValue();
	
	/**
	 * Devuelve el precio base de un ticket del evento
	 *
	 * @return
	 */
	public double getBaseTicketPrice();	
	
	/**
	 * Calcula el precio de un ticket en particular.
	 * 
	 * Debe aplicar las políticas de descuento para encontrar el
	 * máximo descuento posible. 
	 * 
	 * @param t
	 * @return
	 */
	public double getPrice(Ticket t);

}
