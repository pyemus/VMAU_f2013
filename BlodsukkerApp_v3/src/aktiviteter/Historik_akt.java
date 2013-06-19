	package aktiviteter;

	import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.apache.http.client.CircularRedirectException;


	import com.androidquery.AQuery;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

	import database.DBAdapter;
	
	import dk.pyemus.blodsukkerapp_v3.R;

	import fragmenter.MenuListFragment;

	import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
		
	import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.*;
import com.bugsense.trace.BugSenseHandler;

	import eu.erikw.*;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class Historik_akt extends SlidingActivity {
	
		ActionBar ab=null;
		public FragmentManager fm;
		public String note_input;
		TextView note_inn_textview;
		DBAdapter db = new DBAdapter(this);
		SimpleCursorAdapter blodsukkervaerdier;
		PullToRefreshListView blodsukkerliste ;
		
		private XYPlot mySimpleXYPlot;
		String[] menuItems = {"Opdater note","Slet måling"};

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			BugSenseHandler.initAndStartSession(getApplicationContext(), "25f4284a"); // bugsense ID
			overridePendingTransition(R.anim.aktivitet_fade_in, R.anim.aktivitet_fade_out);
			setContentView(R.layout.historik);
			setBehindContentView(R.layout.content_frame);
			getSlidingMenu().setBehindOffset(100);
			getSlidingMenu().setMode(SlidingMenu.LEFT);
			


			AQuery aq = new AQuery(this);
			fm = getFragmentManager();
			ab = getActionBar();
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setIcon(R.drawable.bayer);
			ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#4980F5"));//blå farve til actionbar
			ab.setBackgroundDrawable(colorDrawable);
			aq.id(R.id.button1).clicked(this,"clickkkk");
			note_inn_textview=(TextView) findViewById(R.id.note_input);
			
			//***********************Graf plotting*********************************
	        // initialize our XYPlot reference:
	        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
	        
	        //Viser X-aksen (Målinger) i int's
	        mySimpleXYPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
	        
	        // get rid of the decimal place on the display:
	        mySimpleXYPlot.setDomainValueFormat(new DecimalFormat("#"));
	        
	        //Sætter labels
	        mySimpleXYPlot.setDomainLabel("Målinger");
	        mySimpleXYPlot.setRangeLabel("Blodsukkerværdier");
	        mySimpleXYPlot.setTitle("Blodsukkerhistorik");
	        
			db.open();
			Cursor cursor = db.getAlleMaalinger();
			Number[] series1Numbers =new Number[cursor.getCount()];

			int i =0;
			if (cursor.moveToFirst()){//Starter fra sidste værdi i databasen (nyeste værdi)
				
				do{
					series1Numbers[i] =cursor.getDouble(3);//Søjlen (nr 3) med blodsukkerværdier som skal plottes
					i++;
				}while(cursor.moveToNext());
			}
			db.close();
	        
	        // Turn the above arrays into XYSeries':
	        if(series1Numbers!=null){
	        XYSeries series1 = new SimpleXYSeries(
	                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
	                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
	                "Blodsukkerkurve");                   // Set the display title of the series
	 

	        // Create a formatter to use for drawing a series using LineAndPointRenderer:
	        LineAndPointFormatter series1Format = new LineAndPointFormatter(
	        		Color.rgb(0, 0, 200),                   // line color
	        		Color.rgb(0, 0, 100),                   // point color
	                null);                                  // fill color (none)
	 
	        // add a new series' to the xyplot:
	        mySimpleXYPlot.addSeries(series1, series1Format);
	 
	        // reduce the number of range labels
	        mySimpleXYPlot.setTicksPerRangeLabel(3);
	 
	        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
	        // To get rid of them call disableAllMarkup():
	        mySimpleXYPlot.disableAllMarkup();
	        }else{
				Toast.makeText(getApplicationContext(),
						"Tom\nDer er ikke målt noget blodsukkerværdier endnu" , Toast.LENGTH_LONG).show();
	        }
	        //****************************************************************************************
	  		fm.beginTransaction()
			.replace(R.id.content_frame, new MenuListFragment())
			.commit();

		}
		

			// Kaldes hver gang menuen skal vises
			@Override
			public boolean onPrepareOptionsMenu(Menu menu) {
				super.onPrepareOptionsMenu(menu); // forbered systemets standardmenuer
				
				return true;
			}
		
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				if(item.getItemId()==android.R.id.home){
			    	  getSlidingMenu().showMenu();//Viser Slidemenu,
			    	  
				}
				else {
					Log.d("Menu","Ikke håndteret");
				}
				return true;
			}
			
			//Overskriver Optionsmenu knappen
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
			    if ( keyCode == KeyEvent.KEYCODE_MENU ) {
			        Log.d("MENU", "MENU pressed");
			    	  getSlidingMenu().showMenu();//Viser Slidemenu,
			    	  

			        return true;
			    }
			    return super.onKeyDown(keyCode, event);
			}
		
		public void clickkkk(View view) throws IOException, InterruptedException{
			
			//Prøver at fjerne status & actionbar
			try{
				hideStatusBar(view);
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		
		public void hideStatusBar(View view) {
			try
			   {((View)findViewById(R.id.guidefrag).getParent()).setVisibility(View.GONE);
			   }
			catch (Exception e) {}
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			ab.hide();
		}
		
		public void showStatusBar(View view){ 
			try
			   {((View)findViewById(R.id.guidefrag).getParent()).setVisibility(View.VISIBLE);
			   }
			catch (Exception e) {}
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			ab.show();
		}
		
		public void slideTilbage(){
			getSlidingMenu().toggle(true);
		}
		
//	@Override
//	public void onBackPressed() {
//		Log.d("Navigation", "onBackPressed Called");
//		getSlidingMenu().showMenu();// Viser Slidemenu,
//
//		fm
//		.beginTransaction()
//		.replace(R.id.content_frame, new MenuListFragment())
//		.commit();
//	}
		



		}
