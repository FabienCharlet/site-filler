package com.github.fabiencharlet.site_filler.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.github.fabiencharlet.site_filler.domain.Person;
import com.google.common.base.Strings;

public class FakeDataService {

	private static List<String> PRENOMS;
	private static List<String> NOMS;
	private static List<Entry<String, String>> CP_VILLES;
	private static List<String> RUES;

	public FakeDataService() throws IOException {

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

	public Person getFakePerson() {

		final String prenom = PRENOMS.get((int) Math.abs(Math.random() * PRENOMS.size()));
		final String nom = NOMS.get((int) Math.abs(Math.random() * NOMS.size()));
		final String rue = getNumeroRue() + " " + RUES.get((int) Math.abs(Math.random() * RUES.size()));
		final Entry<String, String> cpVille = CP_VILLES.get((int) Math.abs(Math.random() * CP_VILLES.size()));

		return new Person(prenom, nom, getDateNaissance(), rue, cpVille.getValue(), cpVille.getKey(), getNumeroTelephone());

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

	public int random(final int from, final int to) {

		final int interval = to - from;

		return (int) (from + (Math.random() * interval));
	}
}
