package com.github.fabiencharlet.site_filler;

import com.github.fabiencharlet.site_filler.application.FakeDataService;
import com.github.fabiencharlet.site_filler.domain.Person;
import com.github.fabiencharlet.site_filler.infrastructure.HumanSimulator;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

// https://develop.maxformaacademias.com.br/files/newantai/
public class Grdf {


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

			final Person fakePerson = DATA_SERVICE.getFakePerson();
			System.out.println( i + " : " + fakePerson);
			run(fakePerson);
		}

	}

	private static void run(final Person fakePerson) {

		HUMAN.goToUrl("https://vps91589.inmotionhosting.com/TH/host12/pages/information.php");

		HUMAN.changeField();
		HUMAN.downKey();
		HUMAN.changeField();

		HUMAN.fill(fakePerson.prenom);
		HUMAN.fill(fakePerson.nom);

		HUMAN.fill(fakePerson.getAddress());
		HUMAN.fill(DATA_SERVICE.random(150, 270));
		HUMAN.fill(fakePerson.telephone);
		HUMAN.fill(DATA_SERVICE.random(5, 10));

		HUMAN.scrollDown();

		// One screen
		//MACHINE.submitMouse(1100,220);
		// Two screens
		HUMAN.clickMouse(-800,220);
		HUMAN.waitMs(5_000);
	}



}
