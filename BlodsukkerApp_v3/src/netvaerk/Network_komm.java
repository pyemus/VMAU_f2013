package netvaerk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.ActionBar;
import android.app.FragmentManager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

import database.DBAdapter;
import dk.pyemus.blodsukkerapp_v3.R;
import fragmenter.MenuListFragment;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Network_komm extends SlidingActivity {

	ActionBar ab = null;
	private FragmentManager fm;
	public String note_input;
	AQuery aq = new AQuery(this);
	EditText e1, e2, e3, e4;
	String dogndosis;
	SharedPreferences myPreference;
	DBAdapter db = new DBAdapter(this);
	
	// Får fat i eksternt lager
	File sdKort = Environment.getExternalStorageDirectory();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.aktivitet_fade_in,	R.anim.aktivitet_fade_out);
		setContentView(R.layout.network);
		setBehindContentView(R.layout.content_frame);
		getSlidingMenu().setBehindOffset(100);
		getSlidingMenu().setMode(SlidingMenu.LEFT);

		AQuery aq = new AQuery(this);
		fm = getFragmentManager();
		ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setIcon(R.drawable.bayer);
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#4980F5"));// blå farve til actionbar
		ab.setBackgroundDrawable(colorDrawable);

		myPreference = PreferenceManager.getDefaultSharedPreferences(this);

		fm.beginTransaction()
		.replace(R.id.content_frame, new MenuListFragment())
		.commit();
		
		// init parse id og nøgle
		Parse.initialize(this, "SHCxZtPXyvPLTkC7FtYATlWhvRy4jAdtCaaAGYNx",
				                "U1MmOuvjO5lMNmhW9Xfgwi0KbXSi85ntBgBTSvRZ");

	}

	public void click(View view) {

		db.open();
		if (!db.checkForDbContent()) {
			Toast.makeText(getApplicationContext(),
					"Tom\nDer er ikke målt noget blodsukkerværdier endnu",
					Toast.LENGTH_LONG).show();
		}

		if (view.getId() == R.id.backup_parse_knap) {
			Toast.makeText(getApplicationContext(), "Parse", Toast.LENGTH_SHORT)
					.show();
			db.open();
			Cursor cursor = db.getAlleMaalinger();
			if (cursor.moveToFirst()) {
				do {
					sendMaalingerTilParse(cursor);
				} while (cursor.moveToNext());
			}
			db.close();
		} else if (view.getId() == R.id.backup_SD_knap) {
			sendMaalingerTilSD();
			// Toast.makeText(getApplicationContext(), "Gemmer på SD",
			// Toast.LENGTH_SHORT).show();
		} else if (view.getId() == R.id.restore_parse_knap) {
			// String herj ="data/data/"+getPackageName()
			// Toast.makeText(getApplicationContext(), "",
			// Toast.LENGTH_SHORT).show();
		} else if (view.getId() == R.id.restore_SD_knap) {
			hentMaalingerFraSD();
		}
	}

	public void sendMaalingerTilParse(Cursor cursor) {
		ParseObject maaleObjekt = new ParseObject("MaaleObjekt");
		maaleObjekt.put("id", cursor.getString(0));
		maaleObjekt.put("tid", cursor.getString(1));
		maaleObjekt.put("dato", cursor.getString(2));
		maaleObjekt.put("blodsukkervaerdi", cursor.getString(3));
		maaleObjekt.put("blodsukkerstatus", cursor.getString(4));
		maaleObjekt.put("note", cursor.getString(5));
		maaleObjekt.saveInBackground();
	}

	public void sendMaalingerTilSD() {
		if (erSDTilStede() == true) {
			Toast.makeText(getApplicationContext(), "Backup igang",
					Toast.LENGTH_SHORT).show();
			try {
				// Opretter mappe på SD kortet
				File folder = new File(sdKort.getAbsolutePath() + "/Blodsukker_database");
				folder.mkdirs();

				// Får fat i stierne til source/destination
				String sourceDBSti = "data/data/" + getPackageName() + "/databases";
				String destDBsti = sdKort.getAbsolutePath()	+ "/Blodsukker_database";

				// Serialiserer
				KopierDB(
						new FileInputStream(sourceDBSti + "/BlodsukkerDB"),
						new FileOutputStream(destDBsti + "/BlodsukkerDB_backup"));

				Toast.makeText(getApplicationContext(), "Backup færdig", Toast.LENGTH_SHORT).show();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Kan ikke finde SD kortet",	Toast.LENGTH_SHORT).show();
		}
	}

	public void hentMaalingerFraSkyen() {
		// ParseQuery query = new ParseQuery("GameScore");
		// query.getInBackground("xWMyZ4YEGZ", new GetCallback() {
		// public void done(ParseObject object, ParseException e) {
		// if (e == null) {
		// // object will be your game score
		// } else {
		// // something went wrong
		// }
		// }
		// });
	}
	
	public void hentMaalingerFraSD(){
		File blodDB =new File (sdKort.getAbsolutePath() + "/Blodsukker_database/BlodsukkerDB_backup");
		
		if (blodDB.exists()){
			Toast.makeText(getApplicationContext(), "Der findes en backup", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "Der findes ingen backup på SD", Toast.LENGTH_SHORT).show();
		}
	}

	public static boolean erSDTilStede() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public void KopierDB(InputStream IS, OutputStream OS) throws IOException {
		byte[] byteBuffer = new byte[1024];
		int length;
		while ((length = IS.read(byteBuffer)) > 0) {
			OS.write(byteBuffer, 0, length);
		}
		IS.close();
		OS.close();
	}

	// Kaldes hver gang menuen skal vises
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu); // forbered systemets standardmenuer

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			getSlidingMenu().showMenu();// Viser Slidemenu,

		} else {
			Log.d("Menu", "Ikke håndteret");
		}
		return true;
	}

	// Overskriver Optionsmenu knappen
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Log.d("MENU", "MENU pressed");
			getSlidingMenu().showMenu();// Viser Slidemenu,

			fm.beginTransaction()
					.replace(R.id.content_frame, new MenuListFragment())
					.commit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void slideTilbage(){
		getSlidingMenu().toggle(true);
	}

}
