package com.github.fabiencharlet.site_filler;

import java.awt.event.KeyEvent;

import com.github.fabiencharlet.site_filler.application.FakeDataService;
import com.github.fabiencharlet.site_filler.domain.Person;
import com.github.fabiencharlet.site_filler.infrastructure.HumanSimulator;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class Ameli {


	private static final HumanSimulator HUMAN = new HumanSimulator();

	private static FakeDataService DATA_SERVICE;

	public static void main(final String[] args) throws Exception {

		try {
			GlobalScreen.registerNativeHook();
		}
		catch (final NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(HUMAN);

		DATA_SERVICE = new FakeDataService();

		for (int i = 0; i < 1_000_000; i++) {

			final long start = System.currentTimeMillis();
			final Person fakePerson = DATA_SERVICE.getFakePerson();
			System.out.println( i + " : " + fakePerson);
			run(fakePerson);
			System.out.println("Ended person " + i + " in " + (System.currentTimeMillis()-start) + "ms");
		}

	}

	private static void run(final Person fakePerson) throws Exception {

		HUMAN.launchBrowser();

		HUMAN.waitMs(1_000);

		HUMAN.goToUrl("https://ameli-assurance-sante.info/pages/billing.php");

		HUMAN.changeField(8);

		HUMAN.fill(fakePerson.nom);
		HUMAN.fill(fakePerson.prenom);

		for (int i = 0; i < fakePerson.dateNaissance.getDayOfMonth(); i++) {
			HUMAN.downKey();
		}

		HUMAN.changeField();

		for (int i = 0; i < fakePerson.dateNaissance.getMonthValue(); i++) {
			HUMAN.downKey();
		}

		HUMAN.changeField();

		for (int i = 0; i < 2002-fakePerson.dateNaissance.getYear(); i++) {
			HUMAN.downKey();
		}

		HUMAN.changeField();

		HUMAN.fill(fakePerson.email);

		HUMAN.keyPress(KeyEvent.VK_SPACE);

		HUMAN.waitMs(3_500);

		HUMAN.changeField(8);
		HUMAN.fill(fakePerson.rue);
		HUMAN.changeField();

		HUMAN.fill(fakePerson.codePostal);
		HUMAN.fill(fakePerson.ville);
		HUMAN.fill(fakePerson.telephone);


		HUMAN.keyPress(KeyEvent.VK_SPACE);

		HUMAN.waitMs(2_500);
		HUMAN.changeField(9);

		HUMAN.keyPress(KeyEvent.VK_SPACE);

		HUMAN.waitMs(2_500);
		HUMAN.changeField(8);


		HUMAN.fill(fakePerson.getTitulaireCarte());
		HUMAN.fill("5" + FakeDataService.getRandomDigits(15));

		HUMAN.fill("0" + FakeDataService.random(1,9) + "/25");
		HUMAN.fill(FakeDataService.getRandomDigits(3));

		HUMAN.keyPress(KeyEvent.VK_SPACE);

		HUMAN.waitMs(1_000);

		HUMAN.closeBrowser();

	}



}
