package ule.edi.tree;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class WorldTests {
	
	private World w = null;
	
	private World w1 = null;
	
	private World w2 = null;
	
	private World ejemplo1 = null;
	
	private World ejemplo2 = null;
	
	private LinkedList<Character> lC = null;
	
	@Before
	public void setupWorlds(){
		
		w = World.createEmptyWorld();
		
		w1 = World.createEmptyWorld();
		w1.insert("", Entity.princesses(1));
		w1.insert("LL", Entity.dragons(2));
		w1.insert("R", Entity.castles(1));
		
		w2 = World.createEmptyWorld();
		w2.insert("", Entity.warriors(2));
		w2.insert("LL", Entity.dragons(2));
		w2.insert("LL", Entity.warriors(3));
		w2.insert("R", Entity.castles(1));
		w2.insert("RL", Entity.dragons(1));
		
		ejemplo1 = World.createEmptyWorld();
		ejemplo2 = World.createEmptyWorld();
		
		ejemplo1.insert("LL", Entity.dragons(2));
		ejemplo1.insert("LL", Entity.princesses(1));
		ejemplo1.insert("LR", Entity.castles(1));
		
		ejemplo2.insert("L", Entity.princesses(2));
		ejemplo2.insert("R", Entity.dragons(1));
		
		lC = new LinkedList<Character>();
	}
	
	@Test
	public void insertTest(){
		w.insert("", Entity.castles(1));
		Assert.assertEquals("{[C(1)], ∅, ∅}", w.toString());
		w.insert("R", Entity.warriors(2));
		Assert.assertEquals("{[C(1)], ∅, {[W(2)], ∅, ∅}}", w.toString());
	}
	
	@Test
	public void insertTestInNotEmptyNotEqual(){
		w1.insert("R", Entity.castles(1));
		Assert.assertEquals("{[P(1)], {[F(1)], {[D(2)], ∅, ∅}, ∅}, {[C(2)], ∅, ∅}}", w1.toString());
		w1.insert("LL", Entity.warriors(2));
		Assert.assertEquals("{[P(1)], {[F(1)], {[D(2), W(2)], ∅, ∅}, ∅}, {[C(2)], ∅, ∅}}", w1.toString());
	}
	
	@Test
	public void insertTestInNotEmptyEqual(){
		w1.insert("R", Entity.castles(1));
		Assert.assertEquals("{[P(1)], {[F(1)], {[D(2)], ∅, ∅}, ∅}, {[C(2)], ∅, ∅}}", w1.toString());
		w1.insert("LL", Entity.warriors(2));
		Assert.assertEquals("{[P(1)], {[F(1)], {[D(2), W(2)], ∅, ∅}, ∅}, {[C(2)], ∅, ∅}}", w1.toString());
	}
	
	@Test
	public void testCountEntity(){
		Assert.assertEquals(2, w1.countEntity(Entity.DRAGON));
		Assert.assertEquals(1, w1.countEntity(Entity.PRINCESS));
	}
	
	@Test
	public void testCountEntityDiffPlaces(){
		Assert.assertEquals(3, w2.countEntity(Entity.DRAGON));
		Assert.assertEquals(5, w2.countEntity(Entity.WARRIOR));	// TODO arreglar metodo countEntity
		w2.insert("LL", Entity.warriors(3));
		Assert.assertEquals(8, w2.countEntity(Entity.WARRIOR));
	}
	
	@Test
	public void testAddEmptyThis(){
		Assert.assertEquals(w1.toString(), w.add(w1).toString());
		Assert.assertEquals(w2.toString(), w.add(w2).toString());
	}
	
	@Test
	public void testAddEmptyOther(){
		Assert.assertEquals(w1.toString(), w1.add(w).toString());
		Assert.assertEquals(w2.toString(), w2.add(w).toString());
	}
	
	@Test
	public void testAdd(){
		Assert.assertEquals("{[P(1), W(2)], {[F(2)], {[D(4), W(3)], ∅, ∅}, ∅}, {[C(2)], {[D(1)], ∅, ∅}, ∅}}", w1.add(w2).toString());
	}
	
	@Test
	public void testAdd2(){
		Assert.assertEquals("{[P(1), W(2)], {[F(2)], {[D(4), W(3)], ∅, ∅}, ∅}, {[C(2)], {[D(1)], ∅, ∅}, ∅}}", w2.add(w1).toString());
	}
	
	@Test
	public void testAddEjemplo(){
		Assert.assertEquals("{[F(2)], {[F(1), P(2)], {[D(2), P(1)], ∅, ∅}, {[C(1)], ∅, ∅}}, {[D(1)], ∅, ∅}}", ejemplo1.add(ejemplo2).toString());
	}
	
}
