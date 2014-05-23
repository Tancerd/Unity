package com.unity.recruitment.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import com.unity.recruitment.model.Client;

public class ClientService {

	@Setter
	@Getter
	private Set<Client> clientSet;

	public ClientService() {
		clientSet = new HashSet<>();
	}

	public List<String> getAllClients() {
		List<String> clientListDTO = clientSet.stream()
				.map(client -> client.toString()).collect(Collectors.toList());
		return clientListDTO;
	}

	public List<String> getAllClientsWhoOrderedArticlesFor(double value) {
		List<String> clientListDTO = clientSet.stream()
				.filter(client -> client.getValueOfOrderedArticles() >= value)
				.map(client -> client.toString()).collect(Collectors.toList());
		return clientListDTO;
	}

	public AddClientStatus addClient(@NonNull Client client) {
		if (!clientSet.contains(client)) {
			clientSet.add(client);
			return AddClientStatus.SUCCESS;
		}
		return AddClientStatus.CONFLICT;
	}

	public List<String> getAllOccupiedIDsForClients() {
		List<String> idListDTO = clientSet.stream()
				.map(client -> String.valueOf(client.getID())).collect(Collectors.toList());
		return idListDTO;
	}

}
