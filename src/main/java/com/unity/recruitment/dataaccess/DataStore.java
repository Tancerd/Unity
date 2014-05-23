package com.unity.recruitment.dataaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.unity.recruitment.model.Client;

public class DataStore implements Serializable {

	@Setter
	@Getter
	private Set<Client> clientSet;

	public DataStatus saveDataToSerializableFile(String path) {
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;

		try {
			fileOutputStream = new FileOutputStream(path);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(clientSet);
			fileOutputStream.close();
			return DataStatus.SAVED;
		} catch (FileNotFoundException e) {
			return DataStatus.ERROR;
		} catch (IOException e) {
			return DataStatus.ERROR;
		}

	}

	public DataStatus loadDataFromSerializableFile(String path) {
		
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			clientSet = (HashSet<Client>)objectInputStream.readObject();
			objectInputStream.close();
			return DataStatus.LOADED;
		} catch (FileNotFoundException e) {
			return DataStatus.ERROR;
		} catch (IOException e) {
			return DataStatus.ERROR;
		} catch (ClassNotFoundException e) {
			return DataStatus.ERROR;
		}
	}
}
