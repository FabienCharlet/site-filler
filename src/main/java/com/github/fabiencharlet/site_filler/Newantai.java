package com.github.fabiencharlet.site_filler;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.github.fabiencharlet.site_filler.application.FakeDataService;
import com.github.fabiencharlet.site_filler.domain.Person;

// https://develop.maxformaacademias.com.br/files/newantai/
public class Newantai {


	private static  Robot robot;
	private static  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	public static void main(final String[] args) throws Exception {

		robot = new Robot();
		final FakeDataService personService = new FakeDataService();

		for (int i = 0; i < 1_000_000; i++) {

			final Person fakePerson = personService.getFakePerson();
			System.out.println( i + " : " + fakePerson);
			run(fakePerson);
		}

	}

	private static void run(final Person fakePerson) {

		waitMs(3000);

		changeField();changeField();changeField();changeField();
		type(fakePerson.nom + " " + fakePerson.prenom); changeField();
		type(fakePerson.dateNaissance.toString()); changeField();
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



}
