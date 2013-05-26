package fragmenter;

import com.androidquery.AQuery;

import dk.pyemus.blodsukkerapp_v3.R;

import aktiviteter.BlodsukkerMain_akt;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Guide_frag3 extends Fragment {
	
	public Guide_frag3() {
		
	}

	ImageView tekst, pil;
	ImageButton noteknap;
//	public String note = "";
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.guidefrag3, container, false);

		 pil = (ImageView) v.findViewById(R.id.next_pil);
		 tekst = (ImageView) v.findViewById(R.id.next_tekst);
		 noteknap = (ImageButton) v.findViewById(R.id.note_knap);
		pil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				BlodsukkerMain_akt a = (BlodsukkerMain_akt) getActivity();
				a.Kald_hovedskærm();
			}
		});
		
		tekst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				BlodsukkerMain_akt a = (BlodsukkerMain_akt) getActivity();
				a.Kald_hovedskærm();
			}
		});
		
		
		noteknap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bygDialog();
			}
		});
		
        //Animation settet bruges til flere former for animationer (scale,rotate,translate)
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation r = new RotateAnimation(0f, -90f,44,50);
        r.setDuration(2000);
        animationSet.setFillEnabled(true);//Animationen bliver i den roterede state
        animationSet.setFillAfter(true);
        animationSet.addAnimation(r);
        pil.setAnimation(animationSet);
        
        

		return v;
	}
	
	public void bygDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Indtast note");

		// Set up the input
		final EditText input = new EditText(getActivity());
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT );
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	BlodsukkerMain_akt a = (BlodsukkerMain_akt) getActivity();
		         a.note_input= input.getText().toString();
		    }
		});

		builder.show();
	}
}
