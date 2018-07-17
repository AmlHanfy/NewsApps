package com.example.aml.newsapps;
import android.content.AsyncTaskLoader;
import java.util.List;
import android.content.Context;
public class NewsAsyncLoader extends AsyncTaskLoader<List<New>>
{
    private String url;
    public NewsAsyncLoader(Context context, String url)
    {
        super(context);
        this.url = url;
    }
    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }
    @Override
    public List<New> loadInBackground()
    {
        if (url == null) {
            return null;
        }
        List<New> news = QueryUtils.fetchEarthquakeData(url);
        return news;
    }

}
