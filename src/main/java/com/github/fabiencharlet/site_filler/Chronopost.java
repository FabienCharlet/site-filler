package com.github.fabiencharlet.site_filler;

import java.awt.event.KeyEvent;

import com.github.fabiencharlet.site_filler.application.FakeDataService;
import com.github.fabiencharlet.site_filler.domain.Person;
import com.github.fabiencharlet.site_filler.infrastructure.HumanSimulator;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class Chronopost {


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

		for (int i = 0; i < 1; i++) {

			final Person fakePerson = DATA_SERVICE.getFakePerson();
			System.out.println( i + " : " + fakePerson);
			run(fakePerson);
		}

	}

	private static void run(final Person fakePerson) throws Exception {

		HUMAN.launchBrowser();

		HUMAN.waitMs(2_000);

		HUMAN.goToUrl("https://welikesomuch.click/c/ZvntUKw6OC0?s1=102c89847078e3f0576f2176e5cae9&s2=1309&s3=4542&offer_id=38864&s4=&first=&last=&country=&zip=&city=&address=&email=&phone={adv_sub}&p_id=#nt");

		HUMAN.typeCombined(KeyEvent.VK_CONTROL, KeyEvent.VK_L);

		HUMAN.waitMs(200);
		HUMAN.changeField();HUMAN.changeField();
		HUMAN.fill(fakePerson.prenom);
		HUMAN.fill(fakePerson.nom);

		HUMAN.fill(fakePerson.rue);
		HUMAN.fill(fakePerson.codePostal);
		HUMAN.fill(fakePerson.ville);
		HUMAN.changeField();
		HUMAN.changeField();
		HUMAN.fill(FakeDataService.getNumeroTelephone06());
		HUMAN.fill(fakePerson.email);

		HUMAN.keyPress(KeyEvent.VK_SPACE);

		HUMAN.waitMs(7_000);
		HUMAN.changeField();
		HUMAN.fill("5579880140654463");

		HUMAN.type("0");HUMAN.waitMs(1050);
		HUMAN.type("7");HUMAN.waitMs(1050);
		HUMAN.type("2");HUMAN.waitMs(1050);
		HUMAN.type("4");HUMAN.waitMs(1050);

//		HUMAN.fill(fakePerson.prenom);
//
//		HUMAN.waitMs(2_000);
//
//

//
//		HUMAN.type("470");HUMAN.waitMs(1050);
//		HUMAN.fill(fakePerson.prenom + " " + fakePerson.nom.toUpperCase());
//
//		HUMAN.keyPress(KeyEvent.VK_SPACE);
//
//		HUMAN.waitMs(40_000);
//
//		HUMAN.typeCombined(KeyEvent.VK_ALT, KeyEvent.VK_F4);
//
//		HUMAN.waitMs(3_000);
	}



}
