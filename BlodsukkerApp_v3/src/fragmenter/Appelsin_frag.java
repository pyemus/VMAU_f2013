package fragmenter;



import database.DBAdapter;
import dk.pyemus.blodsukkerapp_v3.R;
import aktiviteter.BlodsukkerMain_akt;
import aktiviteter.Kulhydrat_akt;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;


public class Appelsin_frag extends Fragment implements FragmentManager.OnBackStackChangedListener{

	public Appelsin_frag() {

	}

	ImageView appelFlip;
    
    //A handler object, used for deferring UI operations.
    private Handler mHandler = new Handler();
    CheckBox mCheckBox1;
    // Whether or not we're showing the back of the card (otherwise showing the front).
    private boolean mShowingBack = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.appelsin_frag, container, false);


		
//        if (savedInstanceState == null) {
//            // If there is no saved instance state, add a fragment representing the
//            // front of the card to this activity. If there is saved instance state,
//            // this fragment will have already been added to the activity.
//            getFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.appelsin_frag, new CardFrontFragment())
//                    .commit();
//        } else {
//            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
//        }

        // Monitor back stack changes to ensure the action bar shows the appropriate
        // button (either "photo" or "info").
        getFragmentManager().addOnBackStackChangedListener(this);
        
//		appelFlip = (ImageView) v.findViewById(R.id.AppelsinImageViewIFragmentCardFront);
//		appelFlip.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				flipCard();
//			}
//		});
        setHasOptionsMenu(true);

        return v;
	}
	

	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE,1,Menu.NONE,"Flip for info").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
    }
	

//    //A fragment representing the front of the card.
//    public static class CardFrontFragment extends Fragment {
//        public CardFrontFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            return inflater.inflate(R.layout.fragment_card_front, container, false);
//        }
//    }
//
//    
//    //A fragment representing the back of the card.
//    public static class CardBackFragment extends Fragment {
//        public CardBackFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            return inflater.inflate(R.layout.fragment_card_back, container, false);
//        }
//    }

	@Override
	public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action bar).
//        invalidateOptionsMenu();
		
	}
	
//    public void flipCard() {
//        if (mShowingBack) {
//            getFragmentManager().popBackStack();
//            return;
//        }
//
//        // Flip to the back.
//
//        mShowingBack = true;
//
//        // Create and commit a new fragment transaction that adds the fragment for the back of
//        // the card, uses custom animations, and is part of the fragment manager's back stack.
//
//        getFragmentManager()
//                .beginTransaction()
//
//                // Replace the default fragment animations with animator resources representing
//                // rotations when switching to the back of the card, as well as animator
//                // resources representing rotations when flipping back to the front (e.g. when
//                // the system Back button is pressed).
//                .setCustomAnimations(
//                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
//                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)
//
//                // Replace any fragments currently in the container view with a fragment
//                // representing the next page (indicated by the just-incremented currentPage
//                // variable).
//                .replace(R.id.appelsin_frag, new CardBackFragment())
//
//                // Add this transaction to the back stack, allowing users to press Back
//                // to get to the front of the card.
//                .addToBackStack(null)
//
//                // Commit the transaction.
//                .commit();
//
//        // Defer an invalidation of the options menu (on modern devices, the action bar). This
//        // can't be done immediately because the transaction may not yet be committed. Commits
//        // are asynchronous in that they are posted to the main thread's message loop.
////        mHandler.post(new Runnable() {
////            @Override
////            public void run() {
////                invalidateOptionsMenu();
////            }
////        });
//    }
}
