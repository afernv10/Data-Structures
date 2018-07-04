package ule.edi.event;

public interface DiscountPolicy {
	
	/**
	 * Calcula el descuento para un ticket.
	 * 
	 * Será un valor en [0.0, 1.0]; por ejemplo, 0.20 es un
	 * descuento del 20% sobre el precio base del ticket.
	 * 
	 * @param t
	 * @return
	 */
	public double getDiscount(Ticket t);

}
