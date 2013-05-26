package robotium_test;

import aktiviteter.Kulhydrat_akt;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

import dk.pyemus.blodsukkerapp_v3.R;

public class Kulhydrat_test extends ActivityInstrumentationTestCase2<Kulhydrat_akt>{

	private Solo solo;

	public Kulhydrat_test() {
		super(Kulhydrat_akt.class);
	}
	
	
    @Override
    public void setUp() throws Exception {
            //setUp() is run before a test case is started. 
            //This is where the solo object is created.
            solo = new Solo(getInstrumentation(), getActivity());
    }
    
    @Override
    public void tearDown() throws Exception {
            //tearDown() is run after a test case has finished. 
            //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
            solo.finishOpenedActivities();
    }
    
    public void testVisKulhydratmængdeIAppelsin(){
    	solo.clickInList(1);
    	solo.waitForText("FLIP FOR INFO", 1, 2000); //Venter på fragment animation
    	solo.clickOnActionBarItem(R.id.flipknap);
//    	solo.clickOnText("FLIP FOR INFO");
    	solo.waitForText("appelsin", 1, 2000); //Venter på fragment animation
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Vi søger efter det forventede
        boolean actual = solo.searchText("En appelsin indeholder 10g. kulhydrater.");
        //Hævder om resultatet er godkendt 
        assertEquals("Resultatet er ikke godkendt", expected, actual);
        solo.goBack();
        solo.goBack();
    }
	
	
}