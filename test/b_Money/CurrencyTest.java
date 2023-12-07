package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		NOK = new Currency("NOK", 2.0);
	}

	/**
	 * Test for getting a currency name
	 */
	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());

		assertEquals("DKK", DKK.getName());

		assertEquals("EUR", EUR.getName());

		assertEquals("NOK", NOK.getName());
	}

	/**
	 * Test for getting a currency rate
	 */
	@Test
	public void testGetRate() {
		assertEquals(Optional.of(0.15), Optional.of(SEK.getRate()));

		assertEquals(Optional.of(0.20), Optional.of(DKK.getRate()));

		assertEquals(Optional.of(1.5), Optional.of(EUR.getRate()));

		assertEquals(Optional.of(2.0), Optional.of(NOK.getRate()));
	}

	/**
	 * Test for setting a currency rate
	 */
	@Test
	public void testSetRate() {
		SEK.setRate(0.5);
		assertEquals(Optional.of(0.5), Optional.of(SEK.getRate()));

		DKK.setRate(1.5);
		assertEquals(Optional.of(1.5), Optional.of(DKK.getRate()));

		EUR.setRate(2.0);
		assertEquals(Optional.of(2.0), Optional.of(EUR.getRate()));

		NOK.setRate(0.5);
		assertEquals(Optional.of(0.5), Optional.of(NOK.getRate()));
	}

	/**
	 * Test for converting an amount of a currency to universal value
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(Optional.of(1500), Optional.of(SEK.universalValue(10000)));

		assertEquals(Optional.of(3000), Optional.of(SEK.universalValue(20000)));

		assertEquals(Optional.of(0), Optional.of(SEK.universalValue(0)));

		assertEquals(Optional.of(-1500), Optional.of(SEK.universalValue(-10000)));

		assertEquals(Optional.of(3000), Optional.of(EUR.universalValue(2000)));

		assertEquals(Optional.of(1500), Optional.of(EUR.universalValue(1000)));

		assertEquals(Optional.of(0), Optional.of(EUR.universalValue(0)));

		assertEquals(Optional.of(-3000), Optional.of(EUR.universalValue(-2000)));

		assertEquals(Optional.of(1500), Optional.of(NOK.universalValue(750)));

		assertEquals(Optional.of(3000), Optional.of(NOK.universalValue(1500)));
	}

	/**
	 * Test for converting an amount of a currency to another currency
	 */
	@Test
	public void testValueInThisCurrency() {
		assertEquals(Optional.of(1000), Optional.of(SEK.valueInThisCurrency(1500, EUR)));

		assertEquals(Optional.of(2000), Optional.of(SEK.valueInThisCurrency(3000, EUR)));

		assertEquals(Optional.of(0), Optional.of(SEK.valueInThisCurrency(0, EUR)));

		assertEquals(Optional.of(-1000), Optional.of(SEK.valueInThisCurrency(-1500, EUR)));

		assertEquals(Optional.of(20000), Optional.of(EUR.valueInThisCurrency(3000, SEK)));

		assertEquals(Optional.of(10000), Optional.of(EUR.valueInThisCurrency(1500, SEK)));

		assertEquals(Optional.of(0), Optional.of(EUR.valueInThisCurrency(0, SEK)));

		assertEquals(Optional.of(-20000), Optional.of(EUR.valueInThisCurrency(-3000, SEK)));

		assertEquals(Optional.of(10000), Optional.of(NOK.valueInThisCurrency(1500, SEK)));

		assertEquals(Optional.of(20000), Optional.of(NOK.valueInThisCurrency(3000, SEK)));
	}
}
