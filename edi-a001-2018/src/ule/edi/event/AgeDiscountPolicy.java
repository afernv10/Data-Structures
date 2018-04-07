package ule.edi.event;

public class AgeDiscountPolicy implements DiscountPolicy {

	private int inmin;
	
	private int exmax;
	
	private double value;
	
	public AgeDiscountPolicy(Event e) {

	}
	
	public void setAgeRange(int inmin, int exmax) {
		this.inmin = inmin;
		this.exmax = exmax;
	}
	
	public void setDiscount(double value) {
		this.value = value;
	}
	
	@Override
	public double getDiscount(Ticket t) {
		
		double discount = 0.0;
		
		if ((inmin <= t.getHolder().age) && (t.getHolder().age < exmax)) {
			discount = value;
		}
		
		return discount;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof AgeDiscountPolicy) {
			
			AgeDiscountPolicy other = (AgeDiscountPolicy) obj;
			
			return (inmin == other.inmin) && (exmax == other.exmax) && (value == other.value);
		}
		
		return false;
	}
	
}
