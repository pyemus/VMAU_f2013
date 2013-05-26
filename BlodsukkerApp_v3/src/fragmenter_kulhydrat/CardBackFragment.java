package fragmenter_kulhydrat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dk.pyemus.blodsukkerapp_v3.R;

//A fragment representing the back of the card.
public class CardBackFragment extends Fragment {
    public CardBackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_back, container, false);
    }
}