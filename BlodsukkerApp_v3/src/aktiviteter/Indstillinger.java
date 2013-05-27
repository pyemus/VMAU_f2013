package aktiviteter;

import com.bugsense.trace.BugSenseHandler;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import dk.pyemus.blodsukkerapp_v3.R;

public class Indstillinger extends PreferenceActivity {
	private ListPreference mListPreference;
	public static final String KEY_LIST_PREFERENCE = "listPref";
	private CheckBoxPreference usb_next_CheckBox, usb_Box;
	public EditTextPreference dogndosis,paarorende;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(getApplicationContext(), "25f4284a"); // bugsense ID
		addPreferencesFromResource(R.xml.indstillinger);
		mListPreference = (ListPreference) getPreferenceScreen()
				.findPreference(KEY_LIST_PREFERENCE);

		usb_next_CheckBox = (CheckBoxPreference) getPreferenceManager().findPreference("usb_next");
		usb_Box = (CheckBoxPreference) getPreferenceManager().findPreference("usb");
		dogndosis = (EditTextPreference)getPreferenceManager().findPreference("dogndosis");
		paarorende = (EditTextPreference)getPreferenceManager().findPreference("paarorende");
		
		usb_next_CheckBox
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference arg0) {
						usb_Box.setChecked(false);
						return true;
					}
				});

		usb_Box.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				usb_next_CheckBox.setChecked(false);
				return true;
			}
		});
		
//		dogndosis.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//			@Override
//			public boolean onPreferenceChange(Preference preference, Object newValue){
//				usb_next_CheckBox.setChecked(false);
//				return true;
//			}
//		});
		
	}

	public boolean onPreferenceChange(Preference preference, Object newValue) {

		return true;
	}

	int onPrepareOptionsMenu = 0;
}
