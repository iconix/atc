package staticVariables;

public class SharedPreference {
	public static final String PREFERENCE = "PreferenceAroundTheCorner";

    //check the last saved view so we can go immediately to that view when we start the program
    public static final String LAST_SAVED_VIEW = "lastSavedView";
    
    //check for the preferences in the view type of map
    public static final String MAP_TYPE_VIEW = "mapViewType";
    
    //check for the preferences which give you the from time
    public static final String TIME_FROM_PREFERENCE = "fromTime";
    //check for the preferences which give you the to time
    public static final String TIME_TO_PREFERENCE = "toTime";
    //check for the preferences which give you the time gap
    public static final String TIME_GAP_PREFERENCE = "gapTime";
    
}
