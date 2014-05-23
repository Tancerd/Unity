package com.unity.recruitment.view;

import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.unity.recruitment.dataaccess.DataStatus;
import com.unity.recruitment.facade.Facade;
import com.unity.recruitment.logic.AddClientStatus;
import com.unity.recruitment.model.Address;
import com.unity.recruitment.model.Client;

public class PoorView {

	private Facade facade;

	private final String MAINMENULABEL = "1. Dodaj klienta \n"
			+ "2. Wczytaj klientow z pliku \n"
			+ "3. Wyswietl wszystkich klientow \n"
			+ "4. Wyswietl wszyskich klientow spelniajacych warunek minimalnej kwoty zamowienia \n"
			+ "5. Zapisz plik z danymi \n";

	private final String BACKLABEL = "1. Powrot \n";
	private final String IDIOTUSERLABEL = "Zly format danych";
	private final String NOFILESELECTED = "Nie wybrano pliku";
	private final String GETCONDITIONLABEL = "Podaj wartosc zamowien, powyzej jakich klienci maja byc szukani";
	private final String NUMBEROFCLIENTS = "Liczba wysuzkanych klientow: ";
	private final String OCCUPIEDIDS = "Zajete id klientow: ";
	private final String[] CLIENTDATA = { "Imie: ", "Nazwisko: ", "Miasto: ",
			"Ulica: ", "Numer lokalu: ", "Kod pocztowy: ", "Wartosc zakupow: ",
			"ID: " };
	private final String ADDCLIENTSUCCESS = "Klient zapisany w bazie danych ";
	private final String ADDCLIENTFAILD = "Klient NIE zapisany w bazie danych ";
	private final String FILESAVED = "Plik zapisano pomyslnie ";
	private final String FILENOTSAVED = "Pliku NIE zapisano ";
	private final String AREYOUSURE = "Czy jestes pewien? Jezeli to zrobisz przed wczesniejszym zapisem biezacych danych to je stracisz ";
	private final String FILELOADED = "Plik wczytano pomyslnie ";
	private final String FILENOTLOADED = "Pliku NIE wczytano ";

	public PoorView() {
		facade = new Facade();
	}

	public void show() {
		showMainMenu();
	}

	private void showMainMenu() {
		int nextWindow = 0;

		String userInput = (String) JOptionPane.showInputDialog(MAINMENULABEL);
		nextWindow = checkUserInput(userInput);

		switch (nextWindow) {
		case -1:
			showMainMenu();
		case 1:
			showAddClientsDialog();
			break;
		case 2:
			showAreYouSureDialog();	
			break;
		case 3:
			showAllClientsDialogWithoutCondition();
			break;
		case 4:
			showAllClientsDialogWithCondition();
			break;
		case 5:
			showDirectoryChooserDialog();
		default:
			break;
		}

	}

	private void showAreYouSureDialog() {
		int result = JOptionPane.showConfirmDialog(null, AREYOUSURE);
		if (result == JOptionPane.OK_OPTION)
			showFileChooserDialog();
		else
			showMainMenu();
		
	}

	private void showDirectoryChooserDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("File Chooser");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().toString() + "\\data.ser";
			DataStatus status = facade.saveFile(path);
			if (status == DataStatus.SAVED) {
				JOptionPane.showMessageDialog(null, FILESAVED);
			} else {
				JOptionPane.showMessageDialog(null, FILENOTSAVED);
			}
			showMainMenu();
		} else {
			JOptionPane.showMessageDialog(null, NOFILESELECTED);
			showMainMenu();
		}

	}

	private void showFileChooserDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("File Chooser");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("ser", "ser"));
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().toString();
			System.out.println(path);
			DataStatus status = facade.loadFile(path);
			if (status == DataStatus.LOADED) {
				JOptionPane.showMessageDialog(null, FILELOADED);
				showMainMenu();
			} else {
				JOptionPane.showMessageDialog(null, FILENOTLOADED);
				showMainMenu();
			}
		} else {
			JOptionPane.showMessageDialog(null, NOFILESELECTED);
			showMainMenu();
		}

	}

	private int checkUserInput(String userInput) {
		if (userInput == null)
			return 0;
		try {
			return Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, IDIOTUSERLABEL);
			return -1;
		}
	}

	private void showAllClientsDialog(List<String> list) {

		String label = NUMBEROFCLIENTS + list.size() + "\n";
		label += String.join("\n", list);
		/*
		 * for (String s : list) label += s + "\n";
		 */
		label += "\n\n" + BACKLABEL;

		String userInput = (String) JOptionPane.showInputDialog(label);
		int nextWindow = checkUserInput(userInput);

		switch (nextWindow) {
		case 1:
			showMainMenu();
			break;
		default:
			break;
		}

	}

	private void showAllClientsDialogWithoutCondition() {
		List<String> clientsDTOList = facade.getAllClients();
		showAllClientsDialog(clientsDTOList);
	}

	private void showAllClientsDialogWithCondition() {
		String userInput = (String) JOptionPane
				.showInputDialog(GETCONDITIONLABEL);
		int conditionValue = checkUserInput(userInput);
		if (conditionValue < 0)
			conditionValue = 0;

		List<String> clientsDTOList = facade
				.getAllClientsWhoOrderedArticlesFor(conditionValue);
		showAllClientsDialog(clientsDTOList);
	}

	private void showAddClientsDialog() {
		/*
		 * 0 firstName; 1 surname; 2 city; 3 street; 4 numberOfLocal; 5
		 * postcode;
		 */
		String stringData[] = new String[CLIENTDATA.length - 2];
		long id;
		double valueOfOrderedArticles;

		for (int i = 0; i < stringData.length; i++) {
			String input = (String) JOptionPane.showInputDialog(CLIENTDATA[i]);
			if (input == null) {
				showMainMenu();
				return;
			} else if (input.equals(""))
				i--;
			else
				stringData[i] = input;
		}

		String input = "";
		do {
			input = (String) JOptionPane
					.showInputDialog(CLIENTDATA[CLIENTDATA.length - 2]);
			if (input == null) {
				showMainMenu();
				return;
			}
		} while (input == null || input.equals("")
				|| !input.matches("-?\\d+(\\.\\d+)?"));
		valueOfOrderedArticles = Double.parseDouble(input);

		List<String> idsList = facade.getAllOccupiedIDsForClients();
		String label = "\n" + " " + OCCUPIEDIDS + " "
				+ String.join(", ", idsList);
		input = "";
		do {
			input = (String) JOptionPane
					.showInputDialog(CLIENTDATA[CLIENTDATA.length - 1] + label);
			if (input == null) {
				showMainMenu();
				return;
			}
		} while (input.equals("") || !input.matches("\\d+")
				|| idsList.contains(input));
		id = Integer.parseInt(input);

		AddClientStatus status = facade.addClient(id, stringData[0],
				stringData[1], stringData[2], stringData[3], stringData[4],
				stringData[5], valueOfOrderedArticles);
		if (status == AddClientStatus.SUCCESS) {
			JOptionPane.showMessageDialog(null, ADDCLIENTSUCCESS);
			showMainMenu();
		} else {
			JOptionPane.showMessageDialog(null, ADDCLIENTFAILD);
		}

	}

	public static void main(String[] args) {
		PoorView view = new PoorView();
		view.show();
	}
}
