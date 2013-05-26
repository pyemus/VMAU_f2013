package fragmenter;

import com.androidquery.AQuery;

import dk.pyemus.blodsukkerapp_v3.R;



import aktiviteter.BlodsukkerMain_akt;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Guide_frag extends Fragment {

	public Guide_frag() {
	}

//	Button but;
	BlodsukkerMain_akt main;
	public View MyView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.guidefrag, container, false);
		ImageView pil = (ImageView)v.findViewById(R.id.next_pil);
		ImageView hjemknap = (ImageView)v.findViewById(R.id.hjem_knap);
		
		pil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				BlodsukkerMain_akt a = (BlodsukkerMain_akt) getActivity();
				a.Kald_næste_frag();
			}
			});
		
		hjemknap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BlodsukkerMain_akt a = (BlodsukkerMain_akt) getActivity();
				a.dismissAlleFragmenter();
				
//				Toast.makeText(getActivity(), 
//						"Kalder dismissAlleFragmenter", 
//						Toast.LENGTH_SHORT).show();
			}
		});
//		
//		Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation_arrow);
//		pil.startAnimation(rotation);
		MyView = v;
		return v;
	}
	
	@Override
	public void onDetach(){
		super.onDetach();
		try{
			BlodsukkerMain_akt a = (BlodsukkerMain_akt) getActivity();
			a.showStatusBar(null);
			Log.d("FragmentFulleScreen", "Kalder showStatusBar");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	


//	public void lul(View view) {
//
//		main.showStatusBar(view);
//	}

	// static Guide_frag newInstance() {
	// Guide_frag gf = new Guide_frag();
	// return gf;
	// }
	//
	// public View onCreateView(LayoutInflater infl, ViewGroup container,
	// Bundle savedInstanceState) {
	//
	// View v = infl.inflate(R.layout.guidefrag, container, false);
	// return v;
	// }

}
