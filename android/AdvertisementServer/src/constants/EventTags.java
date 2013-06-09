package constants;

import db.Ads;

public class EventTags {
	
	public static final String Entertainment[] = {
		"entertainment",
		"music",
		"party",
		"gathering",
		"social",
		"dance",
		"game",
		"fair",
		"nightlife",
		"dinner",
		"relax",
		"food",
		"live",
		"performance"};
	
	public static final String Educational[] = {
		"education",
		"forum",
		"talk",
		"presentation",
		"career",
		"study",
		"debate",
		"demo",
		"lecture",
		"cultural",
		"ted"
	};
	
	public static final String Service[] = {
		"service",
		"community",
		"vonlunteer",
		"elders",
		"planning",
		"homeless",
		"philantrophy",
		"giving back",
		"helping",
		"assisting",
		"dorm",
		"reunion",
		"drive",
		"save lives"
	};
	
	public static final String Organization[] = {
		"group",
		"organization",
		"student",
		"planning",
		"association",
		"council",
		"dorm"
	};
	
	public static final String Stanford = "stanford";
	
	
	public static final String preparingSqlQuery(String eventTags[]) {
		String tagQuery = "(";
		for (int i = 0; i < eventTags.length ; i++) {
			if (i != (eventTags.length - 1)) {
				tagQuery += Ads.TAG + " like \"%" + eventTags[i] + "%\" or ";
			} else {
				tagQuery += Ads.TAG + " like \"%" + eventTags[i] + "%\")";
			}
		}
		return tagQuery;
	}
	
	public static final String preparingSqlStanfordQuery() {
		return "(" + Ads.TAG + " like \"%stanford%\" or " + 
				Ads.SHORT_DESCRIPTION + " like \"%stanford%\" or " + 
				Ads.LONG_DESCRIPTION + " like \"%stanford%\")";
	}
	
	
}
