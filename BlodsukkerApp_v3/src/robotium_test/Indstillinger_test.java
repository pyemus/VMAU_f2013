package robotium_test;

import aktiviteter.Indstillinger;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

import dk.pyemus.blodsukkerapp_v3.R;

public class Indstillinger_test extends ActivityInstrumentationTestCase2<Indstillinger>{

	private Solo solo;
	SharedPreferences myPreference;
	public Indstillinger_test() {
		super(Indstillinger.class);
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
    
    public void testAfIndstillinger(){
		solo.clickOnCheckBox(0);
		solo.clickOnCheckBox(1);
		solo.clickOnCheckBox(0);
		solo.clickOnCheckBox(2);
		solo.clickOnCheckBox(2);
		
		solo.clickOnText("Doegndosis");
//    	solo.waitForText("Cancel",1,2000);
		solo.clearEditText(0);
		
    	solo.typeText(0, "33");
//    	solo.sleep(250);
//    	solo.clickOnText("CANCEL");
//    	solo.sleep(250);
    	solo.clickOnText("OK");
		solo.clickOnText("Doegndosis");
//    	solo.waitForText("Cancel",1,2000);
		solo.clearEditText(0);
    	solo.typeText(0, "22");
    	solo.clickOnText("OK");
    	solo.sleep(250);
    	solo.clickOnText("Nød SMS ved lavt blodsukker");
//    	solo.waitForText("Cancel",1,2000);
    	solo.clearEditText(0);
    	solo.typeText(0, "5555");
    	solo.clearEditText(0);
    	solo.typeText(0, "5556");
//    	solo.clickOnText("Cancel");
    	solo.clickOnText("OK");
    	solo.clickOnText("Nød SMS ved lavt blodsukker");
//    	solo.waitForText("Cancel",1,2000);
    	solo.clearEditText(0);
    	solo.typeText(0, "5556");
    	solo.clickOnText("OK");
		solo.sleep(1000);
		solo.goBack();
    	
    	
    	
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Vi søger efter det forventede
        boolean actual = solo.searchText("En appelsin indeholder 10g. kulhydrater.");
        //Hævder om resultatet er godkendt 
        assertEquals("Resultatet er ikke godkendt", expected, actual);
    }
	
	
}