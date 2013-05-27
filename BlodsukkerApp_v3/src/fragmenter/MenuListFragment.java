package fragmenter;


import java.util.Random;

import netvaerk.Network_komm;

import dk.pyemus.blodsukkerapp_v3.R;
import aktiviteter.BlodsukkerMain_akt;
import aktiviteter.Historik_akt;
import aktiviteter.Insulinberegning_akt;
import aktiviteter.Kulhydrat_akt;
import android.R.fraction;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MenuListFragment extends ListFragment  {
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 SampleAdapter adapter = new SampleAdapter(getActivity());
		 MenuItem mi,m2,m3;
			adapter.add(mi=new MenuItem("Hjem", android.R.drawable.ic_menu_revert));
			mi.overskrft="Hovedmenuer";
//			adapter.add(new MenuItem("", 0));
			adapter.add(new MenuItem("Historik", android.R.drawable.ic_menu_view));
			adapter.add(new MenuItem("Fødevareliste", R.drawable.food3));
			adapter.add(new MenuItem("Insulinberegning", R.drawable.calc2));
			adapter.add(new MenuItem("Backup", android.R.drawable.ic_menu_recent_history));
			
			
			adapter.add(m2 =new MenuItem("Hjælp", android.R.drawable.ic_menu_help));
			m2.overskrft = "Andet";
			adapter.add(new MenuItem("Om", R.drawable.about));
			adapter.add(new MenuItem("Indstillinger", android.R.drawable.ic_menu_manage));
			adapter.add(new MenuItem("Luk app", android.R.drawable.ic_menu_close_clear_cancel));
			
		setListAdapter(adapter);	

	}

	private class MenuItem {
		public String tag;
		public String overskrft;
		public int iconRes;
		public MenuItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}
	
	public class SampleAdapter extends ArrayAdapter<MenuItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			MenuItem it = getItem(position);
			int ikon = getItem(position).iconRes;
//			if (ikon==0) {
//				icon.setVisibility(View.INVISIBLE);
//				icon.setLayoutParams(new LinearLayout(LayoutParams.WRAP_CONTENT));
//			}else {
				icon.setImageResource(ikon);
//				icon.setVisibility(View.VISIBLE);				
//			}
				
			if(ikon==android.R.drawable.ic_menu_revert||ikon==android.R.drawable.ic_menu_help){
			TextView overskrift = (TextView) convertView.findViewById(R.id.overskrift);
			overskrift.setVisibility(View.VISIBLE);
			overskrift.setText(it.overskrft);
			overskrift.setTextColor(Color.WHITE);
			overskrift.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
			overskrift.setShadowLayer(2, 1, 1, Color.BLACK);
			}
			
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setTextColor(Color.WHITE);
			title.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
			title.setTypeface(null,Typeface.BOLD);
			title.setShadowLayer(2, 1, 1, Color.BLACK);
			title.setText(it.tag);
//			TextView header = new TextView(getActivity());
//			header.setText("asd");
//			title.setBackgroundResource(R.xml.list_header);

			
			return convertView;
		}

	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		switch (position) {
		case 0:
			Activity blod = getActivity();
			if(blod instanceof BlodsukkerMain_akt){
				((BlodsukkerMain_akt)blod).slideTilbage();
			}else{
				Intent i0 = new Intent(getActivity(),aktiviteter.BlodsukkerMain_akt.class);
				i0.setFlags(i0.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i0);
			}
			break;

		case 1:
			Activity hist=getActivity();
			if(hist instanceof Historik_akt){
				((Historik_akt)hist).slideTilbage();
			}else{
				Intent i1 =new Intent(getActivity() , aktiviteter.Historik_akt.class);
				i1.setFlags(i1.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i1);
			}
			break;
		
		case 2:
			Activity kul = getActivity();
			if(kul instanceof Kulhydrat_akt){
				((Kulhydrat_akt)kul).slideTilbage();
			}else{
				Intent i2 =new Intent(getActivity() , aktiviteter.Kulhydrat_akt.class);
				i2.setFlags(i2.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i2);
			}
			break;
		
		case 3:
			Activity insu = getActivity();
			if(insu instanceof Insulinberegning_akt){
				((Insulinberegning_akt)insu).slideTilbage();
			}else{
				Intent i3 =new Intent(getActivity() , aktiviteter.Insulinberegning_akt.class);
				i3.setFlags(i3.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i3);
			}
			break;
			
		case 4:
			Activity backup = getActivity();
			if(backup instanceof Network_komm){
				((Network_komm)backup).slideTilbage();
			}else{
				Intent i4 =new Intent(getActivity() , netvaerk.Network_komm.class);
				i4.setFlags(i4.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i4);
			}
			break;
		
		case 5:
	        Dialog dialog = new Dialog(getActivity());
	        dialog.setContentView(R.layout.om_help_dialog);
	        dialog.setTitle(getString(R.string.help_titel));
	        dialog.setCancelable(true);
	        
	        ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
	        img.setImageResource(R.drawable.help);
	        
	        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
	        text.setText(R.string.help_dialog);

	        dialog.show();
	        dialog.setCanceledOnTouchOutside(true);

			break;
				
		case 6:
	        Dialog om_dialog = new Dialog(getActivity());
	        om_dialog.setContentView(R.layout.om_help_dialog);
	        om_dialog.setTitle(getString(R.string.om_titel));
	        om_dialog.setCancelable(true);
	        
	        ImageView header_img = (ImageView) om_dialog.findViewById(R.id.ImageView01);
	        header_img.setImageResource(R.drawable.om);
	        
	        TextView om_text = (TextView) om_dialog.findViewById(R.id.TextView01);
	        om_text.setText(R.string.om_dialog);

	        om_dialog.show();
	        om_dialog.setCanceledOnTouchOutside(true);
			
	        break;
	    case 7:
			Intent i6 =new Intent(getActivity() , aktiviteter.Indstillinger.class);
			startActivity(i6);
			break;
				
		case 8:
			getActivity().moveTaskToBack(true);
			break;
		}
	
		
	}
	
}
