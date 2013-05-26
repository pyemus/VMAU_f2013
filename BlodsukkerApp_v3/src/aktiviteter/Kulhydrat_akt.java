package aktiviteter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidquery.AQuery;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

import database.DBAdapter;
import dk.pyemus.blodsukkerapp_v3.R;
import eu.erikw.PullToRefreshListView;

import fragmenter.Appelsin_frag;
import fragmenter.Guide_frag2;
import fragmenter.MenuListFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class Kulhydrat_akt extends SlidingActivity implements FragmentManager.OnBackStackChangedListener{
	
	ActionBar ab=null;
	private FragmentManager fm;
	public String note_input;
	TextView note_inn_textview;
	DBAdapter db = new DBAdapter(this);
	SimpleCursorAdapter blodsukkervaerdier;
	PullToRefreshListView blodsukkerliste ;
    
	private Handler mHandler = new Handler();
    private boolean mShowingBack = false;
    
	private XYPlot mySimpleXYPlot;
	String[] menuItems = {"Opdater note","Slet måling"};

	@Override
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.aktivitet_fade_in, R.anim.aktivitet_fade_out);
		setContentView(R.layout.kulhydrat);
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
		
		
		ListView listview = (ListView) findViewById(R.id.appelsinListe);
		String[] values = new String[] { "Appelsin","Æble","Pære","Banan" };
		
		ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) {
	      list.add(values[i]);
	    }
	    
	    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
	    listview.setAdapter(adapter);
	    
	    
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) {
//	        	Toast.makeText(getApplicationContext(), "Du trykkede"+position, Toast.LENGTH_SHORT).show();
//	    		Appelsin_frag af = new Appelsin_frag();
//	    		fm.findFragmentById(R.id.appelsin_frag);
//	    		FragmentTransaction ft = fm.beginTransaction();
//	    				
//	    		ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_ind_left,R.anim.slide_out_right)
//	    			.replace(R.id.kulhydrat, af)
//	    			.addToBackStack(null)
//	    			.commit();
	    		
//	            if (savedInstanceState == null) {
	                // If there is no saved instance state, add a fragment representing the
	                // front of the card to this activity. If there is saved instance state,
	                // this fragment will have already been added to the activity.
	                getFragmentManager()
	                        .beginTransaction()
	                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_ind_left,R.anim.slide_out_right)
	                        .replace(R.id.kulhydrat, new fragmenter_kulhydrat.CardFrontFragment())
	                        .addToBackStack(null)
	                        .commit();
//	            } else {
//	                mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
//	            }
//	                getFragmentManager().addOnBackStackChangedListener(this);
	        }

	      });
	    
	}
	

	// Kaldes hver gang menuen skal vises
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu); // forbered systemets standardmenuer
		
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

	

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if(item.getItemId()==android.R.id.home){
		    	  getSlidingMenu().showMenu();//Viser Slidemenu,
		    	  
			  		fm.beginTransaction()
					.replace(R.id.content_frame, new MenuListFragment())
					.commit();
			}
			
			if(item.getItemId()==R.id.flipknap){
//				Log.d("Menu","Yes inde");
//				Appelsin_frag af = new Appelsin_frag();
				flipCard();
				
			}
			
			else {
				Log.d("Menu","Ikke håndteret");
			}
			return true;
		}

		
		 private void flipCard() {
		        if (mShowingBack) {
		            getFragmentManager().popBackStack();
		            return;
		        }

		        // Flip to the back.

		        mShowingBack = true;

		        // Create and commit a new fragment transaction that adds the fragment for the back of
		        // the card, uses custom animations, and is part of the fragment manager's back stack.

		        getFragmentManager()
		                .beginTransaction()

		                // Replace the default fragment animations with animator resources representing
		                // rotations when switching to the back of the card, as well as animator
		                // resources representing rotations when flipping back to the front (e.g. when
		                // the system Back button is pressed).
		                .setCustomAnimations(
		                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
		                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)

		                // Replace any fragments currently in the container view with a fragment
		                // representing the next page (indicated by the just-incremented currentPage
		                // variable).
		                .replace(R.id.kulhydrat, new fragmenter_kulhydrat.CardBackFragment())

		                // Add this transaction to the back stack, allowing users to press Back
		                // to get to the front of the card.
		                .addToBackStack(null)

		                // Commit the transaction.
		                .commit();

		        // Defer an invalidation of the options menu (on modern devices, the action bar). This
		        // can't be done immediately because the transaction may not yet be committed. Commits
		        // are asynchronous in that they are posted to the main thread's message loop.
		        mHandler.post(new Runnable() {
		            @Override
		            public void run() {
		                invalidateOptionsMenu();
		            }
		        });
		    }





		@Override
		public void onBackStackChanged() {
			// TODO Auto-generated method stub
	        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

	        // When the back stack changes, invalidate the options menu (action bar).
	        invalidateOptionsMenu();
			
		}
		
//		@Override
//		public void onBackPressed() {
//			Log.d("Navigation", "onBackPressed Called");
//			getSlidingMenu().showMenu();// Viser Slidemenu,
//
//			fm
//			.beginTransaction()
//			.replace(R.id.content_frame, new MenuListFragment())
//			.commit();
//		}

	}
