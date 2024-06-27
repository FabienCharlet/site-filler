package com.github.fabiencharlet.site_filler.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.ToString;

@ToString
public class Person {

	public String prenom;
	public String nom;
	public LocalDate dateNaissance;
	public String rue;
	public String codePostal;
	public String ville;
	public String telephone;
	public String email;

	public Person(
			final String prenom,
			final String nom,
			final LocalDate dateNaissance,
			final String rue,
			final String codePostal,
			final String ville,
			final String telephone,
			final String email) {

		this.prenom = prenom;
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.telephone = telephone;
		this.email = email;
	}

	public String getAddress() {

		return rue + " " + codePostal + " " + ville;
	}

	public String getDateNaissance(final String dateFormat) {

		return dateNaissance.format(DateTimeFormatter.ofPattern(dateFormat));
	}

	public String getTitulaireCarte() {

		return prenom + " " + nom.toLowerCase();
	}

}
