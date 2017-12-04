package com.vrasn.new_app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by Vikas Ratre on 3/23/2016.
 */
public class customAdapter extends ArrayAdapter<File_info> {
    private Context c;
    private int id;
    private List<File_info> files;
    int i=0;

    public customAdapter(Context context, int resource, List<File_info> objects) {
        super(context, resource, objects);
        c= context;
        id=resource;
        files = objects;



    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= convertView;
        File ff ;
        if(v==null) {
            LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(id,null);
        }
        final String s = files.get(position).getpath();
        ff = new File(s);

       /* v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getItem(position);

                return false;
            }
        });*/

        if(s!=null){
                ImageView iv = (ImageView) v.findViewById(R.id.imageView);
                TextView tv = (TextView) v.findViewById(R.id.textview);

                if(files.get(position).getname().equals("/"))
                {
                    iv.setImageResource(R.drawable.directory_icon);
                    tv.setText("root");
                }
                else if(files.get(position).getname().equals("../")) {
                    iv.setImageResource(R.drawable.directory_up);
                    tv.setText("../");
                }
                else {
                    if (ff.isDirectory()){
                        iv.setImageResource(R.drawable.directory_icon);
                        tv.setText(ff.getName());
                    }
                    else {
                        iv.setImageResource(R.drawable.file_icon);
                        tv.setText(ff.getName());
                    }

                }
        }

        return v;
    }


}
