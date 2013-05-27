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
		solo.clearEditText(0);		
    	solo.enterText(0, "33");
    	solo.goBack();
    	solo.clickOnText("OK");
    	
		solo.clickOnText("Doegndosis");
		solo.clearEditText(0);
    	solo.enterText(0, "22");
    	boolean actual1 =solo.searchText("22");
    	solo.clickOnText("OK");
    	solo.sleep(250);

    	solo.clickOnText("Nød SMS ved lavt blodsukker");
    	solo.clearEditText(0);
    	solo.enterText(0, "5555");
    	solo.goBack();
    	solo.clearEditText(0);
    	solo.typeText(0, "5556");
    	solo.clickOnText("OK");
    	
    	solo.clickOnText("Nød SMS ved lavt blodsukker");
    	solo.clearEditText(0);
    	solo.typeText(0, "5556");
    	boolean actual2 = solo.searchText("5556");
    	solo.clickOnText("OK");
		
    	//Vi forventer at resultat er der
        boolean expected = true;
        
        //Hævder om resultatet er godkendt 
        assertEquals("Resultatet er ikke godkendt", expected, actual1);
        assertEquals("Resultatet er ikke godkendt", expected, actual2);
        
		solo.goBack();
		solo.sleep(1000);

    }
	
	
}