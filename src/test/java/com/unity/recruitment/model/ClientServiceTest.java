package com.unity.recruitment.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.unity.recruitment.logic.AddClientStatus;
import com.unity.recruitment.logic.ClientService;

public class ClientServiceTest {

	private ClientService clientService;
	private Client client1;
	private Client client2;

	@Before
	public void prepare() {
		clientService = new ClientService();
		client1 = new Client();
		client1.setValueOfOrderedArticles(200);
		client2 = new Client();
		client2.setValueOfOrderedArticles(300);
	}

	@Test
	public void shouldAddElementsToTheSet() {
		client1.setID(1);
		client2.setID(2);

		assertEquals(AddClientStatus.SUCCESS, clientService.addClient(client1));
		assertEquals(AddClientStatus.SUCCESS, clientService.addClient(client2));
		assertEquals(2, clientService.getAllClients().size());
	}

	@Test
	public void shouldNotAddElementsToTheSet() {
		client1.setID(1);
		client2.setID(1);

		assertEquals(AddClientStatus.SUCCESS, clientService.addClient(client1));
		assertEquals(AddClientStatus.CONFLICT, clientService.addClient(client2));
		assertEquals(1, clientService.getAllClients().size());
	}
	
	@Test
	public void shouldReturnTwoElementsFromSetWhenValueIsOnTheBrink() {
		client1.setID(1);
		client2.setID(2);
		clientService.addClient(client1);
		clientService.addClient(client2);
		assertEquals(2, clientService.getAllClientsWhoOrderedArticlesFor(200).size());
	}
	
	@Test
	public void shouldReturnTwoElementsFromSet() {
		client1.setID(1);
		client2.setID(2);
		clientService.addClient(client1);
		clientService.addClient(client2);
		assertEquals(2, clientService.getAllClientsWhoOrderedArticlesFor(190).size());
	}
	
	@Test
	public void shouldReturnOneElementFromSet() {
		client1.setID(1);
		client2.setID(2);
		clientService.addClient(client1);
		clientService.addClient(client2);
		assertEquals(1, clientService.getAllClientsWhoOrderedArticlesFor(210).size());
	}
}
