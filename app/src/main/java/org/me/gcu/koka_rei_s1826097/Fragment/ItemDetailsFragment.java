package org.me.gcu.koka_rei_s1826097.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.me.gcu.koka_rei_s1826097.R;

 public class ItemDetailsFragment extends Fragment {
     TextView txtTitle;
     TextView txtDescription;
     TextView txtTime;
     String title;
     String description;
     String pubDate;

    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details_fragment, container, false);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtTime = view.findViewById(R.id.txtTime);
        title = getArguments().getString("Title");
        description = getArguments().getString("Description");
        pubDate = getArguments().getString("Date");
        txtTitle.setText(title);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtDescription.setText(Html.fromHtml(description));
        }
        txtTime.setText(pubDate);
        return view;
    }
}