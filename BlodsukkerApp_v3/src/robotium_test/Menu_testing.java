package robotium_test;

import aktiviteter.BlodsukkerMain_akt;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class Menu_testing extends
		ActivityInstrumentationTestCase2<BlodsukkerMain_akt> {

	private Solo solo;

	public Menu_testing() {
		super(BlodsukkerMain_akt.class);
	}

	@Override
	public void setUp() throws Exception {
		// setUp() is run before a test case is started.
		// This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		// tearDown() is run after a test case has finished.
		// finishOpenedActivities() will finish all the activities that have
		// been opened during the test execution.
		solo.finishOpenedActivities();
	}

	// Solo starter i første menu

	// Anden menu
	public void testAndenMenu() throws Exception {
		// Skal køres I GUI tråden
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Historik");
		solo.assertCurrentActivity("Fejler...Forventet Historik Aktivitet", aktiviteter.Historik_akt.class);
		solo.sleep(1000);
	}

	public void testTredjeMenu() throws Exception {
		// Tredje menu
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Fødevareliste");
		solo.assertCurrentActivity("Fejler...Forventet Fødevareliste aktivitet", aktiviteter.Kulhydrat_akt.class);
		solo.sleep(1000);
	}

	public void testFjerdeMenu() throws Exception {
		// Fjerde menu
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Insulinberegning");
		solo.assertCurrentActivity("Fejler...Forventet Insulinberegning aktivitet", aktiviteter.Insulinberegning_akt.class);
		solo.sleep(1000);
	}

	public void testFemteMenu() throws Exception {
		// Femte menu
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Backup");
		solo.assertCurrentActivity("Fejler...Forventet Backup aktivitet", netvaerk.Network_komm.class);
		solo.sleep(1000);
	}

	public void testDialog1() throws Exception {
		// Dialog 1
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Hjælp");
		
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Vi søger efter det forventede
        boolean actual = solo.waitForDialogToOpen(2000);
        //Hævder om resultatet er godkendt 
        assertEquals("Dialog ikke åbnet..ikke godkendt", expected, actual);
		solo.sleep(1000);
		solo.goBack();
	}

	public void testDialog2() throws Exception {
		// Dialog 2
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Om");
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Vi søger efter det forventede
        boolean actual = solo.waitForDialogToOpen(2000);
        //Hævder om resultatet er godkendt 
        assertEquals("Dialog ikke åbnet..ikke godkendt", expected, actual);
		solo.sleep(1000);
		solo.goBack();
	}

	public void testIndstillinger() throws Exception {
		// Indstillinger
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Indstillinger");
		solo.clickOnCheckBox(0);
		solo.clickOnCheckBox(1);
		solo.clickOnCheckBox(0);
		solo.clickOnCheckBox(2);
		solo.clickOnCheckBox(2);
		solo.assertCurrentActivity("Fejler...Forventet Indsillinger aktivitet", aktiviteter.Indstillinger.class);
	}

	public void testAfslut() throws Exception {
		// afslut
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				solo.clickOnActionBarHomeButton();
			}
		});
		solo.clickOnText("Luk app");

		
	}

}
