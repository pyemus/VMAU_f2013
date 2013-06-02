package aktiviteter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import com.androidquery.AQuery;
import com.bugsense.trace.BugSenseHandler;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

import database.DBAdapter;

import dk.pyemus.blodsukkerapp_v3.R;

import fragmenter.Guide_frag;
import fragmenter.Guide_frag2;
import fragmenter.Guide_frag3;
import fragmenter.MenuListFragment;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.telephony.gsm.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import eu.erikw.*;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class BlodsukkerMain_akt extends SlidingActivity {
	ActionBar ab=null;
	private FragmentManager fm;
	public String note_input;
	TextView note_inn_textview;
	DBAdapter db = new DBAdapter(this);
	SimpleCursorAdapter blodsukkervaerdier;
	PullToRefreshListView blodsukkerliste ;
	boolean tipsVedOpstart, usb;
	Editor set_usb;
	SharedPreferences myPreference;
	String lavtBlodsukkerNiveau,værdi;
	
	String[] menuItems = {"Opdater note","Slet måling"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(getApplicationContext(), "25f4284a"); // bugsense ID

		setContentView(R.layout.blodsukker_main);
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
		
		blodsukkerliste=(PullToRefreshListView) findViewById(R.id.listView1);
		registerForContextMenu(blodsukkerliste);
//		getYFraction();
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		note_inn_textview.setText(""+year);
		
		myPreference = PreferenceManager.getDefaultSharedPreferences(this); 

//		USB_broadcastReceiver usbb = new USB_broadcastReceiver();
//		 // listen for new devices
//		 IntentFilter filter = new IntentFilter();
//		 filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
//		 registerReceiver(usbb., filter);
		
		// Sæt en kliklistener
		blodsukkerliste.setOnItemClickListener(
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> listView, View view,
							int position, long id) {
						// Get the cursor, positioned to the corresponding row in the
						// result set
//					Cursor cursor = (Cursor) listView.getItemAtPosition(position);
//
//						// Get titel & artist fra databasen.
//					titel = cursor.getString(cursor.getColumnIndexOrThrow("titel"));
//					artist = cursor.getString(cursor
//							.getColumnIndexOrThrow("artist"));

						Toast.makeText(
								getApplicationContext(),
								"Langt tryk for menu" , Toast.LENGTH_SHORT).show();
					}
				});
		
		//Lang tryk listener
//		blodsukkerliste.setOnItemLongClickListener(
//				new OnItemLongClickListener() {
//
//					@Override
//					public boolean onItemLongClick(AdapterView<?> listView, View view,
//							int position, long id) {
////						db.deleteSong(position);
//						Toast.makeText(getApplicationContext(),"Lang tryk på "+position , Toast.LENGTH_SHORT).show();
//						return false;
//					}
//				});
		
		blodsukkerliste.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// Your code to refresh the list contents goes here

				// Make sure you call listView.onRefreshComplete()
				// when the loading is done. This can be done from here or any
				// other place, like on a broadcast receive from your loading
				// service or the onPostExecute of your AsyncTask.

				// For the sake of this sample, the code will pause here to
				// force a delay when invoking the refresh
//				Toast.makeText(getBaseContext(), "I onRefresh", Toast.LENGTH_SHORT).show();
//				refreshUsbDevices();
				blodsukkerliste.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						blodsukkerliste.onRefreshComplete();
//						boolean nogetIListen =;
						if(blodsukkerliste.getCount()!=0){
						Cursor cursor = (Cursor) blodsukkerliste.getItemAtPosition(1);

						værdi = cursor.getString(cursor.getColumnIndexOrThrow("blodsukkervaerdi"));
						
						double double_værdi=Double.parseDouble(værdi);
						//Hvis blodsukkerniveauet er under 4, er det lavt. Sendt sms
						if(double_værdi<4.0){
							send_nød_SMS();
						}
						}
					}
				}, 500);
			}
		});
		
		
		tipsVedOpstart = myPreference.getBoolean("tips", true);
		if (tipsVedOpstart == true) {
			tips();
		}
		

  		fm.beginTransaction()
		.replace(R.id.content_frame, new MenuListFragment())
		.commit();
  		
  		seDB();
	}
	
	
	public void tips(){
        //set up dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.tips_dialog);
        dialog.setTitle(getString(R.string.titel_tips));
        dialog.setCancelable(true);
        
        //set tilfældigt tips textview
		Random rand = new Random();
		int laveste = 1;
		int højeste = 6;
		int myRandom = rand.nextInt(højeste-laveste) + laveste;
		TextView text = (TextView) dialog.findViewById(R.id.TextView01);

		if (myRandom == 1) {
			text.setText(getString(R.string.tips1));
		} else if (myRandom == 2) {
			text.setText(getString(R.string.tips2));
		} else if (myRandom == 3) {
			text.setText(getString(R.string.tips3));
		} else if (myRandom == 4) {
			text.setText(getString(R.string.tips4));
		} else if(myRandom == 5) {
			text.setText(getString(R.string.tips5));
		}
//		Toast.makeText(getApplicationContext(), ""+myRandom, Toast.LENGTH_SHORT).show();
        //set image view
        ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
        img.setImageResource(R.drawable.idepaere);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        
//        //set knap
//        AQuery aq = new AQuery(this);
//        aq.id(R.id.dialog_knap).clicked(this,"tryk");
//        public void tryk(){
//        	
//        }
	}
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    //Test for fragement er synligt...ellers kaldes slidemenu efter skærm sluk/tænd
	    overridePendingTransition(R.anim.aktivitet_fade_in, R.anim.aktivitet_fade_out);
	    getSlidingMenu().toggle(true);
	}
	
