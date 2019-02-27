package org.videoApp.backend;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class GetFeedController {

    Gson GSON = new Gson();
    //TODO Timestamp
    //Todo feed update (Require begging in request)
    @RequestMapping("/getPopularFeedItems")
    public String getPopularFeedItems(@RequestBody GetFeedItemRequest request) {
        SQLClient sqlClient = new SQLClient();
        JSONObject sqlOutput = sqlClient.getRows(getSQLQuery(request.getLatitude(), request.getLongitude(), "Votes DESC", request.getGetPostsFrom(), Integer.toString(Integer.getInteger(request.getGetPostsFrom())+20)));
        if (sqlOutput.has("error")) {
            return sqlOutput.toString();
        }
        try {
            JSONArray sqlArray = sqlOutput.getJSONArray("entries");
            FeedItem[] outputArray = new FeedItem[sqlArray.length()];
            for (int i = 0; i < sqlArray.length(); i++) {
                JSONObject item = sqlArray.getJSONObject(i);
                //TODO add user vote
                outputArray[i] = new FeedItem(i+1, item.getString("Media"), item.getString("Submitter"), 0, item.getInt("Votes"));
            }
            return GSON.toJson(outputArray);
        } catch (JSONException e) {
            return "{error: \"internal server error\"}";
        }
    }

    @RequestMapping("/getLatestFeedItems")
    public String getLatestFeedItems(@RequestBody GetFeedItemRequest request) {
        SQLClient sqlClient = new SQLClient();
        JSONObject sqlOutput = sqlClient.getRows(getSQLQuery(request.getLatitude(), request.getLongitude(), "Timestamp DESC", request.getGetPostsFrom(), Integer.toString(Integer.getInteger(request.getGetPostsFrom())+20)));
        if (sqlOutput.has("error")) {
            return sqlOutput.toString();
        }
        try {
            JSONArray sqlArray = sqlOutput.getJSONArray("entries");
            FeedItem[] outputArray = new FeedItem[sqlArray.length()];
            for (int i = 0; i < sqlArray.length(); i++) {
                JSONObject item = sqlArray.getJSONObject(i);
                //TODO add user vote
                outputArray[i] = new FeedItem(i+1, item.getString("Media"), item.getString("Submitter"), 0, item.getInt("Votes"));
            }
            return GSON.toJson(outputArray);
        } catch (JSONException e) {
            return "{error: \"internal server error\"}";
        }
    }

    public String getSQLQuery(final String latitude, final String longitude, final String orderBy, final String searchStart, final String searchEnd) {
        return "SELECT\n" +
                "    *, (\n" +
                "      3959 * acos (\n" +
                "      cos ( radians(" + latitude + ") )\n" +
                "      * cos( radians( Latitude ) )\n" +
                "      * cos( radians( Longitude ) - radians(" + longitude + ") )\n" +
                "      + sin ( radians(" + latitude + ") )\n" +
                "      * sin( radians( Latitude ) )\n" +
                "    )\n" +
                ") AS distance\n" +
                "FROM posts\n" +
                "HAVING distance < 5\n" +
                "ORDER BY " + orderBy + "\n" +
                "LIMIT " + searchStart + " , " + searchEnd + ";";
    }
}
