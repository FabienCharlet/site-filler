package com.github.fabiencharlet.site_filler.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import com.github.fabiencharlet.site_filler.domain.Person;
import com.google.common.base.Strings;

public class FakeDataService {

	private static List<String> PRENOMS;
	private static List<String> NOMS;
	private static List<Entry<String, String>> CP_VILLES;
	private static List<String> RUES;
	private static List<String> MAIL_PROVIDERS = List.of(
			"gmail.com",
			"hotmail.com",
			"aol.com",
			"hotmail.fr",
			"msn.com",
			"yahoo.fr",
			"orange.fr",
			"live.com",
			"free.fr",
			"outlook.com",
			"sfr.fr",
			"live.fr",
			"neuf.fr");


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

		final String mailProvider = MAIL_PROVIDERS.get((int) Math.abs(Math.random() * MAIL_PROVIDERS.size()));

		final String codePostal = cpVille.getValue();

		final String mailBase = switch (random(0, 3)) {

			case 0 -> prenom + "." + nom;
			case 1 -> prenom + codePostal;
			default -> prenom.charAt(0) + nom;
		};

		return new Person(
				prenom,
				nom,
				getDateNaissance(),
				rue,
				codePostal,
				cpVille.getKey(),
				getNumeroTelephone(),
				mailBase.toLowerCase() + "@" + mailProvider);

	}

	public static LocalDate getDateNaissance() {

		final int year = 1930 + ((int) Math.abs(Math.random() * 60));

		return LocalDate.of(year, 1, 1).plusDays(random(0, 365));
	}
	public static String getNumeroRue() {

		return "" + ((int) Math.abs(Math.random() * 1000));
	}

	public static String getUuid() {

		return UUID.randomUUID().toString();
	}

	public static String getRandomString(final int nbChar) {

		String res = "";

		for (int i = 0; i < nbChar; i++) {

			res += getRandomCharDigit();
		}

		return res;
	}

	public static String getRandomDigits(final int nbChar) {

		String res = "";

		for (int i = 0; i < nbChar; i++) {

			res += random(0, 10);
		}

		return res;
	}


	private static char getRandomCharDigit() {

		final char random = (char) random(0, 61);

		if (random < 26) {

			return (char) ('a' + random);
		}

		if (random < 52) {

			return (char) ('A' + (random - 26));
		}

		return (char) ('0' + (random - 52));
	}

	public static String getNumeroTelephone06() {

		final int first = 6;
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

	public static int random(final int from, final int to) {

		final int interval = to - from;

		return (int) (from + (Math.random() * interval));
	}
}
