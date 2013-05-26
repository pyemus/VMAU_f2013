package fragmenter_kulhydrat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import dk.pyemus.blodsukkerapp_v3.R;

//A fragment representing the front of the card.
public class CardFrontFragment extends Fragment {
    public CardFrontFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_card_front, container, false);
    }
    
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
////    	
//        menu.add(Menu.NONE,1,Menu.NONE,"Flip for info").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        
//    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	inflater.inflate(R.menu.flipmenu, menu);
        
    }
	
}
