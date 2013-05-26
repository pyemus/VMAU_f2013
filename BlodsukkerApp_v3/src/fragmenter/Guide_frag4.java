package fragmenter;

import com.androidquery.AQuery;

import dk.pyemus.blodsukkerapp_v3.R;

import aktiviteter.BlodsukkerMain_akt;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Guide_frag4 extends Fragment {

	public Guide_frag4() {

	}

	Button but;
	BlodsukkerMain_akt main;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.guidefrag4, container, false);

		return v;
	}
}
