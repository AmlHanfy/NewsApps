package com.example.aml.newsapps;
import android.text.TextUtils;
import android.util.Log;
import com.example.aml.newsapps.New;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<New> fetchEarthquakeData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<New> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the new JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<New> extractFeatureFromJson(String newJSON) {
        if (TextUtils.isEmpty(newJSON)) {
            return null;
        }
        List<New> news = new ArrayList<>();
        try {
            New currentNew;
            JSONObject baseJsonResponse = new JSONObject(newJSON);
            JSONObject response = baseJsonResponse.optJSONObject("response");
            String Name="";
            String authorName ="";
            if (response != null) {
                JSONArray newsArray = response.optJSONArray("results");
                if (newsArray != null) {
                    for (int i = 0; i < newsArray.length(); i++)
                    {
                        JSONObject currentNewJson = newsArray.getJSONObject(i);
                        JSONArray tag = currentNewJson.optJSONArray("tags");
                        for(int j =0;j<tag.length();j++)
                        {
                            JSONObject currentJson = tag.getJSONObject(j);
                             Name = currentJson.getString("webTitle");
                            authorName =authorName+"    "+ Name ;
                        }

                        String sectionName = currentNewJson.getString("sectionName");
                        String title = currentNewJson.getString("webTitle");
                        String webPublicationDate = currentNewJson.getString("webPublicationDate");
                        String webUrl = currentNewJson.getString("webUrl");
                        currentNew = new New(sectionName, title, webUrl, webPublicationDate,authorName);
                        news.add(currentNew);
                        authorName ="";
                    }
                }
                else
                {
                    return null;
                }
            }
            else {
                return null;
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        return news;
    }

}