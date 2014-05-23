package com.unity.recruitment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class lombokTestForClients {

	@Test
	public void clientsShouldBeEquals() throws Exception {
		Client client1 = new Client();
		client1.setID(1);
		Client client2 = new Client();
		client2.setID(1);

		assertTrue(client1.equals(client2));
		assertTrue(client2.equals(client1));
	}

	@Test
	public void clientsShouldNotBeEquals() throws Exception {
		Client client1 = new Client();
		client1.setID(1);
		Client client2 = new Client();
		client2.setID(2);

		assertFalse(client1.equals(client2));
		assertFalse(client2.equals(client1));
	}

	@Test
	public void clientsShouldReturnNiceStringFromToString() throws Exception {
		Address address = new Address("Wroclaw", "XYZ 36", "7", "55-400");
		Client client1 = new Client(1, "Michal", "Kowalik", address, 200.20d);
		String toString = client1.toString();
		System.out.println(toString);
		assertNotNull(toString);

	}

	@Test
	public void regexDoubleTest() throws Exception {
		String input = "123";
		assertTrue(input.matches("-?\\d+(\\.\\d+)?"));
		input = "123.22";
		assertTrue(input.matches("-?\\d+(\\.\\d+)?"));
		input = "123.22a";
		assertFalse(input.matches("-?\\d+(\\.\\d+)?"));
		input = "-123.22";
		assertTrue(input.matches("-?\\d+(\\.\\d+)?"));
	}

	@Test
	public void regexIntegerTest() throws Exception {
		String input = "123";
		assertTrue(input.matches("\\d+"));
		input = "123.22";
		assertFalse(input.matches("\\d+"));
		input = "123.22a";
		assertFalse(input.matches("\\d+"));
		input = "-123.22";
		assertFalse(input.matches("\\d+"));
		input = "-123";
		assertFalse(input.matches("\\d+"));
	}

	@Test
	public void clientsShouldReturnException() throws Exception {
		Address address = new Address("Wroclaw", "XYZ 36", "7", "55-400");
		boolean exception = false;
		try {
			Client client1 = new Client(1, "Michal", "Kowalik", null, 200.20d);
		} catch (NullPointerException e) {
			exception = true;
		}
		assertTrue(exception);
	}
}
