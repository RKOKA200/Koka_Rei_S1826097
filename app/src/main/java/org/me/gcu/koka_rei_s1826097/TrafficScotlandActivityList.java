package org.me.gcu.koka_rei_s1826097;
// @Koka_Rei_S1826097
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.koka_rei_s1826097.Adapters.RecycleViewAdapterTrafficScotland;
import org.me.gcu.koka_rei_s1826097.Helpers.InternetConnection;
import org.me.gcu.koka_rei_s1826097.Modules.Item;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TrafficScotlandActivityList extends AppCompatActivity {
    InternetConnection internetConnection;
    OkHttpClient client = new OkHttpClient();
    final String TAG = "";
    ArrayList<Item> channelItem;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    String title;
    private int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    Button btnChooseDate;
    TextView txtClearDate;
    public RecycleViewAdapterTrafficScotland currentIncidentsRecycleViewAdapter;
    String currentUrlSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_scotland);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.noDataLayout);
        btnChooseDate = findViewById(R.id.btnSelectDate);
        txtClearDate = findViewById(R.id.txtClear);
        internetConnection = new InternetConnection();
        // Get data from MainActivity
        currentUrlSource = getIntent().getStringExtra("URL");
        title = getIntent().getStringExtra("Title");
        // Set toolbar title
        getSupportActionBar().setTitle(title);
        checkInternetConnection();
        // Recycle View
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        // Populate Adapter with data
        currentIncidentsRecycleViewAdapter = new RecycleViewAdapterTrafficScotland(channelItem, TrafficScotlandActivityList.this);
        // Add adapter to RecycleView
        recyclerView.setAdapter(currentIncidentsRecycleViewAdapter);

        // Clear the date and restart the recycle View
        txtClearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIncidentsRecycleViewAdapter = new RecycleViewAdapterTrafficScotland(channelItem, TrafficScotlandActivityList.this);
                recyclerView.setAdapter(currentIncidentsRecycleViewAdapter);
                currentIncidentsRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        // Select Date
        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = (c.get((Calendar.YEAR)));
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                c.set(mYear, mMonth, mDay);

                datePickerDialog = new DatePickerDialog(TrafficScotlandActivityList.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String fmonth, fDate;
                                int month;
                                if (monthOfYear < 10 && dayOfMonth < 10) {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    fDate = "0" + dayOfMonth;
                                    String paddedMonth = String.format("%02d", month);
                                    // Populate button with the selected date
                                    btnChooseDate.setText(fDate + "-" + paddedMonth + "-" + (year));
                                    // Filter the RecycleView with the new selected date
                                    filter(fDate + "-" + paddedMonth + "-" + (year), true);
                                } else {
                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String paddedMonth = String.format("%02d", month);
                                    // Populate button with the selected date
                                    btnChooseDate.setText(dayOfMonth + "-" + (paddedMonth) + "-" + (year));
                                    // Filter the RecycleView with the new selected date
                                    filter(dayOfMonth + "-" + (paddedMonth) + "-" + (year), true);
                                }
                                mYear = year;
                                mMonth = monthOfYear + 1;
                                mDay = dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.searchItem);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter/Search the data with the new text
                filter(newText, false);
                return false;
            }
        });
        return true;
    }

    // Filter method with new date or new text
    private void filter(String text, Boolean isCheckByDate) {
        // creating a new array list to filter our data.
        ArrayList<Item> filteredlist = new ArrayList<>();

        for (Item item : channelItem) {
            if (isCheckByDate) {
                if (item.getPubDate().equals(text)) {
                    filteredlist.add(item);
                }
            } else {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }
        }
        if (filteredlist.isEmpty()) {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            currentIncidentsRecycleViewAdapter.filterList(filteredlist);
        }

    }

    // Check Internet Connection
    void checkInternetConnection() {
        if (internetConnection.checkInternetConnection(this)) {
            getDatafromRSSUrl();
        } else {
            Toast.makeText(TrafficScotlandActivityList.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    // convert String date to another string date
    // Date from XML format is different compared to user selected date from data picker.
    public String convertStringDateToAnotherStringDate(String stringdate, String stringdateformat, String returndateformat) {

        try {
            Date date = new SimpleDateFormat(stringdateformat).parse(stringdate);
            String returndate = new SimpleDateFormat(returndateformat).format(date);
            return returndate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    //
    void getDatafromRSSUrl() {
        Request request = new Request.Builder()
                .url(currentUrlSource)
                .build();

        client.newCall(request).enqueue(new Callback() {
            // When something goes wrong with the request, throw exception
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Error", e.getMessage());
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // Check for response if response is successful
                if (response.isSuccessful()) {
                // Parsing of the data with XMLPullParser and populate the array with the data
                    try {
                        channelItem = new ArrayList<>();
                        Item item = new Item();
                        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser pullParser = parserFactory.newPullParser();
                        pullParser.setInput(response.body().byteStream(), "UTF-8");
                        int eventType = pullParser.getEventType();
                        boolean isInsideItem = false;
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_TAG) {
                                Log.d(TAG, "StarT Tag" + pullParser.getName());
                                if (pullParser.getName().equalsIgnoreCase("item")) {
                                    isInsideItem = true;
                                    item = new Item();
                                } else if (pullParser.getName().equalsIgnoreCase("title")) {
                                    if (isInsideItem) {
                                        item.setTitle(pullParser.nextText());
                                    }
                                } else if (pullParser.getName().equalsIgnoreCase("description")) {
                                    if (isInsideItem) {
                                        item.setDescription(pullParser.nextText());
                                    }
                                } else if (pullParser.getName().equalsIgnoreCase("pubDate")) {
                                    item.setPubDate(convertStringDateToAnotherStringDate(pullParser.nextText(), "E, dd MMM yyyy HH:mm:ss z", "dd-MM-yyyy"));
                                } else if (pullParser.getName().equalsIgnoreCase("georss:point")) {
                                    item.setPoint(pullParser.nextText());
                                }

                            } else if (eventType == XmlPullParser.END_TAG && pullParser.getName().equalsIgnoreCase("item")) {
                                isInsideItem = false;
                                channelItem.add(item);
                                Log.d(TAG, "End Tag" + item.description);
                            }
                            eventType = pullParser.next();
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // Update the UI
                                currentIncidentsRecycleViewAdapter = new RecycleViewAdapterTrafficScotland(channelItem, TrafficScotlandActivityList.this);
                                recyclerView.setAdapter(currentIncidentsRecycleViewAdapter);
                                currentIncidentsRecycleViewAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                            }
                        });

                    } catch (XmlPullParserException exception) {
                        //progressBar.setVisibility(View.GONE);
                        exception.printStackTrace();
                    }
                }
            }
        });
    }
}