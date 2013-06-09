package constants;

import db.Ads;

public class PromotionTags {
	
	public static final String Entertainment[] = {
		"relax",
		"hotel",
		"vacation",
		"skydiving",
		"game",
		"club",
		"bar",
		"pub",
		"nightlife",
		"trip",
		"outing",
		"camp",
		"party",
		"recreational",
		"amusement",
		"concert",
		"theater",
		"band",
		"music",
		"festival",
		"live",
		"performance"};
	
	public static final String Food[] = {
		"food",
		"cuisine",
		"breakfast",
		"lunch",
		"dinner",
		"coffee",
		"bread",
		"cake",
		"pizza",
		"burger",
		"boba",
		"sushi",
		"taco",
		"dining",
		"restaurant"
	};
	
	public static final String Shopping[] = {
		"shopping",
		"sale",
		"discount",
		"clearance",
		"cloth",
		"book",
		"black friday",
		"giving back",
		"computer",
		"phone",
		"grocery",
		"supermarket",
		"mall"
	};

	
	public static final String Stanford = "stanford";
	
	
	public static final String preparingSqlQuery(String promotionTags[]) {
		String tagQuery = "(";
		for (int i = 0; i < promotionTags.length ; i++) {
			if (i != (promotionTags.length - 1)) {
				tagQuery += Ads.TAG + " like \"%" + promotionTags[i] + "%\" or ";
			} else {
				tagQuery += Ads.TAG + " like \"%" + promotionTags[i] + "%\")";
			}
		}
		return tagQuery;
	}
}