//	//*************CONTEXT menu ved langt klik*****************
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Ret måling");
		menu.add(0, v.getId(), 0, "Opdatér Note");
		menu.add(0, v.getId(), 0, "Slet Måling");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int itemsTotal=blodsukkerliste.getCount()-1;
		
		if (item.getTitle().equals("Opdatér Note")) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Noteændring");

			final EditText input = new EditText(this);
			input.setHintTextColor(Color.GRAY);
			input.setHint("Skriv din ændring her");
			builder.setView(input);

			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    	
			    	 String m_Text = input.getText().toString();
						db.open();
						db.updateMaalingNote(info.position, m_Text);
						db.close();
			    }
			});
			builder.show();
			
			
		} else if (item.getTitle().equals("Slet Måling")) {

//			Cursor cursor = (Cursor) blodsukkerliste.getItemAtPosition();
//			værdi = cursor.getString(cursor.getColumnIndexOrThrow("blodsukkervaerdi"));
			
			db.open();
			db.sletMaaling(itemsTotal-info.position+1);
			db.close();
//			Toast.makeText(getApplication(), "Måling slettet "+info.position, Toast.LENGTH_SHORT).show();
//			Toast.makeText(getApplication(), "Item:"+item +", Info:"+info.position, Toast.LENGTH_SHORT).show();
//			Toast.makeText(getApplication(), ""+info.position+", "+blod, Toast.LENGTH_SHORT).show();
			
		} else {
			return false;
		}
		return true;
	}

	public void opdaterNote(int id) {
		Toast.makeText(getApplication(), "Opdaterer", Toast.LENGTH_SHORT).show();
	}


	
	   // Kaldes én gang for generelt at forberede menuen 
	
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(Menu.NONE, 102, Menu.NONE, "Smid i databasen").setIcon(android.R.drawable.btn_plus);
			menu.add(Menu.NONE, 101, Menu.NONE, "Se databasen").setIcon(android.R.drawable.btn_dropdown);
			menu.add(Menu.NONE, 107, Menu.NONE, "Skjul databasen").setIcon(android.R.drawable.star_off);
			menu.add(Menu.NONE, 108, Menu.NONE, "Slet databasen").setIcon(android.R.drawable.ic_menu_delete);
			return true;
		}
		
		//** Kaldes hver gang menuen skal vises 
		@Override
		public boolean onPrepareOptionsMenu(Menu menu) {
			super.onPrepareOptionsMenu(menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == 101) {
				seDB();
			} else if (item.getItemId() == 102) {
			//		---Tilføj en blodsukkermåling----
				db.open();
				long id = db.maaling(1337, 22042013, 4.1, "før måltid", "det gik godt");
				db.close();
				seDB();
			} 
			else if (item.getItemId() == 107) {
				blodsukkerliste.setVisibility(View.INVISIBLE);
			} 
			else if (item.getItemId() == 108) {
				db.deleteEntireDB();
				blodsukkerliste.setVisibility(View.INVISIBLE);
			} 
			//Hvis der trykkes i actionbaren
			else if(item.getItemId()==android.R.id.home){
		    	  getSlidingMenu().showMenu();//Viser Slidemenu,
		    	  
			}
			
			else {
				Log.d("Menu","Ikke håndteret");
			}
			return true;
		}
		
		public void seDB(){
			db.open();
			if(!db.checkForDbContent()){
				Toast.makeText(getApplicationContext(),
						"Tom\nDer er ikke målt noget blodsukkerværdier endnu" , Toast.LENGTH_LONG).show();
			}
			
			
			// db.open();
			Cursor cursor = db.getAlleMaalingerIterated();
			
			// The desired columns to be bound
			String[] columns = new String[] { 
					DBAdapter.KEY_ROWID,
					DBAdapter.KEY_TID, 
					DBAdapter.KEY_DATO, 
					DBAdapter.KEY_BLODSUKKERVAERDI,
					DBAdapter.KEY_BLODSUKKERSTATUS,
					DBAdapter.KEY_NOTE };

			// the XML defined views which the data will be bound to
			int[] to = new int[] { 
					R.id.maalingnr, 
					R.id.tid, 
					R.id.dato, 
					R.id.blodvaerdi,
					R.id.blodstatus,
					R.id.note};

			blodsukkervaerdier = new SimpleCursorAdapter(this, R.layout.db_celle_info, cursor,
					columns, to, 0);
			
			// Find &Sæt listadaptoren
			blodsukkerliste.setAdapter(blodsukkervaerdier);
			blodsukkerliste.setVisibility(View.VISIBLE);
			db.close();
		}
	
	public void clickkkk(View view) throws IOException, InterruptedException{
		
		//Prøver at fjerne status & actionbar
		try{
			hideStatusBar(view);
		}catch (Exception e){
			e.printStackTrace();
		}

		
		if(fm.findFragmentById(R.id.guidefrag)==null ){
			FragmentTransaction ft = fm.beginTransaction();
			Guide_frag gf = new Guide_frag();
			ft
//			.setCustomAnimations(getYFraction(), R.anim.slide_out_top)
			.setCustomAnimations(R.anim.slide_in_buttom, R.anim.slide_out_top, R.anim.slide_in_top,R.anim.slide_out_buttom)
			.replace(R.id.main_layout, gf, "Guide_frag")
			.addToBackStack(null)
			.commit();
			
			LayoutParams p;
			view.getX();
		}
		else{
			showStatusBar(view);
		}
	}
	
