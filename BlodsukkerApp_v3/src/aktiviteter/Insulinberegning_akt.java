package aktiviteter;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter;
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

public class Insulinberegning_akt extends SlidingActivity {

	ActionBar ab = null;
	private FragmentManager fm;
	public String note_input;
	AQuery aq = new AQuery(this);
	EditText e1, e2, e3, e4;
	String dogndosis;
	SharedPreferences myPreference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.aktivitet_fade_in, R.anim.aktivitet_fade_out);
		setContentView(R.layout.insulin);
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
		// aq.id(R.id.dogndosis).clicked(this,"click");
		aq.id(R.id.kulhydratmaengde).clicked(this, "click");
		aq.id(R.id.nuvaerende_blodsukker).clicked(this, "click");
		aq.id(R.id.oensket_blodsukker).clicked(this, "click");
		aq.id(R.id.beregn_knap).clicked(this, "click");
		myPreference = PreferenceManager.getDefaultSharedPreferences(this);
		// dogndosis_int = (TextView)findViewById(R.id.edittextstatus);

	}

	public void click(View view) {
		e1 = (EditText) findViewById(R.id.nuvaerende_blodsukker);
		e2 = (EditText) findViewById(R.id.oensket_blodsukker);
		e3 = (EditText) findViewById(R.id.kulhydratmaengde);
		// e4=(EditText)findViewById(R.id.dogndosis);
		dogndosis = myPreference.getString("dogndosis", "");
		if (view.getId() == R.id.nuvaerende_blodsukker) {
			// Toast.makeText(this, "Nuværende blodsukker",
			// Toast.LENGTH_SHORT).show();
			aq.id(R.id.nuvaerende_blodsukker).text("");
			e1.setTextColor(Color.BLACK);
			// aq.id(R.id.oensket_blodsukker).textColor(000000);
		} else if (view.getId() == R.id.oensket_blodsukker) {
			aq.id(R.id.oensket_blodsukker).text("");
			e2.setTextColor(Color.BLACK);
		} else if (view.getId() == R.id.kulhydratmaengde) {
			aq.id(R.id.kulhydratmaengde).text("");
			e3.setTextColor(Color.BLACK);
		} else if (view.getId() == R.id.beregn_knap) {
			if (aq.id(R.id.kulhydratmaengde).getText().toString().equals("")
					|| aq.id(R.id.kulhydratmaengde).getText().toString()
							.equals("Fx 200")) {
				Toast.makeText(this, "Udfyld venligst kulhydratmængden",
						Toast.LENGTH_SHORT).show();
			} else if (aq.id(R.id.nuvaerende_blodsukker).getText().toString()
					.equals("")
					|| aq.id(R.id.nuvaerende_blodsukker).getText().toString()
							.equals("Fx 10.6")) {
				Toast.makeText(this, "Udfyld venligst nuværende blodsukker",
						Toast.LENGTH_SHORT).show();
			} else if (aq.id(R.id.oensket_blodsukker).getText().toString()
					.equals("")
					|| aq.id(R.id.oensket_blodsukker).getText().toString()
							.equals("Fx 6.4")) {
				Toast.makeText(this, "Udfyld venligst ønsket blodsukker",
						Toast.LENGTH_SHORT).show();
			} else if (dogndosis.equals("") || dogndosis.equals(null)) {
				Toast.makeText(this,
						"Sæt døgndosis. Menu-->Instillinger-->Døgndosis",
						Toast.LENGTH_LONG).show();
			} else {

				insulinberegning();
			}

		} else {
			Log.d("Click", "kunne ikke håndteres");
		}
	}

	public void insulinberegning() {
		// Toast.makeText(this, "Her er beregningen",
		// Toast.LENGTH_SHORT).show();
		double kulhydratmængde = Double.parseDouble(aq
				.id(R.id.kulhydratmaengde).getText().toString());
		double døgndosis = Double.parseDouble(myPreference.getString(
				"dogndosis", ""));
		double nuværende = Double.parseDouble(aq.id(R.id.nuvaerende_blodsukker)
				.getText().toString());
		double ønsket = Double.parseDouble(aq.id(R.id.oensket_blodsukker)
				.getText().toString());

		int madberegning = (int) Math.round(kulhydratmængde)
				/ (500 / (int) Math.round(døgndosis));
		int korrektion = (((int) Math.round(nuværende)) - ((int) Math
				.round(ønsket))) / (100 / (int) Math.round(døgndosis));

		int insulinResultat = madberegning + korrektion;

		// Toast.makeText(this, madberegning+","+korrektion+","+insulinResultat,
		// Toast.LENGTH_LONG).show();

		String visResultat = Integer.toString(insulinResultat);
		aq.id(R.id.rela2).visible();
		aq.id(R.id.behøver).visible();
		aq.id(R.id.udregning).text(visResultat).visible();
		aq.id(R.id.insulin).visible();

		aq.id(R.id.beregner).visible();
		String kul1, dogn1, nuv1, ønsk1;
		kul1 = aq.id(R.id.kulhydratmaengde).getText().toString();
		dogn1 = myPreference.getString("dogndosis", "");
		nuv1 = aq.id(R.id.nuvaerende_blodsukker).getText().toString();
		ønsk1 = aq.id(R.id.oensket_blodsukker).getText().toString();
		aq.id(R.id.regnestykke)
				.text(kul1 + "g. kulhydrat/(500/" + dogn1 + "døgndosis)= "
						+ madberegning + "  IE\n" + "(" + nuv1 + "-" + ønsk1
						+ ")/(100/" + dogn1 + "døgndosis) = " + korrektion
						+ "  IE korrektion\n" + "---------------\n" + "Total: "
						+ (madberegning + korrektion) + "  IE").visible();
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

			fm.beginTransaction()
					.replace(R.id.content_frame, new MenuListFragment())
					.commit();
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

}
