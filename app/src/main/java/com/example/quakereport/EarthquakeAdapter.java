// This is a custom Adapter java file where the Objects of the class 'Earthquake' would be created and the list items would be created over here.
// The final arrayList however that consists of all the list items would be actually created in 'QueryUtils.java' File and that would be passed to 'EarthquakeActivity.java' File where that (i.e the one that was created in QueryUtils.java) arraylist would be stored in a variable and that variable would then be used for linking to custom array adapter.
// The data would come from 'QueryUtils.java' class which would passed to the constructor of 'Earthquake.java' File  and then that data would be accessed by the 'public' methods whose definition is present in the 'Earthquake.java' File.

package com.example.quakereport;

import android.content.Context;
// Earlier:
// import android.support.v4.content.ContextCompat;

// Now:
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;

import android.graphics.drawable.GradientDrawable;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>    // <Earthquake> is passed beside the ArrayAdapter to indicate that the ArrayAdapter source input is the 'Earthquake' Class
{

    /**
     * Create a new {@link EarthquakeAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param pEarthquake is the list of {@link Earthquake} to be displayed.
     */

    // This separator  would just be passed as input to the 'split()' method and if valid then the appropriate 2 separate strings would be created or else the hardcoded 'near the' would be the first string before the primary location
    private static final String LOCATION_SEPARATOR = " of ";


    public EarthquakeAdapter(Context context, ArrayList<Earthquake> pEarthquake) {
        super(context, 0, pEarthquake);  // ArrayAdapter's constructor is being called here.. the Resource Id is kept 0 here so that the ArrayAdapter does not create a list item view for us manually, but we ourselves would create a list item view via our own getView() implementation.
    }

    // View Recycling
    // The below code would decide how the views would be shown up an hence it is written over here
    // This below part is crucial when we are implementing the use of custom adapters in our code.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Creation/Reusing of List item view

        // Storing a view in the 'listItemView' variable
        View listItemView = convertView;

        // 'null' here means that if no views are there to reuse..if no views are there to be reused, we would inflate a new view according to the XML Layout file passed in the inflate() method
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // The received object at the desired position fetched via the 'getItem()' method would be stored in a variable named 'earthquake' and that would have a return Data type of Custom Class 'Earthquake' because that is the source input of our Superclass.
        // 'Earthquake' is our Custom Class and here we are just creating an Object of that class.
        Earthquake earthquake = getItem(position);

        // First, we get the original location String from the Earthquake object and store that in a variable.
        String originalLocation = earthquake.getEarthquakeLocation();

        // The new variables (primary location and location offset) are created to store the resulting Strings.
        String primaryLocation;
        String locationOffset;

        // The split(String string) method in the String class to split the original string at the position where the text “ of “ occurs.
        // The result will be a String containing the characters PRIOR to the “ of ” text and a String containing the characters AFTER the “ of “ text.
        // A String array is the return value of the 'split' method.
        // Here, the separator defined above i.e 'of' is used as the input condition
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);   // The location would be split into a String array named 'parts'
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else   // If the location offset has no 'of' within it, then the hardcoded string would be the first element of the String Array.
        {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }


        // Now we would refer to the view that we had created which consisted of 3 textviews.


        // Find the TextView with view ID magnitude
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable. i.e which is a empty circle
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        // The getEarthquakeMagnitude() would be associated with our 'earthquake' object and that would be then passed as input to the 'getMagnitudeColor()' helper method
        // The helper method then returns the color according to the 'switch' statement output and stores it in a variable called 'magnitudeColor'
        int magnitudeColor = getMagnitudeColor(earthquake.getEarthquakeMagnitude());

        // Set the color on the magnitude circle i.e the color value stored in variable 'magnitudeColor' would be set to the empty circle using the 'setColor()' method
        magnitudeCircle.setColor(magnitudeColor);

        // Format the magnitude to show 1 decimal place via our helper method
        // The getEarthquakeMagnitude() would be associated with our 'earthquake' object and that would be then passed as input to the 'formatMagnitude()' helper method
        // The helper method then returns the proper format of output that we desired i.e just a single digit after the decimal point
        String formattedMagnitude = formatMagnitude(earthquake.getEarthquakeMagnitude());

        // Display the magnitude of the current earthquake in that TextView
        magnitudeView.setText(formattedMagnitude);

        /* Earlier when we were simply getting the magnitude in a String Format with no decimal point conditions i.e (wanting just a single digit after the decimal point) the below commented out method was valid, but now that we have the magnitude in 'double' format, the below method is no more valid
        // Getting the Magnitude TextView by its id
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(earthquake.getEarthquakeMagnitude());   // The position that was stored in our variable..that gets passed to our getMiwokTranslation() method and the data in the textview gets set in our layout
        */

        /* Earlier when we were simply getting the location in a SINGLE Textview with no Font styling, the below commented out method was valid, but now that we have 2 textviews, the below method is no more valid
        // Getting the Location TextView by its id
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location);
        locationTextView.setText(earthquake.getEarthquakeLocation());   // The position that was stored in our variable..that gets passed to our getMiwokTranslation() method and the data in the textview gets set in our layout
        */

        // The 2 separate Location related textviews would now be populated accordingly
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        /* Earlier when the Return Type of 'mTimeInMilliseconds' variable was 'String', the below commented out method was valid..but now since one more textview has been added to the 'earthquake_activity.xml' file and we are now focusing to show both time and date, we no longer require the below method
        // Getting the Date and Time TextView by its id
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(earthquake.getTimeInMilliseconds());   // The position that was stored in our variable..that gets passed to our getMiwokTranslation() method and the data in the textview gets set in our layout
        */

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(earthquake.getTimeInMilliseconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);

        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        // Format the time string (i.e. "4:30PM") via the helper method and the input would be provided to the helper method via the Date object which converted the time received in milliseconds to the desired format.
        // The desired format is mentioned in the defintion of the helper method itself
        String formattedTime = formatTime(dateObject);

        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        return listItemView;  // The list is then finally returned once its populated..
    }

    // Helper Methods

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    //
    private String formatMagnitude(double magnitude)
    {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    // Helper method that would be used to return the date according to our desired format
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    // Helper method that would be used to return the date according to our desired format
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;

        // Since, switch cannot accept 'double' values, we cast them into 'int'
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor)
        {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        // Color resource IDs just point to the resource we defined, but not the value of the color. For example, R.layout.earthquake_list_item is a reference to tell us where the layout is located. It’s just a number, not the full XML layout.
        // You can call ContextCompat getColor() to convert the color resource ID into an actual integer color value, and return the result as the return value of the getMagnitudeColor() helper method.
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