//	public void getYFraction() {
//	    final WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
//	    int height = wm.getDefaultDisplay().getHeight();
//	    
//	    Toast.makeText(getBaseContext(), String.valueOf(height), Toast.LENGTH_LONG).show();
//	    ValueAnimator va = ValueAnimator.ofInt(height,0);
//	    va.setDuration(1000);
//	    va.start();
//	}


	
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
	


	public void Kald_næste_frag() {

		Guide_frag2 gf2 = new Guide_frag2();
		fm.findFragmentById(R.id.guidefrag2);
		FragmentTransaction ft = fm.beginTransaction();
				
		ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_ind_left, R.anim.slide_out_right)
			.replace(R.id.main_layout, gf2, "Guide_frag2")
			.addToBackStack(null)
			.commit();

	}
	
	public void Kald_næste_frag2() {

		Guide_frag3 gf3 = new Guide_frag3();
		fm.findFragmentById(R.id.guidefrag3);
		FragmentTransaction ft = fm.beginTransaction();
				
		ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_ind_left, R.anim.slide_out_right)
			.replace(R.id.main_layout, gf3, "Guide_frag3")
			.addToBackStack("hej")
			.commit();
	}
	
	public void Kald_hovedskærm(){
		Guide_frag3 gf3 = new Guide_frag3();
		
//		if(!note_inn_textview.getText().equals("")){
//			note_inn_textview.setText("Tilføjet note: "+note_input);
//		}

		fm.findFragmentById(R.id.guidefrag3);
		FragmentTransaction ft = fm.beginTransaction();
				
		ft.setCustomAnimations(R.anim.slide_out_buttom, R.anim.slide_out_buttom)
			.replace(R.id.main_layout, gf3)
			.addToBackStack(null)
			.commit();
		
		//Fjern alle fragmenter fra backstacken efter animation
		Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	        	 dismissAlleFragmenter();
	         } 
	    }, 1000); 
	}
	
	public void dismissAlleFragmenter(){
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}
	
	
	
	public void send_nød_SMS(){
		String pårørendeNr =myPreference.getString("paarorende", "");
		  try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(pårørendeNr, 
									   null, 
									   "Jeg har et lavt blodsukker på: "+værdi+" giv mig et ring", 
									   null, 
									   null);
			Toast.makeText(getApplicationContext(), "SMS Sendt!",
						Toast.LENGTH_LONG).show();
		  } catch (Exception e) {
			Toast.makeText(getApplicationContext(),
				"SMS fejlede, tjek nummeret i Menu-->Instillinger-->Nød SMS og prøv igen",
				Toast.LENGTH_LONG).show();
			e.printStackTrace();
		  }
		
	}
	
	public void slideTilbage(){
		getSlidingMenu().toggle(true);
	}


}
