package robotium_test;

import aktiviteter.BlodsukkerMain_akt;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class Skabelon_testklasse extends ActivityInstrumentationTestCase2<BlodsukkerMain_akt>{

	private Solo solo;

	public Skabelon_testklasse() {
		super(BlodsukkerMain_akt.class);
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
    
    public void test(){
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Vi søger efter det forventede
        boolean actual = solo.searchText("En appelsin indeholder 10g. kulhydrater.");
        //Hævder om resultatet er godkendt 
        assertEquals("Resultatet er ikke godkendt", expected, actual);
    }
	
	
}