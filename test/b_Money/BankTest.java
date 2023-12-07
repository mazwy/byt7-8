package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	/**
	 * Test for getting a bank name
	 * @result The name of the bank is returned
	 */
	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	/**
	 * Test for getting a bank currency
	 * @result The currency of the bank is returned
	 */
	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK,Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	/**
	 * Test for opening an account
	 * @result An account is opened
	 * @throws AccountExistsException If the account already exists
	 */
	@Test
	public void testOpenAccount() throws AccountExistsException {
		SweBank.openAccount("Alice");

		var alice = SweBank.getAccount("Alice");

		assertNotNull(alice);
		assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Alice"));

		Nordea.openAccount("Alice");

		var alice2 = Nordea.getAccount("Alice");

		assertNotNull(alice2);
		assertThrows(AccountExistsException.class, () -> Nordea.openAccount("Alice"));

		DanskeBank.openAccount("Bob");

		var bob = DanskeBank.getAccount("Bob");

		assertNotNull(bob);
		assertThrows(AccountExistsException.class, () -> DanskeBank.openAccount("Bob"));
	}

	/**
	 * Test for depositing money to an account
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(2000000, SEK));

		var ulrika = SweBank.getAccount("Ulrika");

		assertEquals(2000000, ulrika.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.deposit("Ulrika2", new Money(1000000, SEK)));

		Nordea.deposit("Bob", new Money(1000000, SEK));

		var bob = Nordea.getAccount("Bob");

		assertEquals(1000000, bob.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class,
			() -> Nordea.deposit("Bob2",
				new Money(1000000, SEK)
			)
		);

		DanskeBank.deposit("Gertrud", new Money(1000000, DKK));

		var gertrud = DanskeBank.getAccount("Gertrud");

		assertEquals(1000000, gertrud.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class,
			() -> DanskeBank.deposit("Gertrud2",
				new Money(1000000, DKK)
			)
		);
	}

	/**
	 * Test for withdrawing money from an account
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000000, SEK));
		SweBank.withdraw("Ulrika", new Money(1000000, SEK));

		var ulrika = SweBank.getAccount("Ulrika");

		assertEquals(0,
				ulrika.getBalance().getAmount(),
				0.0
		);

		assertThrows(AccountDoesNotExistException.class, () -> SweBank.withdraw("Ulrika2", new Money(1000000, SEK)));

		Nordea.deposit("Bob", new Money(1000000, SEK));
		Nordea.withdraw("Bob", new Money(1000000, SEK));

		var bob = Nordea.getAccount("Bob");

		assertEquals(0, bob.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class, () -> Nordea.withdraw("Bob2", new Money(1000000, SEK)));
	}

	/**
	 * Test for getting the balance of an account
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000000, SEK));

		assertEquals(1000000, SweBank.getBalance("Ulrika"), 0.0);
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.getBalance("Ulrika2"));

		Nordea.deposit("Bob", new Money(1000000, SEK));

		assertEquals(1000000, Nordea.getBalance("Bob"), 0.0);
		assertThrows(AccountDoesNotExistException.class, () -> Nordea.getBalance("Bob2"));
	}

	/**
	 * Test for transferring money between accounts
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000000, SEK));
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(1000000, SEK));

		var ulrika = SweBank.getAccount("Ulrika");

		assertEquals(0, ulrika.getBalance().getAmount(), 0.0);

		var bob = Nordea.getAccount("Bob");

		assertEquals(1000000, bob.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class,
			() -> SweBank.transfer("Ulrika2",
				Nordea,
				"Bob",
				new Money(1000000, SEK)
			)
		);

		Nordea.deposit("Bob", new Money(1000000, SEK));
		Nordea.transfer("Bob", SweBank, "Ulrika", new Money(1000000, SEK));

		var ulrika2 = SweBank.getAccount("Ulrika");

		assertEquals(1000000, ulrika2.getBalance().getAmount(), 0.0);

		var bob2 = Nordea.getAccount("Bob");

		assertEquals(1000000, bob2.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class,
			() -> Nordea.transfer("Bob2",
				SweBank,
				"Ulrika",
				new Money(1000000, SEK)
			)
		);
	}

	/**
	 * Test for adding a timed payment to an account
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000000, SEK));
		SweBank.addTimedPayment("Ulrika", "1", 2, 2, new Money(1000000, SEK), Nordea, "Bob");
		SweBank.tick();
		SweBank.tick();

		var ulrika = SweBank.getAccount("Ulrika");

		assertEquals(0, ulrika.getBalance().getAmount(), 0.0);

		var bob = Nordea.getAccount("Bob");

		assertEquals(1000000, bob.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class,
			() -> SweBank.addTimedPayment(
				"Ulrika2",
				"1",
				2,
				2,
				new Money(1000000, SEK),
				Nordea,
				"Bob"
			)
		);

		Nordea.deposit("Bob", new Money(1000000, SEK));
		Nordea.addTimedPayment("Bob",
			"1",
			2,
			2,
			new Money(1000000, SEK),
			SweBank,
			"Ulrika"
		);

		Nordea.tick();
		Nordea.tick();

		var ulrika2 = SweBank.getAccount("Ulrika");

		assertEquals(1000000, ulrika2.getBalance().getAmount(), 0.0);

		var bob2 = Nordea.getAccount("Bob");

		assertEquals(1000000, bob2.getBalance().getAmount(), 0.0);
		assertThrows(AccountDoesNotExistException.class,
			() -> Nordea.addTimedPayment(
				"Bob2",
				"1",
				2,
				2,
				new Money(1000000, SEK),
				SweBank,
				"Ulrika"
			)
		);
	}
}
