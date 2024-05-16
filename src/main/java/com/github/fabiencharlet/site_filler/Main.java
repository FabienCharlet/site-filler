package com.github.fabiencharlet.site_filler;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Strings;

import lombok.ToString;

// https://develop.maxformaacademias.com.br/files/newantai/
public class Main {

	@ToString
	public static class Person {

		public String prenom;
		public String nom;
		public String dateNaissance;
		public String rue;
		public String codePostal;
		public String ville;
		public String telephone;

		private Person(final String prenom, final String nom, final String dateNaissance, final String rue, final String codePostal, final String ville, final String telephone) {

			this.prenom = prenom;
			this.nom = nom;
			this.dateNaissance = dateNaissance;
			this.rue = rue;
			this.codePostal = codePostal;
			this.ville = ville;
			this.telephone = telephone;
		}



	}

	private static List<String> PRENOMS;
	private static List<String> NOMS;
	private static List<Entry<String, String>> CP_VILLES;
	private static List<String> RUES;

	private static  Robot robot;
	private static  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	public static void main(final String[] args) throws Exception {

		robot = new Robot();
		loadFakeData();

		for (int i = 0; i < 1_000_000; i++) {

			final Person fakePerson = createFakePerson();
			System.out.println( i + " : " + fakePerson);
			run(fakePerson);
		}

	}

	private static void run(final Person fakePerson) {

		waitMs(3000);

		changeField();changeField();changeField();changeField();
		type(fakePerson.nom + " " + fakePerson.prenom); changeField();
		type(fakePerson.dateNaissance); changeField();
		type(fakePerson.telephone); changeField();
		type(fakePerson.ville); changeField();
		type(fakePerson.rue); changeField();
		type(fakePerson.codePostal);changeField();

		submit();
		goBack();
		refresh();
	}

	private static void goBack() {

		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_ALT);

		waitMs(100);
	}

	private static void refresh() {

		robot.keyPress(KeyEvent.VK_F5);
		waitMs(300);
	}

	private static void submit() {

		robot.mouseMove(530,648);
		waitMs(200);

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		waitMs(50);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		waitMs(2500);
	}

	private static void waitMs(final int timeMs) {

		try {
			Thread.sleep(timeMs);
		}
		catch (final InterruptedException e) {}
	}

	private static void changeField() {

		robot.keyPress(KeyEvent.VK_TAB);
		waitMs(200);
	}

	private static void type(final String text) {

		System.out.println("Typing " + text);
		final StringSelection stringSelection = new StringSelection(text);
		clipboard.setContents(stringSelection, stringSelection);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		waitMs(250);
	}

	private static Person createFakePerson() {

		final String prenom = PRENOMS.get((int) Math.abs(Math.random() * PRENOMS.size()));
		final String nom = NOMS.get((int) Math.abs(Math.random() * NOMS.size()));
		final String rue = getNumeroRue() + " " + RUES.get((int) Math.abs(Math.random() * RUES.size()));
		final Entry<String, String> cpVille = CP_VILLES.get((int) Math.abs(Math.random() * CP_VILLES.size()));

		return new Person(prenom, nom, getDateNaissance(), rue, cpVille.getValue(), cpVille.getKey(), getNumeroTelephone());
	}

	private static void loadFakeData() throws IOException {

		PRENOMS = List.copyOf(Files.readAllLines(Path.of("src/main/resources/prenoms.txt")));
		System.out.println("Loaded " + PRENOMS.size() + " prenoms");

		NOMS = List.copyOf(Files.readAllLines(Path.of("src/main/resources/noms.txt")));
		System.out.println("Loaded " + NOMS.size() + " noms");

		final HashMap<String, String> cpVilles = new HashMap<>();

		for (final String line : Files.readAllLines(Path.of("src/main/resources/villes.txt"))) {

			final String[] cpVille = line.split("\\t");
			cpVilles.put(cpVille[1], Strings.padStart(cpVille[0], 5, '0'));
		}

		CP_VILLES = List.copyOf(cpVilles.entrySet());
		System.out.println("Loaded " + CP_VILLES.size() + " cp / villes");

		RUES = List.copyOf(Files.readAllLines(Path.of("src/main/resources/rues.txt")));
		System.out.println("Loaded " + RUES.size() + " rues");
	}

	public static String getDateNaissance() {

		final int day = (int) Math.abs(Math.random() * 30) + 1;
		final int month = (int) Math.abs(Math.random() * 12) + 1;
		final int year = (int) Math.abs(Math.random() * 60) + 60;

		return Strings.padStart("" + day, 2, '0')
		+ "/" + Strings.padStart("" + month, 2, '0')
		+ "/19" +  Strings.padStart("" + year, 2, '0');
	}
	public static String getNumeroRue() {

		return "" + ((int) Math.abs(Math.random() * 1000));
	}

	public static String getNumeroTelephone() {

		final int first = (int) Math.abs(Math.random() * 2) + 6;
		final int second = (int) Math.abs(Math.random() * 100);
		final int third = (int) Math.abs(Math.random() * 100);
		final int fourth = (int) Math.abs(Math.random() * 100);
		final int fifth = (int) Math.abs(Math.random() * 100);

		return
				Strings.padStart("" + first, 2, '0')
        +		Strings.padStart("" + second, 2, '0')
		+ 		Strings.padStart("" + third, 2, '0')
		+ 		Strings.padStart("" + fourth, 2, '0')
		+	    Strings.padStart("" + fifth, 2, '0');
	}

}
