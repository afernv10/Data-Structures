package ule.edi.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class EventArrayImplTests {

	private DateFormat dformat = null;
	private EventArrayImpl e;
	private Person p1;
	private Person p2;
	private AgeDiscountPolicy adp1;
	private AgeDiscountPolicy adp2;
	private OccupationalDiscountPolicy odp1;
	private OccupationalDiscountPolicy odp2;
	
	private Date parseLocalDate(String spec) throws ParseException {
        return dformat.parse(spec);
	}

	public EventArrayImplTests() {
		
		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Before
	public void setUp() throws Exception {
		this.e = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 100, 10.0);
		this.p1 = new Person("Alice", 34, "Teacher");
		this.p2 = new Person("Judy", 7);
		
		// discounts
		this.adp1 = new AgeDiscountPolicy(e);
		adp1.setAgeRange(2, 9);
		adp1.setDiscount(0.5);
		this.adp2 = new AgeDiscountPolicy(e);
		adp2.setAgeRange(40, 60);
		adp2.setDiscount(0.7);
		
		this.odp1 = new OccupationalDiscountPolicy(e, "Police");
		odp1.setDiscount(0.3);
		this.odp2 = new OccupationalDiscountPolicy(e, "Teacher");
		odp2.setDiscount(0.2);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getUnderPositionTicketTest() throws Exception {
		e.getTicket(-1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getZeroPositionTicketTest() throws Exception {
		e.getTicket(0);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getHigherPositionTicketTest() throws Exception {
		e.getTicket(101);
	}
	
	@Test
	public void getTicketTest() {
		Ticket uno = new Ticket(e, 1, p1);
		Assert.assertEquals(1, uno.getPosition());
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertEquals(uno.getEvent(), e.getTicket(1).getEvent());
		Assert.assertEquals(uno.getHolder(), e.getTicket(1).getHolder());
		Assert.assertEquals(uno.toString(), e.getTicket(1).toString());
	}
	
	@Test
	public void getNameTest() throws Exception {
		Assert.assertEquals("The Fabulous Five", e.getName());
	}
	
	@Test
	public void getBaseTicketPriceTest() throws Exception {
		Assert.assertEquals(10.0, e.getBaseTicketPrice(), 0.0f);
	}
	
	@Test
	public void getDateTest() throws Exception {
		Assert.assertEquals(parseLocalDate("24/02/2018 17:00:00"), e.getDate());
	}
	
	@Test
	public void getSoldTicketsTest() {
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertTrue(e.sellTicket(2, p2));
		
		Assert.assertEquals(2, e.getSoldTickets());
	}
	
	@Test
	public void getNumberOfTicketsTest() {
		Assert.assertEquals(100, e.getNumberOfTickets());
	}
	
	@Test
	public void availableAllTicketsTest() {
		
		for(int i = 1; i <= e.getNumberOfTickets(); i++){
			Assert.assertNull(e.getTicket(i));
		}
		// no se ha vendido ningun ticket todavia
		Assert.assertEquals(e.getNumberOfTickets(), e.getAvailableTickets());
	}
	
	@Test
	public void getAvailableTicketsSellingTest() {
		
		// vendemos 2 tickets
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertTrue(e.sellTicket(2, p2));
		Assert.assertEquals(98, e.getAvailableTickets());
		
		// vendemos otro mas
		Assert.assertTrue(e.sellTicket(3, new Person("Gary", 23, "DJ")));
		Assert.assertEquals(97, e.getAvailableTickets());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void refundNegativeTest() throws Exception {
		e.refundTicket(-1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void refundZeroTest() throws Exception {
		e.refundTicket(0);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void refundOutOfTest() throws Exception {
		e.refundTicket(101);
	}

	@Test
	public void refundTicketTest() {
		
		// devolvemos un ticket no vendido, no hace nada
		e.refundTicket(1);
		// podemos vender porque era null
		Assert.assertNull(e.getTicket(1));
		Assert.assertTrue(e.sellTicket(1, p1));
		
		// intentamos vender el ticket en la misma pos de antes, no vamos a poder
		Assert.assertFalse(e.sellTicket(1,  p1));
		
		// intentamos vender ticket en otra pos
		Assert.assertTrue(e.sellTicket(2,  p1));
		
		// intentamos devolver el ticket vendido
		e.refundTicket(1);
		
		// intentamos vender el ticket otra vez, ahora si porque ya lo devolvimos
		Assert.assertTrue(e.sellTicket(1,  p1));
	}
	
	@Test
	public void sellTicketTest() {
		Assert.assertNull(e.getTicket(1));
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertNotNull(e.getTicket(1));
	}
	
	@Test
	public void sellSameTicketTest() {
		Assert.assertNull(e.getTicket(1));
		Assert.assertTrue(e.sellTicket(1, p1));
		// probamos a vender la misma posicion a otro
		Assert.assertFalse(e.sellTicket(1, p2));
		Assert.assertNotNull(e.getTicket(1));
		// si quiere otra posicion si puede
		Assert.assertTrue(e.sellTicket(2, p2));
	}
	
	
	@Test
	public void sellTicketZeroTest() {
		Assert.assertFalse(e.sellTicket(0, p1));
	}
	
	@Test
	public void sellTicketNegativeTest() {
		Assert.assertFalse(e.sellTicket(-1, p1));
	}
	
	@Test
	public void sellTicketUpperLimitTest() {
		// ticket 100 en este caso e
		Assert.assertTrue(e.sellTicket(e.getNumberOfTickets(), p2));
	}
	
	@Test
	public void sellTicketOutOfRangeTest() {
		// posicion por encima de los posibles tickets (ticket 101 en este caso no existe)
		Assert.assertFalse(e.sellTicket(e.getNumberOfTickets() + 1, p2));
	}
	
	@Test
	public void getNumberOfAttendingChildren() {
		// p2 age = 7
		Assert.assertTrue(e.sellTicket(1, p2));
		Assert.assertEquals(1, e.getNumberOfAttendingChildren());
		Assert.assertTrue(e.sellTicket(2, new Person("July", 0)));
		Assert.assertTrue(e.sellTicket(3, new Person("Jules", 2)));
		Assert.assertTrue(e.sellTicket(4, new Person("Margaret", 13)));
		Assert.assertEquals(4, e.getNumberOfAttendingChildren());
		// p1 age = 37
		Assert.assertTrue(e.sellTicket(5, p1));
		Assert.assertEquals(4, e.getNumberOfAttendingChildren());
	}
	
	@Test
	public void getNumberOfAttendingAdultsTest() {
		
		// p2 age = 7
		Assert.assertTrue(e.sellTicket(1, p2));
		Assert.assertEquals(0, e.getNumberOfAttendingAdults());
		// 14- es child
		Assert.assertTrue(e.sellTicket(2, new Person("Pepe", 14)));
		Assert.assertEquals(1, e.getNumberOfAttendingAdults());
		// 65+ es elderly
		Assert.assertTrue(e.sellTicket(3, new Person("Pepa", 64)));
		Assert.assertEquals(2, e.getNumberOfAttendingAdults());
		// p1 age = 37
		Assert.assertTrue(e.sellTicket(4, p1));
		Assert.assertEquals(3, e.getNumberOfAttendingAdults());
			
	}
	
	@Test
	public void getNumberOfAttendingElderlyPeopleTest() {
		// 64 = not elderly 
		Assert.assertTrue(e.sellTicket(1, new Person("Paco", 64)));
		Assert.assertEquals(0, e.getNumberOfAttendingElderlyPeople());
		
		Assert.assertTrue(e.sellTicket(2, new Person("Leovigildo", 88)));
		Assert.assertEquals(1, e.getNumberOfAttendingElderlyPeople());
		
		Assert.assertTrue(e.sellTicket(3, new Person("Leo", 65)));
		Assert.assertEquals(2, e.getNumberOfAttendingElderlyPeople());
	}
	
	@Test
	public void  totalPriceWithoutDiscountsTest() {
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertEquals(e.getBaseTicketPrice(), e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void totalPriceAgeDiscountNotApplyTest() {
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertEquals(e.getBaseTicketPrice(), e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void totalPriceOccupationalDiscountNotApplyTest() {
		Assert.assertTrue(e.addDiscountPolicy(odp1));
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertEquals(e.getBaseTicketPrice(), e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void totalPriceOccupationalDiscountTest() {
		Assert.assertTrue(e.addDiscountPolicy(odp2));
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertEquals(8.0, e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void totalPriceAgeDiscountTest() {
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertTrue(e.sellTicket(1, p2));
		Assert.assertEquals(5.0, e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void totalPriceDiscountsNotAppliedTest() {
		Assert.assertTrue(e.sellTicket(1, new Person("Kate", 9)));
		Assert.assertTrue(e.sellTicket(2, new Person("Joe", 1)));
		Assert.assertTrue(e.sellTicket(3, new Person("Carol", 60, "Designer")));
		Assert.assertTrue(e.sellTicket(4, new Person("Hugo", 20, "Writer")));
		Assert.assertTrue(e.sellTicket(5, new Person("Martin", 39, "Academy teacher")));
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertTrue(e.addDiscountPolicy(adp2));
		Assert.assertTrue(e.addDiscountPolicy(odp1));
		Assert.assertTrue(e.addDiscountPolicy(odp2));
		
		Assert.assertEquals(50, e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void totalPriceDiscountsAppliedTest() {
		Assert.assertTrue(e.sellTicket(1, new Person("Kate", 8)));
		Assert.assertTrue(e.sellTicket(2, new Person("Joe", 2)));
		Assert.assertTrue(e.sellTicket(3, new Person("Carol", 60, "Police")));
		Assert.assertTrue(e.sellTicket(4, new Person("Hugo", 20, "Teacher")));
		Assert.assertTrue(e.sellTicket(5, new Person("Martin", 40, "Academy teacher")));
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertTrue(e.addDiscountPolicy(adp2));
		Assert.assertTrue(e.addDiscountPolicy(odp1));
		Assert.assertTrue(e.addDiscountPolicy(odp2));
		
		Assert.assertEquals(28, e.getTotalTicketValue(), 0.0);
	}
	
	
	@Test
	public void maxDiscountOccupationTest() {
		OccupationalDiscountPolicy odp3 = new OccupationalDiscountPolicy(e, "Teacher");
		odp3.setDiscount(1.0);
		Assert.assertTrue(e.addDiscountPolicy(odp2));
		Assert.assertTrue(e.addDiscountPolicy(odp3));
		Assert.assertTrue(e.sellTicket(1, p1));
		Assert.assertEquals(0.0, e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void maxDiscountAgeTest() {
		AgeDiscountPolicy adp3 = new AgeDiscountPolicy(e);
		adp3.setAgeRange(7, 21);
		adp3.setDiscount(1.0);
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertTrue(e.addDiscountPolicy(adp3));
		Assert.assertTrue(e.sellTicket(1, p2));
		Assert.assertEquals(0.0, e.getTotalTicketValue(), 0.0);
	}
	
	@Test
	public void discountsFullTest() {
		AgeDiscountPolicy adp3 = new AgeDiscountPolicy(e);
		adp3.setAgeRange(11, 18);
		adp3.setDiscount(0.2);
		AgeDiscountPolicy adp4 = new AgeDiscountPolicy(e);
		adp3.setAgeRange(11, 18);
		adp3.setDiscount(0.2);
		OccupationalDiscountPolicy odp5 = new OccupationalDiscountPolicy(e, "Plumber");
		odp5.setDiscount(0.4);
		OccupationalDiscountPolicy odp6 = new OccupationalDiscountPolicy(e, "Singer");
		odp6.setDiscount(0.6);
		
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertTrue(e.addDiscountPolicy(adp2));
		Assert.assertTrue(e.addDiscountPolicy(odp1));
		Assert.assertTrue(e.addDiscountPolicy(odp2));
		Assert.assertFalse(e.addDiscountPolicy(adp3));
		Assert.assertFalse(e.addDiscountPolicy(adp4));
		Assert.assertFalse(e.addDiscountPolicy(odp5));
		Assert.assertFalse(e.addDiscountPolicy(odp6));
	}
	
	@Test
	public void equalAgeDiscountsTest() {
		AgeDiscountPolicy adpE = new AgeDiscountPolicy(e);
		adpE.setAgeRange(2, 9);
		adpE.setDiscount(0.5);
		
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertFalse(e.addDiscountPolicy(adp1));
		Assert.assertTrue(adp1.equals(adpE));
		Assert.assertFalse(e.addDiscountPolicy(adpE));
	}
	
	
	@Test
	public void equalOccupationalDiscountsTest() {
		OccupationalDiscountPolicy odpE = new OccupationalDiscountPolicy(e, "Police");
		odpE.setDiscount(0.3);
		
		Assert.assertTrue(e.addDiscountPolicy(odp1));
		Assert.assertFalse(e.addDiscountPolicy(odp1));
		Assert.assertFalse(e.addDiscountPolicy(odpE));
	}
	
	
	@Test
	public void getPriceWithoutDiscountsTest() {
		Ticket t1 = new Ticket(e, 1, p1);
		Assert.assertEquals(10.0, e.getPrice(t1), 0.0);
	}
	
	@Test
	public void getPriceAgeDiscountTest() {
		Ticket t2 = new Ticket(e, 1, p2);
		Assert.assertTrue(e.addDiscountPolicy(adp1));
		Assert.assertEquals(5.0, e.getPrice(t2), 0.0);
	}
	
	@Test
	public void  getPriceOccupationalDiscountTest() {
		Ticket t1 = new Ticket(e, 1, p1);
		Assert.assertTrue(e.addDiscountPolicy(odp2));
		Assert.assertEquals(8.0,  e.getPrice(t1), 0.0);
	}
	
	@Test
	public void occupationPersonNullTest() {
		Person pNull = new Person("Liam", 23);
		Assert.assertNull(pNull.occupation);
		System.out.println(pNull.toString());
		Assert.assertEquals("{'Name':'Liam', 'Age':23, 'Occupation':'n/a'}", pNull.toString());
	}
	
	@Test
	public void  toStringWithoutTicketsTest() {
		// without tickets in array
		Assert.assertEquals("{'Name':'The Fabulous Five', 'Date':'Sat Feb 24 17:00:00 CET 2018', 'Ticket':10.0, 'Tickets':[]}", e.toString());
	}
	
	@Test
	public void toStringWithTicketsTest() {
		Assert.assertTrue(e.sellTicket(1, p1));
		// with tickets
		Assert.assertEquals("{'Name':'The Fabulous Five', 'Date':'Sat Feb 24 17:00:00 CET 2018', 'Ticket':10.0, 'Tickets':[{'Event':'The Fabulous Five', 'Position':1, 'Holder':{'Name':'Alice', 'Age':34, 'Occupation':'Teacher'}, 'Price':10.0}]}", e.toString());
	}
	
}
