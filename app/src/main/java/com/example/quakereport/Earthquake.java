// This is a custom class for Earthquake data which would have 'private' variables and 'public' methods.
// The methods are public so that they can be accessed in the EarthquakeAdapter.java file while we want to populate the list view.

package com.example.quakereport;

public class Earthquake
{

    /** Gives the Location of the Earthquake */
    private String Location;

    /** Gives the Time of the earthquake */
    // Initially, it had datatype of String but that meant that only milliseconds were being returned from the API
    // So, now the Datatype has been changed to 'long' because the Date() requires 'Long' as input and then the 'SimpleDateFormat.format()' method requires a Date object, so we do all this procedure
    private long mTimeInMilliseconds;

    /** Stores the magnitude */
    // Initially, it had datatype of String but that meant that magnitude were being returned from the API and we were interpreting it as a String
    // So, now the Datatype has been changed to 'double' because the format() method of DecimalFormat takes in 'double' as the input  so we do all this procedure
    private double magnitude;

    /** Website URL of the earthquake */
    // This was introduced, because we are now introducing intent action on the clicking of the list item
    private String mUrl;


    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param mag is the magnitude (size) of the earthquake
     * @param loc is the location where the earthquake happened
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     * @param url is the website URL to find more details about the earthquake
     */

    public Earthquake(double mag, String loc, Long timeInMilliseconds, String url) {
        magnitude = mag;
        Location = loc;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    // Since the variables are private in nature, in order to access them, public methods are required
    // These methods would be used by other classes to get information

    /**
     * Get the Location of the earthquake.
     */
    public String getEarthquakeLocation() {
        return Location;
    }

    /**
     * Returns the time of the earthquake.
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /**
     * Return the magnitude of the earthquake
     */
    public double getEarthquakeMagnitude() {
        return magnitude;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }
}
