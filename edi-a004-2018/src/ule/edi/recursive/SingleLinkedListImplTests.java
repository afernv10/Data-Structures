package ule.edi.recursive;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SingleLinkedListImplTests {

	private SingleLinkedList<Number> lN;
	
	private SingleLinkedList<Number> lN123;

	private SingleLinkedListImpl<String> lS;
	
	private SingleLinkedListImpl<String> lSABC;
	
	private StackImpl<String> S;
	private StackImpl<String> SABC;
	
	private <T> SingleLinkedListImpl<T> listWith(T ...ts) {
		return new SingleLinkedListImpl<T>(ts);
	}
	
	private <T> SingleLinkedListImpl<T> listWith(Iterator<T> i) {
		SingleLinkedListImpl<T> rx = new SingleLinkedListImpl<T>();
		while (i.hasNext()) {
			rx.addToRear(i.next());
		}
		return rx;
	}
	
	private <T> StackImpl<T> stackWith(T ...ts) {
		return new StackImpl<T>(ts);
	}
	
	private static Comparator<String> cmpf01 = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return (o1.compareTo(o2));
		}
	};

	private static Comparator<String> cmpf02 = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return Integer.compare(o1.length(), o2.length());
		}
	};

	@Before
	public void setUp() {
		this.lS = new SingleLinkedListImpl<String>();
		this.lSABC = new SingleLinkedListImpl<String>("A", "B", "C");
		
		this.lN = new SingleLinkedListImpl<Number>();
		this.lN123 = new SingleLinkedListImpl<Number>(1, 2, 3);
		
		S = stackWith();
		SABC = stackWith("A", "B", "C");
	}
	
	@Test
	public void testExample() {		
	
		Assert.assertEquals("[A]", listWith("A", "A", "A", "A").removeConsecutiveDuplicates().toString());
	}
	
	@Test
	public void testAddToRear(){
		lS.addToRear("A");
		Assert.assertEquals("[A]", lS.toString());
		lS.addToRear("X");
		Assert.assertEquals("[A, X]", lS.toString());
	}
	
	@Test
	public void testLoadIntoStacks(){
		StackImpl<String> forwards = new StackImpl<String>();
		StackImpl<String> backwards = new StackImpl<String>();
		lSABC.loadIntoStacks(forwards, backwards);
		
		Assert.assertEquals("[A, B, C]>", forwards.toString());
		Assert.assertEquals("[C, B, A]>", backwards.toString());
	}
	
	@Test
	public void testLoadIntoStacksEmptyList(){
		StackImpl<String> forwards = new StackImpl<String>();
		StackImpl<String> backwards = new StackImpl<String>();
		lS.loadIntoStacks(forwards, backwards);
		
		Assert.assertEquals("[]>", forwards.toString());
		Assert.assertEquals("[]>", backwards.toString());
	}
	
	@Test
	public void testSameContentsOneElement(){
		StackImpl<String> a = stackWith("A");
		StackImpl<String> b = stackWith("A");
		Assert.assertEquals("[A]>", a.toString());
		Assert.assertEquals("[A]>", b.toString());
		Assert.assertTrue(SingleLinkedListImpl.sameContents(a, b));
		Assert.assertEquals("[A]>", a.toString());
		Assert.assertEquals("[A]>", b.toString());
		
	}
	
	@Test
	public void testSameContentsOneElementDiff(){
		StackImpl<String> a = stackWith("A");
		StackImpl<String> b = stackWith("X");
		Assert.assertEquals("[A]>", a.toString());
		Assert.assertEquals("[X]>", b.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(a, b));
		Assert.assertEquals("[A]>", a.toString());
		Assert.assertEquals("[X]>", b.toString());
	}
	
	@Test
	public void testSameContentsVarious(){
		StackImpl<String> a = stackWith("A", "B", "D", "G");
		StackImpl<String> b = stackWith("A", "B", "E", "G");
		Assert.assertEquals("[A, B, D, G]>", a.toString());
		Assert.assertEquals("[A, B, E, G]>", b.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(a, b));
		Assert.assertEquals("[A, B, D, G]>", a.toString());
		Assert.assertEquals("[A, B, E, G]>", b.toString());
	}
	
	@Test
	public void testSameContents(){
		StackImpl<String> b = stackWith("A", "B", "C");
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertEquals("[A, B, C]>", b.toString());
		Assert.assertTrue(SingleLinkedListImpl.sameContents(SABC, b));
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertEquals("[A, B, C]>", b.toString());
	}
	
	@Test
	public void testNotSameContents(){
		StackImpl<String> a = new StackImpl<String>("A", "V", "C");
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertEquals("[A, V, C]>", a.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(a, SABC));
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertEquals("[A, V, C]>", a.toString());
		
		StackImpl<String> c = new StackImpl<String>("A", "B");
		StackImpl<String> d = new StackImpl<String>("A");
		Assert.assertEquals("[A, B]>", c.toString());
		Assert.assertEquals("[A]>", d.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(c, d));
		Assert.assertEquals("[A, B]>", c.toString());
		Assert.assertEquals("[A]>", d.toString());
		
		Assert.assertEquals("[A, B]>", c.toString());
		Assert.assertEquals("[A]>", d.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(d, c));
		Assert.assertEquals("[A, B]>", c.toString());
		Assert.assertEquals("[A]>", d.toString());
	}
	
	@Test
	public void testSameContentsDifferentProf(){
		StackImpl<String> dos = new StackImpl<String>("X", "Z");
		Assert.assertEquals("[X, Z]>", dos.toString());
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(dos, SABC));
		Assert.assertEquals("[X, Z]>", dos.toString());
		Assert.assertEquals("[A, B, C]>", SABC.toString());
	}
	
	@Test
	public void testSameContentsDifferentProf2(){
		StackImpl<String> dos = new StackImpl<String>("X");
		SABC.push("D");
		Assert.assertEquals("[X]>", dos.toString());
		Assert.assertEquals("[A, B, C, D]>", SABC.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(dos, SABC));
		Assert.assertEquals("[X]>", dos.toString());
		Assert.assertEquals("[A, B, C, D]>", SABC.toString());
	}
	
	@Test
	public void testSameContentsDifferentProf3(){
		StackImpl<String> a = new StackImpl<String>("BA", "A");
		StackImpl<String> b = new StackImpl<String>("A");
		
		Assert.assertEquals("[BA, A]>", a.toString());
		Assert.assertEquals("[A]>", b.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(a, b));
		Assert.assertEquals("[BA, A]>", a.toString());
		Assert.assertEquals("[A]>", b.toString());
	}
	
	@Test
	public void testSameContentsEmptyCasesA(){
		StackImpl<String> a = new StackImpl<String>();
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertEquals("[]>", a.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(a, SABC));
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertEquals("[]>", a.toString());
	}
	
	@Test
	public void testSameContentsEmptyCasesB(){
		Assert.assertEquals("[]>", S.toString());
		Assert.assertEquals("[A, B, C]>", SABC.toString());
		Assert.assertFalse(SingleLinkedListImpl.sameContents(SABC, S));
		Assert.assertEquals("[]>", S.toString());
		Assert.assertEquals("[A, B, C]>", SABC.toString());
	}
	
	@Test
	public void testSameContentsEmptyCasesAB(){
		StackImpl<String> b = new StackImpl<String>();
		StackImpl<String> a = new StackImpl<String>();
		
		Assert.assertEquals("[]>", a.toString());
		Assert.assertEquals("[]>", b.toString());
		Assert.assertTrue(SingleLinkedListImpl.sameContents(a, b));
		Assert.assertEquals("[]>", a.toString());
		Assert.assertEquals("[]>", b.toString());
	}
	
	@Test
	public void testPalindrome(){
		lSABC.addToRear("C");
		lSABC.addToRear("B");
		lSABC.addToRear("A");
		Assert.assertEquals("[A, B, C, C, B, A]", lSABC.toString());
		Assert.assertTrue(lSABC.palindrome());
	}
	
	@Test
	public void testPalindromeFailLast(){
		lSABC.addToRear("C");
		lSABC.addToRear("B");
		lSABC.addToRear("B");
		Assert.assertEquals("[A, B, C, C, B, B]", lSABC.toString());
		Assert.assertFalse(lSABC.palindrome());
	}
	
	@Test
	public void testPalindromeFailMiddle(){
		lSABC.addToRear("X");
		lSABC.addToRear("B");
		lSABC.addToRear("A");
		Assert.assertEquals("[A, B, C, X, B, A]", lSABC.toString());
		Assert.assertFalse(lSABC.palindrome());
	}
	
	@Test
	public void testPalindromeEmpty(){
		Assert.assertTrue(lS.palindrome());
	}
		
	@Test
	public void testMaxPrefix(){
		SingleLinkedListImpl<String> part = new SingleLinkedListImpl<String>("B", "C");
		Assert.assertEquals(2, lSABC.maxPrefix(part));
	}
	
	@Test
	public void testMaxPrefixOther(){
		SingleLinkedListImpl<String> part = new SingleLinkedListImpl<String>("A", "A", "B");
		SingleLinkedListImpl<String> listPart = new SingleLinkedListImpl<String>("A", "A", "A", "B", "C");
		Assert.assertEquals(3, listPart.maxPrefix(part));
	}
	
	@Test
	public void testMaxPrefixPartShort(){
		SingleLinkedListImpl<String> part = new SingleLinkedListImpl<String>("B");
		Assert.assertEquals(1, lSABC.maxPrefix(part));
	}
	
	@Test
	public void testMaxPrefixLastNotEqual(){
		SingleLinkedListImpl<String> part = new SingleLinkedListImpl<String>("B", "X");
		Assert.assertEquals(1, lSABC.maxPrefix(part));
	}
	
	@Test
	public void testMaxPrefixEmpty(){
		SingleLinkedListImpl<String> part = new SingleLinkedListImpl<String>("B", "C");
		Assert.assertEquals(0, lS.maxPrefix(part));
	}
	
	@Test
	public void testPositionOfLast(){
		Assert.assertEquals(3, lSABC.positionOfLast("C"));
	}
	
	@Test
	public void testPositionOfLastDuplicate(){
		lSABC.addToRear("B");
		lSABC.addToRear("C");
		Assert.assertEquals(5, lSABC.positionOfLast("C"));
	}
	
	@Test
	public void testPositionOfLastNotFound(){
		Assert.assertEquals(0, lSABC.positionOfLast("Z"));
	}
	
	@Test
	public void testPositionOfLastEmpty(){
		Assert.assertEquals(0L, lS.positionOfLast("X"));
	}
	
	@Test
	public void testRemoveConsecutiveDuplicates(){
		SingleLinkedListImpl<String> lSDuplicated = new SingleLinkedListImpl<String>("A", "A", "A", "B", "B", "C", "C");
		Assert.assertEquals("[A, B, C]", lSDuplicated.removeConsecutiveDuplicates().toString());
	}
	
	@Test
	public void testRemoveConsecutiveDuplEmpty(){
		Assert.assertEquals("[]", lS.removeConsecutiveDuplicates().toString());
	}
	
	@Test
	public void testSubsequence(){
		SingleLinkedListImpl<String> lSAC = new SingleLinkedListImpl<String>("A", "C");
		SingleLinkedListImpl<String> lSAZC = new SingleLinkedListImpl<String>("A", "Z", "C");
		SingleLinkedListImpl<String> lSCA = new SingleLinkedListImpl<String>("C", "A");
		Assert.assertTrue(lSABC.subsequence(lSAC));
		Assert.assertFalse(lSABC.subsequence(lSAZC));
		Assert.assertTrue(lSABC.subsequence(lSABC));
		Assert.assertFalse(lSABC.subsequence(lSCA));
	}
	
	@Test
	public void testSubsequenceEmptySubsequence(){
		Assert.assertTrue(lSABC.subsequence(lS));
	}
	
	@Test
	public void testSubsequenceEmptyList(){
		Assert.assertFalse(lS.subsequence(lSABC));
	}
	
	@Test
	public void testProgIterator(){
		SingleLinkedListImpl<Number> lNprog = new SingleLinkedListImpl<Number>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
		Iterator<Number> it = lNprog.progIterator();
		
		Assert.assertEquals(1, it.next());
		Assert.assertEquals(2, it.next());
		Assert.assertEquals(4, it.next());
		Assert.assertEquals(7, it.next());
		Assert.assertEquals(11, it.next());
		Assert.assertEquals(16, it.next());
		
		Assert.assertFalse(it.hasNext());	
	}
	
	@Test
	public void testProgIteratorEmpty(){
		Iterator<String> it = lS.progIterator();
		
		Assert.assertFalse(it.hasNext());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testProgIteratorExceptionNoElement(){
		Iterator<String> it = lS.progIterator();
		it.next();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testProgIteratorExceptionRemove(){
		Iterator<String> it = lSABC.progIterator();
		it.remove();
	}
	
	@Test
	public void testMaxString(){
		Assert.assertEquals("C", lSABC.max(cmpf01));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testMaxStringEmpty(){
		lS.max(cmpf01);
	}
	
	@Test
	public void testMaxString1Begin(){
		SingleLinkedListImpl<String> lSCAB = new SingleLinkedListImpl<String>("C", "A", "B");
		Assert.assertEquals("C", lSCAB.max(cmpf01));
	}
	
	@Test
	public void testMaxString1Dupl(){
		SingleLinkedListImpl<String> lSCABBC = new SingleLinkedListImpl<String>("C", "A", "B", "A", "C");
		Assert.assertEquals("C", lSCABBC.max(cmpf01));
	}
	
	@Test
	public void testMaxString2Multiple(){
		SingleLinkedListImpl<String> lSCABBC = new SingleLinkedListImpl<String>("CAA", "ABB", "BJGH", "A", "C");
		Assert.assertEquals("BJGH", lSCABBC.max(cmpf02));
	}
	
	@Test
	public void testMaxString2(){
		Assert.assertEquals("C", lSABC.max(cmpf02));
	}
	
	@Ignore
	@Test
	public void testMaxNumber(){
		//Assert.assertEquals(3, lN123.max(cmpf02));
	}
	
	@Test
	public void testIterator(){
		Iterator<Number> it = lN123.iterator();
		
		Assert.assertEquals(1, it.next());
		Assert.assertEquals(2, it.next());
		Assert.assertEquals(3, it.next());
		
		Assert.assertFalse(it.hasNext());
	}
	
	@Test
	public void testIteratorEmpty(){
		
		Iterator<String> it = lS.iterator();
		
		Assert.assertFalse(it.hasNext());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIteratorEmptyException(){
		Iterator<String> it = lS.iterator();
		it.next();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testIteratorRemoveException(){
		Iterator<String> it = lS.iterator();
		it.remove();
	}
	
	@Test
	public void testConstructorIterable(){
		SingleLinkedListImpl<String> lSCAB = new SingleLinkedListImpl<String>("C", "A", "B");
		SingleLinkedListImpl<String> prueba = new SingleLinkedListImpl(lSCAB);
		Assert.assertEquals(lSCAB.toString(), prueba.toString());
	}

}
