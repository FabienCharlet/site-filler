package com.github.fabiencharlet.site_filler.domain;

import lombok.ToString;

@ToString
public class CreditCard {


	public String numero;
	public String expirationMonth;
	public String expirationYear;
	public String cvc;

	private CreditCard(final String numero, final String expirationMonth, final String expirationYear, final String cvc) {

		this.numero = numero;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.cvc = cvc;
	}



}
