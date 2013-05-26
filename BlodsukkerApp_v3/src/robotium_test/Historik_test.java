package robotium_test;

import java.util.ArrayList;

import aktiviteter.Historik_akt;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;

import com.jayway.android.robotium.solo.Solo;

import dk.pyemus.blodsukkerapp_v3.R;

public class Historik_test extends ActivityInstrumentationTestCase2<Historik_akt>{

	private Solo solo;

	public Historik_test() {
		super(Historik_akt.class);
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
    
    
    public void testVisGraf(){
    	//Henter alle synlige views i aktiviteten
    	ArrayList<View> view =solo.getCurrentViews();
    	boolean actual = false;
    	for (int i=0; i<view.size();i++){
    		Log.d("Alle views",""+view.get(i));
    		//Check hvis der findes nogle synlige views hvor android plot(grafen) indgår
    		if(view.get(i).toString().contains("androidplot")){
    			Log.d("VIEW", "Grafen er fundet");
    			//Vi markerer det aktuelle som sandt(fundet)
    			actual=true;
    		}
    	}
    	
    	//Vi forventer at resultat er der
        boolean expected = true;
        //Hævder om resultatet er godkendt 
        assertEquals("Resultatet er ikke godkendt", expected, actual);
    }
    
    public void testRigtigeGrafValues(){
    	
    }
	
	
}