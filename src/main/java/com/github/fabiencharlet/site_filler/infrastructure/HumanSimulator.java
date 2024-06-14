package com.github.fabiencharlet.site_filler.infrastructure;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class HumanSimulator implements NativeKeyListener {

	private Robot		robot;
	private AtomicBoolean paused = new AtomicBoolean(false);
	private int debugWaitFactor;
	private static Clipboard	clipboard	= Toolkit.getDefaultToolkit().getSystemClipboard();

	public HumanSimulator() {

		this(1);
	}

	public HumanSimulator(final int debugWaitFactor) {

		this.debugWaitFactor = debugWaitFactor;
		try {
			robot = new Robot();
		}
		catch (final AWTException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void nativeKeyPressed(final NativeKeyEvent e) {

		if (e.getKeyCode() == NativeKeyEvent.VC_PAUSE) {

			paused.set(!paused.get());

			System.out.println(paused.get() ? "Pause" : "Resume");
		}

	}


	public void fill(final String text) {

		type(text);
		changeField();
	}

	public void init() {

		waitMs(500);
		typeCombined(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
		waitMs(200);

	}

	public void waitMs(final int timeMs) {

		final int waitSplitTIme = 200;

		final int nbTimes = (timeMs * debugWaitFactor) / waitSplitTIme;

		if (paused.get()) {

			System.out.print("Wating until pause is pressed");
		}

		while (paused.get()) {
			try {

				Thread.sleep(1_000);
			}
			catch (final InterruptedException e) {}
		}

		if (nbTimes > 2) {

			System.out.print("Wating");
		}

		for (int i = nbTimes; i > 0; i--) {

			try {

				if (nbTimes > 2) {

					System.out.print(" " + i);
				}

				Thread.sleep(waitSplitTIme);
			}
			catch (final InterruptedException e) {}
		}

		if (nbTimes > 2) {

			System.out.println();
		}
	}

	public void changeField() {

		robot.keyPress(KeyEvent.VK_TAB);
		waitMs(400);
	}

	public void goToUrl(final String url) {

		System.out.println("Go to " + url);
		typeCombined(KeyEvent.VK_CONTROL, KeyEvent.VK_L);
		waitMs(100);

		type(url);
		waitMs(100);
		submitEnter();

		waitMs(2_000);
	}

	public void goBack() {

		System.out.println("Go Back");
		typeCombined(KeyEvent.VK_ALT, KeyEvent.VK_LEFT);

		waitMs(100);
	}

	public void refresh() {

		System.out.println("F5");
		robot.keyPress(KeyEvent.VK_F5);
		waitMs(300);
	}
	public void submitEnter() {

		System.out.println("ENTER");
		robot.keyPress(KeyEvent.VK_ENTER);
	}

	public void clickMouse(final int x, final int y) {

		System.out.println("Moving mouse to ["+x+","+y+"]");
		robot.mouseMove(x, y);
		waitMs(500);

		System.out.println("Clicking mouse");
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		waitMs(50);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		waitMs(2500);
	}

	public void downKey() {

		System.out.println("Down");
		robot.keyPress(KeyEvent.VK_DOWN);

		waitMs(100);
	}

	public void type(final String text) {

		System.out.println("Typing " + text);
		final StringSelection stringSelection = new StringSelection(text);
		clipboard.setContents(stringSelection, stringSelection);

		typeCombined(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
		waitMs(150);
	}

	public void typeCombined(final int key1, final int key2) {

		robot.keyPress(key1);
		robot.keyPress(key2);
		robot.keyRelease(key2);
		robot.keyRelease(key1);
	}

	public void scrollDown() {

		System.out.println("Scroll down");
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);

		waitMs(100);
	}

	public void scrollUp() {

		System.out.println("Scroll up");
		robot.keyPress(KeyEvent.VK_PAGE_UP);

		waitMs(100);
	}

	public void fill(final int number) {

		fill("" + number);
	}


}
