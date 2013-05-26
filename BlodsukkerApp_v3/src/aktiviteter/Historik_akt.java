	package aktiviteter;

	import java.io.IOException;
import java.util.Arrays;


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

	import eu.erikw.*;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class Historik_akt extends SlidingActivity {
	
		ActionBar ab=null;
		private FragmentManager fm;
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
	 
	        // Create a couple arrays of y-values to plot:
	        Number[] series1Numbers = {7.5, 8, 5, 2, 7, 4};
	        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
	 
	        // Turn the above arrays into XYSeries':
	        XYSeries series1 = new SimpleXYSeries(
	                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
	                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
	                "Series1");                             // Set the display title of the series
	 
	        // same as above
	        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
	 
	        // Create a formatter to use for drawing a series using LineAndPointRenderer:
	        LineAndPointFormatter series1Format = new LineAndPointFormatter(
	                Color.rgb(0, 200, 0),                   // line color
	                Color.rgb(0, 100, 0),                   // point color
	                null);                                  // fill color (none)
	 
	        // add a new series' to the xyplot:
	        mySimpleXYPlot.addSeries(series1, series1Format);
	        
	        // same as above:
	        mySimpleXYPlot.addSeries(series2,
	                new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100), null));
	 
	        // reduce the number of range labels
	        mySimpleXYPlot.setTicksPerRangeLabel(3);
	 
	        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
	        // To get rid of them call disableAllMarkup():
	        mySimpleXYPlot.disableAllMarkup();
	        //****************************************************************************************


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
			    	  
				  		fm.beginTransaction()
						.replace(R.id.content_frame, new MenuListFragment())
						.commit();
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
			    	  
				  		fm.beginTransaction()
						.replace(R.id.content_frame, new MenuListFragment())
						.commit();
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
