/* This File i.e 'EarthquakeActivity.java' Would mediate all the Interaction between the Custom class and the custom array Adapter
How, the ArrayList would be created and how the ArrayAdapter would be set up in conjunction with the ArrayList is setup from here
*/

package com.example.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// For Loader related stuff
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.AsyncTaskLoader;
import android.widget.TextView;

// For network Connectivity status
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


// The 'implements....' part was added when we wanted the Loader to Load up the data..before that for AsyncTask it was not Required.
public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>>
{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    // We use the “private” access modifier on the variable because no other classes except for the EarthquakeActivity that need to reference it.
    /*
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    */

    // New URL that would be set according to User Preferences
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    // To access and modify the instance of the EarthquakeAdapter, we need to make it a global variable in the EarthquakeActivity.
    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Just For checking the network connectivity, the lines 64-73 were added and the 'else' block was also added here for displaying message in case of poor internet i.e lines 82-91

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if(networkInfo != null && networkInfo.isConnected())
        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. The second argument allows us to pass a bundle of additional information.
            // Pass in 'this' activity for the LoaderCallbacks parameter (which is valid because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
        else
        {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    // Start the AsyncTask to fetch the earthquake data

    /* Commented out the below code because we are now using loaders and not AsyncTask anymore

    EarthquakeAsyncTask task = new EarthquakeAsyncTask();
    task.execute(USGS_REQUEST_URL);

    */

        // Create a fake list of earthquake locations.
        // Initially, the below list which implemented only single ArrayAdapter was being used
        /*
        ArrayList<String> earthquakes = new ArrayList<>();
        earthquakes.add("San Francisco");
        earthquakes.add("London");
        earthquakes.add("Tokyo");
        earthquakes.add("Mexico City");
        earthquakes.add("Moscow");
        earthquakes.add("Rio de Janeiro");
        earthquakes.add("Paris");
        */

        // Commenting out the below code because now we would extract live data from an API and hence we no longer need fake placeholder data
        /*
        ArrayList<Earthquake> eq = new ArrayList<>();

        eq.add(new Earthquake("7.2", "San Franscisco", "Feb 2, 2016"));
        eq.add(new Earthquake("6.1", "London", "July 20, 2015"));
        eq.add(new Earthquake("3.9", "Tokyo", "Nov 10, 2014"));
        eq.add(new Earthquake("5.4", "Mexico City", "May 3, 2014"));
        eq.add(new Earthquake("2.8", "Moscow", "Jan 31, 2013"));
        eq.add(new Earthquake("4.9", "Rio De Janerio", "Aug 19, 2012"));
        eq.add(new Earthquake("1.6", "Paris", "Oct 30, 2011"));
        */

        // Create a fake list of earthquakes.
        // Now, that the List of earthquakes is being generated from the URL by the support of different helper methods, we comment out this code.
//        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // We have identified our Textview that would display the message of "No Earthquakes Found" by its id.
        // Now, the Text of 'No Earthquakes Found' would be shown only if the setText() message on our Textview in our onLoadFinished() method executes
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new {@link ArrayAdapter} of earthquakes
        // ArrayAdapter is used here for creating the different list item views
//      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, earthquakes);

        // Since its our own Custom ArrayAdapter class i.e EarthquakeAdapter, no need to mention Explicit generic class such as <String><Word> etc.
        // The Resource ID i.e layout is NOT mentioned as the SECOND Parameter in this EarthquakeAdapter constructor because we are proving or rather inflating the view in the 'EarthquakeAdapter.java' File in the getView() method via the 'inflate()' method
        // While using placeholder data, the below method declaration was 'EarthquakeAdapter adapter = new EarthquakeAdapter(this, eq);'
        // Now, we are storing the method output in an ArrayList variable and that variable is passed to the EarthquakeAdapter now.
        // The 'EarthquakeAdapter' would be set to 'final' so that we can pass the adapter object in the anonymous inner class of the listener
        // Since this adapter object wont work upon the dynamic list of earthquakes that is coming from the URL, we comment this out too..
//        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // So the list can be populated in the user interface
        // Old EarthquakeAdapter object is no longer being used so the below code is commented out.
//        earthquakeListView.setAdapter(adapter);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Since, we want to specify intents for clicks on list item, we would use the 'setOnItemClickListener' method.
        // We need to declare an OnItemClickListener on the ListView. OnItemClickListener is an interface, which contains a single method onItemClick().
        // We declare an anonymous class that implements this interface, and provides customized logic for what should happen in the onItemClick() method.

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
//                Earthquake currentEarthquake = adapter.getItem(position);    // Earlier when it was hardcoded JSON Data, the EarthquakeAdapter was stored in 'adapter'

                // Now this same line of code would be:
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // The Intent constructor (that we want to use) requires a Uri object, so we need to convert our URL (in the form of a String) into a URI. We know that our earthquake URL is a more specific form of a URI, so we can use the Uri.parse method
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());


                // Create a new intent to view the earthquake URI
                // The Intent constructor also accepts a URI for the data resource we want to view, and Android will sort out the best app to handle this sort of content.
                // For instance, if the URI represented a location, Android would open up a mapping app.
                // In this case, the resource is an HTTP URL, so Android will usually open up a browser.
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }



    // Loader Methods that would interact with the EarthquakeLoader.java file and ensure the loading up of data properly
    /*
    We would now override the onCreateLoader to load the data according to our preferences and hence we have commented out the below code which was being used before preferences came into action
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle)
    {
        // Create a new loader for the given URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }
    */

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }
    @Override

    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes)
    {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // The message to be displayed if no Earthquakes are found is declared here and the reason it is declared on the onLoadFinished() method is because we dont want to directly show up "No earthquakes found" when we are just starting the app.
        // We want to show the message once the loader has its work done and then if no data exists, then we would show the message
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty())
        {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the list of earthquakes in the response.
     *
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
     * progress updates, so the second generic is just Void.
     *
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread, so it can run long-running code
     * (like network activity), without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread, so it can use the produced data to update the UI.
     */

    /* Commenting out the AsyncTask because we would now use the AsyncTask Loader Functionality so that memory resources are ideally managed
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>
    {


         // This method runs on a background thread and performs the network request.
         // We should not update the UI from a background thread, so we return a list of
         // {@link Earthquake}s as the result.

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }


         // This method runs on the main UI thread after the background work has been
         // completed. This method receives as input, the return value from the doInBackground()
         // method. First we clear out the adapter, to get rid of earthquake data from a previous
         // query to USGS. Then we update the adapter with the new list of earthquakes,
         // which will trigger the ListView to re-populate its list items.

        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
        }
     */

    // Methods that deal with preference selection
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
