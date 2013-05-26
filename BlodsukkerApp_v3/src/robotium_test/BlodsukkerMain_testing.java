package robotium_test;

import dk.pyemus.blodsukkerapp_v3.*;
import dk.pyemus.blodsukkerapp_v3.R.id;
import aktiviteter.BlodsukkerMain_akt;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.RobotiumUtils;
import com.jayway.android.robotium.solo.Solo;
public class BlodsukkerMain_testing extends ActivityInstrumentationTestCase2<BlodsukkerMain_akt>{

	private Solo solo;

	public BlodsukkerMain_testing() {
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
    
    
    public void testBlodsukkerGuide()throws Exception {
    	solo.clickOnButton(0);
    	assertTrue("guidefrag skal dukke op", solo.waitForFragmentByTag("Guide_frag", 3000));
    	
    	fragmenter.Guide_frag gf = (fragmenter.Guide_frag)
    			 					getActivity()
    			 				   .getFragmentManager()
    							   .findFragmentByTag("Guide_frag");
    	assertNotNull(gf);
    	getInstrumentation().waitForIdleSync();
    	assertNotNull(gf.getView());
    	
        boolean expected = true;
        boolean actual = solo.waitForFragmentByTag("Guide_frag", 3000);
        assertEquals("Resultatet er ikke godkendt", expected, actual);
    	
    	solo.clickOnView(gf.getView().findViewById(R.id.next_pil));
    	
    	assertTrue("guidefrag2 skal dukke op", solo.waitForFragmentByTag("Guide_frag2", 3000));
    	
    	fragmenter.Guide_frag2 gf2 = (fragmenter.Guide_frag2)
					getActivity()
				   .getFragmentManager()
				   .findFragmentByTag("Guide_frag2");
    	assertNotNull(gf2);
    	getInstrumentation().waitForIdleSync();
    	assertNotNull(gf2.getView());
    	assertNotNull(gf2.getView().findViewById(R.id.next_pil));
    	
        boolean expected2 = true;
        boolean actual2 = solo.waitForFragmentByTag("Guide_frag2", 3000);
        assertEquals("Resultatet er ikke godkendt", expected2, actual2);
       	solo.clickOnView(gf2.getView().findViewById(R.id.next_pil));
       	
       	
       	assertTrue("guidefrag3 skal dukke op", solo.waitForFragmentByTag("Guide_frag3", 3000));
    	fragmenter.Guide_frag3 gf3 = (fragmenter.Guide_frag3)
					getActivity()
				   .getFragmentManager()
				   .findFragmentByTag("Guide_frag3");
    	getInstrumentation().waitForIdleSync();
    	
    	solo.clickOnImage(R.id.add_note);
    	solo.typeText(0, "Dette er en ny note");
    	solo.clickOnText("OK");
        boolean expected3 = true;
        boolean actual3 = solo.waitForFragmentByTag("Guide_frag3", 3000);
        assertEquals("Resultatet er ikke godkendt", expected3, actual3);
       	solo.clickOnView(gf3.getView().findViewById(R.id.next_pil));
    }
    
    public void testDatabase()throws Exception {
    	solo.clickOnMenuItem("Slet databasen");
    	solo.clickOnMenuItem("Smid i databasen");
    	solo.clickOnMenuItem("Smid i databasen");
    	solo.clickOnMenuItem("Se datababsen");
    	
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Vi søger efter det forventede
        boolean actual = solo.searchText("Måling");
        //Hævder om resultatet er godkendt 
        assertEquals("Resultatet er ikke godkendt", expected, actual);
    }

    
    public void testTilfoejNoteTilDBMaaling()throws Exception {
    	solo.clickOnMenuItem("Se datababsen");
    	//Tager en måling og opdaterer den tilhørerne tekst
    	solo.clickLongOnText("Måling:");
    	solo.clickOnText("Opdatér Note");
    	solo.typeText(0, "Dette er en ny note");
    	solo.clickOnText("OK");
    	solo.clickOnMenuItem("Se datababsen");
    	
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Vi søger efter det forventede
        boolean actual = solo.searchText("Dette er en ny note");
        //Hævder om resultatet er godkendt 
        assertEquals("Resultatet er ikke godkendt", expected, actual);
        solo.takeScreenshot();
//        BlodsukkerGuide();
    }
    
    
}
