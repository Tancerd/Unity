package com.unity.recruitment.facade;

import java.util.List;

import com.unity.recruitment.dataaccess.DataStatus;
import com.unity.recruitment.dataaccess.DataStore;
import com.unity.recruitment.logic.AddClientStatus;
import com.unity.recruitment.logic.ClientService;
import com.unity.recruitment.model.Address;
import com.unity.recruitment.model.Client;

public class Facade {

	private DataStore dataStore;
	private ClientService clientService;

	
	public Facade() {
		dataStore = new DataStore();
		clientService = new ClientService();
	}

	public List<String> getAllClientsWhoOrderedArticlesFor(double value) {
		return clientService.getAllClientsWhoOrderedArticlesFor(value);
	}

	public List<String> getAllClients() {
		return clientService.getAllClients();
	}
	
	public List<String> getAllOccupiedIDsForClients()
	{
		return clientService.getAllOccupiedIDsForClients();
	}

	public AddClientStatus addClient(long id, String firstName, String surname,
			String city, String street, String numberOfLocal, String postcode,
			double valueOfOrderedArticles) {

		Address addressOfNewClient = new Address(city, street, numberOfLocal,
				postcode);
		Client newClient = new Client(id, firstName, surname,
				addressOfNewClient, valueOfOrderedArticles);
		return clientService.addClient(newClient);
	}

	public DataStatus saveFile(String path) {
		dataStore.setClientSet(clientService.getClientSet());
		return dataStore.saveDataToSerializableFile(path);
	}

	public DataStatus loadFile(String path) {
		DataStatus status = dataStore.loadDataFromSerializableFile(path);
		if (status == DataStatus.LOADED)
			clientService.setClientSet(dataStore.getClientSet());
		return status;
		
	}
}
