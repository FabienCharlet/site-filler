package com.github.fabiencharlet.site_filler.domain;

import lombok.ToString;

@ToString
public class Person {

	public String prenom;
	public String nom;
	public String dateNaissance;
	public String rue;
	public String codePostal;
	public String ville;
	public String telephone;

	public Person(final String prenom, final String nom, final String dateNaissance, final String rue, final String codePostal, final String ville, final String telephone) {

		this.prenom = prenom;
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.telephone = telephone;
	}

	public String getAddress() {

		return rue + " " + codePostal + " " + ville;
	}

}
