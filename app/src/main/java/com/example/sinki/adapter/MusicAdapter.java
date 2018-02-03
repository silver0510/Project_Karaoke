package com.example.sinki.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.sinki.model.BaiHat;
import com.example.sinki.project_karaoke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinki on 9/1/2017.
 */

public class MusicAdapter extends ArrayAdapter<String> {
    Activity context;
    int resource;
    List<String> objects;
    List<String> dsFilter;
    LayoutInflater inflat;
//    MusicFilter musicFilter;

    public MusicAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.dsFilter = objects;
        inflat = this.context.getLayoutInflater();
    }
    // View lookup cache
    private static class ViewHolder {
        TextView txtBaiHat;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = dsFilter.get(position);
        ViewHolder viewHolder;
        final View result;

        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            inflat = LayoutInflater.from(getContext());
            convertView = inflat.inflate(R.layout.itemtext,null);
            viewHolder.txtBaiHat = (TextView)convertView.findViewById(R.id.txtBaiHat);
            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        viewHolder.txtBaiHat.setText(item);

        return result;
    }

//    @NonNull
//    @Override
//    public Filter getFilter() {
//        if(musicFilter == null)
//        {
//            musicFilter = new MusicFilter();
//        }
//        return musicFilter;
//    }

//    private class MusicFilter extends Filter{
//
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            charSequence = charSequence.toString().toLowerCase();
//            FilterResults results = new FilterResults();
//            ArrayList<String>searchList = new ArrayList<String>();
//            if(charSequence!=null&&charSequence.toString().length()>0)
//            {
//                for(String s:objects)
//                {
//                    if(s.toLowerCase().startsWith(charSequence.toString()))
//                    {
//                        searchList.add(s);
//                    }
//                }
//                results.values = searchList;
//                results.count = searchList.size();
//            }
//            else
//            {
//                results.values = objects;
//                results.count = objects.size();
//            }
//
//            return results;
//        }
//
//        @SuppressWarnings("unchecked")
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            dsFilter = (ArrayList<String>)filterResults.values;
//            notifyDataSetChanged();
//        }
//    }
}
