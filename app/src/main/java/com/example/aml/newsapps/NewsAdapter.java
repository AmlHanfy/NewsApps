package com.example.aml.newsapps;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import android.view.ViewGroup;

public class NewsAdapter extends ArrayAdapter<New>
{

    public NewsAdapter(ArrayList<New> news, Context context)
    {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.new_list_item, parent, false);
        }
        New currentNew = getItem(position);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(currentNew.getWebTitle());
        TextView author = (TextView) convertView.findViewById(R.id.section);
        author.setText(currentNew.getSectionName());
        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(currentNew.getWebPublicationDate().substring(0,10));
        TextView time = (TextView)convertView.findViewById(R.id.time);
        time.setText(currentNew.getWebPublicationDate().substring(11,16));
        TextView authorName = (TextView) convertView.findViewById(R.id.authorName);
        authorName.setText(currentNew.getAuthorName());
        return convertView;
    }
}
