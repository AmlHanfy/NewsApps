package com.example.aml.newsapps;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import java.util.ArrayList;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.List;
public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<New>>
{
    public static String queryUrl = "http://content.guardianapis.com/search?q=debates&api-key=test&show-tags=contributor";
    private NewsAdapter mAdapter;
    private static final int BOOK_LOADER_ID = 1;
    LoaderManager loaderManager;
    View loadingIndicator;
    NetworkInfo networkInfo;
    private TextView mEmptyStateTextView;
    ConnectivityManager connMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        loadingIndicator = findViewById(R.id.loading_indicator);
        ListView listView = (ListView) findViewById(R.id.news_list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        mAdapter = new NewsAdapter(new ArrayList<New>(), this);
        listView.setAdapter(mAdapter);
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }
        else if (networkInfo == null)
        {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No Internet");
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                New selectedNew = mAdapter.getItem(position);
                String url = selectedNew != null ? selectedNew.getWebUrl() : null;
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public Loader<List<New>> onCreateLoader(int id, Bundle args)
    {
        return new NewsAsyncLoader(this, queryUrl);
    }
    @Override
    public void onLoadFinished(Loader<List<New>> loader, List<New> data)
    {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText("No News show");
        mAdapter.clear();
        if (data != null && !data.isEmpty())
        {
            mAdapter.addAll(data);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<New>> loader)
    {
        mAdapter.clear();
    }
}