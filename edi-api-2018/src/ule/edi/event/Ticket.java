package ule.edi.event;

public class Ticket {

	private int position;
	
	private Person holder;
	
	private Event event;
	
	public Ticket(Event event, int position, Person holder) {
		
		this.position = position;
		this.holder = holder;
		this.event = event;
	}
	
	public int getPosition() {
		return position;
	}
	
	public Person getHolder() {
		return holder;
	}
	
	public Event getEvent() {
		return event;
	}

	@Override
	public String toString() {
		return "{'Event':'" + event.getName() + "', 'Position':" + position + ", 'Holder':" + holder + ", 'Price':" + event.getPrice(this) + "}";
	}
	
}
