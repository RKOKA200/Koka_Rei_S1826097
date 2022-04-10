package org.me.gcu.koka_rei_s1826097;
// @Koka_Rei_S1826097
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;

import org.me.gcu.koka_rei_s1826097.Fragment.ItemDetailsFragment;
import org.me.gcu.koka_rei_s1826097.Fragment.ItemMapsFragment;
import org.me.gcu.koka_rei_s1826097.Modules.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    ItemMapsFragment itemMapsFragment;
    ItemDetailsFragment itemDetailsFragment;
    String title;
    String description;
    String location;
    String pubDate;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        title = getIntent().getStringExtra("Title");
        description = getIntent().getStringExtra("Description");
        location = getIntent().getStringExtra("Location");
        pubDate = getIntent().getStringExtra("Date");
        // Split the location coordinates and put the latitude and longitude separately.
        String[] coords = location.split(" ");
        latitude = Double.parseDouble(coords[0]);
        longitude = Double.parseDouble(coords[1]);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Call Maps Fragment and ItemDetails Fragment
        callMapsFragment();
        callItemDetailsFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Call ItemDetails fragment and send data to the fragment
    private void callItemDetailsFragment() {
        itemDetailsFragment = new ItemDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Title", title);
        bundle.putString("Description", description);
        bundle.putString("Date", pubDate);
        itemDetailsFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layoutFrameSecond, itemDetailsFragment).commit();
    }

    // Call Maps fragment and send data to the fragment
    private void callMapsFragment() {
        itemMapsFragment = new ItemMapsFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("Lat", latitude);
        bundle.putDouble("Lng", longitude);
        bundle.putString("Title", title);
        itemMapsFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layoutFrameFrist, itemMapsFragment).commit();

    }
}