package robotium_test;

import aktiviteter.Insulinberegning_akt;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class InsulinBeregning_test extends ActivityInstrumentationTestCase2<Insulinberegning_akt>{

	private Solo solo;

	public InsulinBeregning_test() {
		super(Insulinberegning_akt.class);
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
	
    public void testBeregner(){
    	//Udfylder testfelterne
    	solo.clickOnEditText(0);
    	solo.clickOnEditText(0);
    	solo.enterText(0, "235");
    	solo.clickOnEditText(1);
    	solo.clickOnEditText(1);
    	solo.enterText(1, "13.6");
    	solo.clickOnEditText(2);
    	solo.clickOnEditText(2);
    	solo.enterText(2, "6.4");
    	solo.clickOnButton(0);
    	
    	//Tager billede
    	solo.takeScreenshot();
    	
    	//Åbner menu
    	solo.clickOnMenuItem("Instillinger");
    	solo.waitForText("Instillinger", 1, 3000); //Venter på slidemenu animation
    	solo.clickOnText("Instillinger");
    	
    	//åbner instillinger
    	solo.assertCurrentActivity("Her er indstillinger", aktiviteter.Indstillinger.class);
    	solo.clickOnText("Doegndosis");
    	solo.enterText(0, "22");
    	solo.clickOnText("OK");
    	solo.goBack();
    	solo.goBack();
    	solo.clickOnButton(0);
    }
	
}