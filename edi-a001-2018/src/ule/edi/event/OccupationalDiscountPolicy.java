package ule.edi.event;

public class OccupationalDiscountPolicy implements DiscountPolicy {

	private String pattern;
	
	private double value = 0.0;
	
	public OccupationalDiscountPolicy(Event e, String pattern) {
		 
		this.pattern = pattern;
	}
	
	public void setDiscount(double v) {
		this.value = v;
	}
	
	@Override
	public double getDiscount(Ticket t) {
		double discount = 0.0;
		// Si la persona que tiene ese ticket tiene profesión (no es null) y coincide con el patrón, se aplica el descuento (value)
		if(t.getHolder().occupation != null && t.getHolder().occupation.equals(pattern)){
			discount = value;
		}
		return discount;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		// Dos políticas son iguales si el patrón y el valor son iguales.
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof OccupationalDiscountPolicy) {
			
			OccupationalDiscountPolicy other = (OccupationalDiscountPolicy) obj;
			
			return (pattern == other.pattern) && (value == other.value);
		}
		
		return false;
	}
	
}
