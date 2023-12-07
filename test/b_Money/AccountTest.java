package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);

		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");

		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));

		DKK = new Currency("DEK", 0.2);

		Nordea = new Bank("Nordea", DKK);
		Nordea.openAccount("Bob");
		Nordea.deposit("Bob", new Money(1000000, DKK));

		DanskeBank = new Bank("DanskeBank", DKK);
		DanskeBank.openAccount("Gertrud");
		DanskeBank.deposit("Gertrud", new Money(1000000, DKK));

	}

	/**
	 * Test for adding and removing a timed payment
	 */
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1",
			2,
			2,
			new Money(1000000, SEK),
			Nordea,
			"Bob"
		);
		assertTrue(testAccount.timedPaymentExists("1"));

		testAccount.addTimedPayment("2",
			2,
			2,
			new Money(1000000, SEK),
			DanskeBank,
			"Gertrud"
		);
		assertTrue(testAccount.timedPaymentExists("2"));

		testAccount.addTimedPayment("3",
			2,
			2,
			new Money(1000000, SEK),
			SweBank,
			"Alice"
		);
		assertTrue(testAccount.timedPaymentExists("3"));

		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));

		testAccount.removeTimedPayment("2");
		assertFalse(testAccount.timedPaymentExists("2"));

		testAccount.removeTimedPayment("3");
		assertFalse(testAccount.timedPaymentExists("3"));
	}

	/**
	 * Test for timed payments
	 */
	@Test
	public void testTimedPayment() {
		testAccount.addTimedPayment("1",
			2,
			2,
			new Money(300, SEK),
			Nordea,
			"Bob"
		);

		testAccount.addTimedPayment("2",
			2,
			2,
			new Money(1000000, SEK),
			DanskeBank,
			"Gertrud"
		);

		testAccount.addTimedPayment("3",
			2,
			2,
			new Money(1000000, SEK),
			SweBank,
			"Alice"
		);

		testAccount.tick();
		assertEquals(Double.valueOf(10000000), testAccount.getBalance().getAmount());

		testAccount.tick();
		assertEquals(Double.valueOf(7999700), testAccount.getBalance().getAmount());

		testAccount.tick();
		assertEquals(Double.valueOf(5999400), testAccount.getBalance().getAmount());

		testAccount.tick();
		assertEquals(Double.valueOf(5999400), testAccount.getBalance().getAmount());

		testAccount.tick();
		assertEquals(Double.valueOf(3999100), testAccount.getBalance().getAmount());

		testAccount.tick();
		assertEquals(Double.valueOf(1998800), testAccount.getBalance().getAmount());

		testAccount.tick();
		assertEquals(Double.valueOf(1998800), testAccount.getBalance().getAmount());

		testAccount.tick();
		assertEquals(Double.valueOf(-1500), testAccount.getBalance().getAmount());
	}

	/**
	 * Test for depositing money to an account
	 */
	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(9000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(8000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(7000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(6000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(5000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(4000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(3000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(2000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(1000000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(Double.valueOf(0), testAccount.getBalance().getAmount());
	}

	/**
	 * Test for getting an account balance
	 */
	@Test
	public void testGetBalance() {
		assertEquals(Double.valueOf(10000000), testAccount.getBalance().getAmount());
	}
}
