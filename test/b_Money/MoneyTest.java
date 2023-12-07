package b_Money;

import static org.junit.Assert.*;

import b_Money.Currency;
import b_Money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100, NOK100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		NOK = new Currency("NOK",2.0);
		NOK100 = new Money(10000, NOK);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/**
	 * Test for getting a money amount
	 */
	@Test
	public void testGetAmount() {
		assertEquals(Optional.of(SEK100.getAmount()), Optional.of(10000d));

		assertEquals(Optional.of(EUR10.getAmount()), Optional.of(1000d));

		assertEquals(Optional.of(SEK200.getAmount()), Optional.of(20000d));

		assertEquals(Optional.of(EUR20.getAmount()), Optional.of(2000d));

		assertEquals(Optional.of(SEK0.getAmount()), Optional.of(0d));

		assertEquals(Optional.of(EUR0.getAmount()), Optional.of(0d));

		assertEquals(Optional.of(SEKn100.getAmount()), Optional.of(-10000d));

		assertEquals(Optional.of(NOK100.getAmount()), Optional.of(10000d));
	}

	/**
	 * Test for getting a money currency
	 */
	@Test
	public void testGetCurrency() {
		assertEquals(Optional.of(SEK100.getCurrency()), Optional.of(SEK));

		assertEquals(Optional.of(EUR10.getCurrency()), Optional.of(EUR));

		assertEquals(Optional.of(SEK200.getCurrency()), Optional.of(SEK));

		assertEquals(Optional.of(EUR20.getCurrency()), Optional.of(EUR));

		assertEquals(Optional.of(SEK0.getCurrency()), Optional.of(SEK));

		assertEquals(Optional.of(EUR0.getCurrency()), Optional.of(EUR));

		assertEquals(Optional.of(SEKn100.getCurrency()), Optional.of(SEK));

		assertEquals(Optional.of(NOK100.getCurrency()), Optional.of(NOK));
	}

	/**
	 * Test for setting a money amount
	 */
	@Test
	public void testToString() {
		assertEquals(Optional.of(SEK100.toString()), Optional.of("100.0 SEK"));

		assertEquals(Optional.of(EUR10.toString()), Optional.of("10.0 EUR"));

		assertEquals(Optional.of(SEK200.toString()), Optional.of("200.0 SEK"));

		assertEquals(Optional.of(EUR20.toString()), Optional.of("20.0 EUR"));

		assertEquals(Optional.of(SEK0.toString()), Optional.of("0.0 SEK"));

		assertEquals(Optional.of(EUR0.toString()), Optional.of("0.0 EUR"));

		assertEquals(Optional.of(SEKn100.toString()), Optional.of("-100.0 SEK"));

		assertEquals(Optional.of(NOK100.toString()), Optional.of("100.0 NOK"));
	}

	/**
	 * Test for converting a money amount to universal value
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(Optional.of(SEK100.universalValue()), Optional.of(1500));

		assertEquals(Optional.of(EUR10.universalValue()), Optional.of(1500));

		assertEquals(Optional.of(SEK200.universalValue()), Optional.of(3000));

		assertEquals(Optional.of(EUR20.universalValue()), Optional.of(3000));

		assertEquals(Optional.of(SEK0.universalValue()), Optional.of(0));

		assertEquals(Optional.of(EUR0.universalValue()), Optional.of(0));

		assertEquals(Optional.of(SEKn100.universalValue()), Optional.of(-1500));

		assertEquals(Optional.of(NOK100.universalValue()), Optional.of(20000));
	}

	/**
	 * Test for converting a money amount to another currency
	 */
	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(new Money(10000, SEK)));

		assertFalse(SEK100.equals(new Money(20000, SEK)));

		assertFalse(SEK100.equals(new Money(10000, DKK)));

		assertFalse(SEK100.equals(new Money(20000, DKK)));

		assertFalse(SEK100.equals(new Money(0, DKK)));

		assertFalse(SEK100.equals(new Money(-10000, DKK)));

		assertFalse(SEK100.equals(new Money(0, SEK)));

		assertFalse(SEK100.equals(new Money(-10000, SEK)));

		assertFalse(SEK100.equals(new Money(20000, SEK)));

		assertFalse(SEK100.equals(new Money(0, SEK)));
	}

	/**
	 * Test for adding a money amount to another money amount
	 */
	@Test
	public void testAdd() {
		assertEquals(Optional.of(SEK100.add(EUR10).getAmount()), Optional.of(11000d));

		assertEquals(Optional.of(SEK100.add(SEK200).getAmount()), Optional.of(30000d));

		assertEquals(Optional.of(EUR10.add(EUR20).getAmount()), Optional.of(3000d));

		assertEquals(Optional.of(SEK100.add(SEKn100).getAmount()), Optional.of(0d));

		assertEquals(Optional.of(EUR10.add(EUR0).getAmount()), Optional.of(1000d));

		assertEquals(Optional.of(SEK100.add(EUR0).getAmount()), Optional.of(10000d));

		assertEquals(Optional.of(SEK0.add(EUR10).getAmount()), Optional.of(1000d));

		assertEquals(Optional.of(NOK100.add(EUR10).getAmount()), Optional.of(11000d));
	}

	/**
	 * Test for subtracting a money amount from another money amount
	 */
	@Test
	public void testSub() {
		assertEquals(Optional.of(SEK100.sub(EUR10).getAmount()), Optional.of(9000d));

		assertEquals(Optional.of(SEK100.sub(SEK200).getAmount()), Optional.of(-10000d));

		assertEquals(Optional.of(EUR10.sub(EUR20).getAmount()), Optional.of(-1000d));

		assertEquals(Optional.of(SEK100.sub(SEKn100).getAmount()), Optional.of(20000d));

		assertEquals(Optional.of(EUR10.sub(EUR0).getAmount()), Optional.of(1000d));

		assertEquals(Optional.of(SEK100.sub(EUR0).getAmount()), Optional.of(10000d));

		assertEquals(Optional.of(SEK0.sub(EUR10).getAmount()), Optional.of(-1000d));

		assertEquals(Optional.of(NOK100.sub(EUR10).getAmount()), Optional.of(9000d));
	}

	/**
	 * Test for checking if a money amount is zero
	 */
	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());

		assertTrue(EUR0.isZero());

		assertFalse(SEK100.isZero());

		assertFalse(EUR10.isZero());

		assertFalse(SEK200.isZero());

		assertFalse(EUR20.isZero());

		assertFalse(SEKn100.isZero());

		assertFalse(NOK100.isZero());
	}

	/**
	 * Test for negating a money amount
	 */
	@Test
	public void testNegate() {
		assertEquals(Optional.of(SEK100.negate().getAmount()), Optional.of(-10000d));

		assertEquals(Optional.of(EUR10.negate().getAmount()), Optional.of(-1000d));

		assertEquals(Optional.of(SEK200.negate().getAmount()), Optional.of(-20000d));

		assertEquals(Optional.of(EUR20.negate().getAmount()), Optional.of(-2000d));

		assertEquals(Optional.of(SEK0.negate().getAmount()), Optional.of(0d));

		assertEquals(Optional.of(EUR0.negate().getAmount()), Optional.of(0d));

		assertEquals(Optional.of(SEKn100.negate().getAmount()), Optional.of(10000d));

		assertEquals(Optional.of(NOK100.negate().getAmount()), Optional.of(-10000d));
	}

	/**
	 * Test for comparing two money amounts
	 */
	@Test
	public void testCompareTo() {
		assertEquals(SEK100.compareTo(EUR10), 0);

		assertEquals(SEK100.compareTo(SEK200), -1);

		assertEquals(EUR10.compareTo(EUR20), -1);

		assertEquals(SEK100.compareTo(SEKn100), 1);

		assertEquals(EUR10.compareTo(EUR0), 1);

		assertEquals(SEK100.compareTo(EUR0), 1);

		assertEquals(SEK0.compareTo(EUR10), -1);

		assertEquals(NOK100.compareTo(EUR10), 1);
	}
}
