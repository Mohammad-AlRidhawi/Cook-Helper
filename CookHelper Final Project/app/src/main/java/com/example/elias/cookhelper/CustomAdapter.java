package com.example.elias.cookhelper;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<Recipe> {
    private final Context context;
    private final ArrayList<Recipe> values;


    public CustomAdapter(Context context, ArrayList<Recipe> values){
        super(context, R.layout.recipe, values);
        this.context = context;
        this.values = values;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.recipe, parent, false); //list_team_layout must change
        ImageView imageView = (ImageView) rowView.findViewById(R.id.recipe_listview_imageview);
        TextView name = (TextView) rowView.findViewById(R.id.recipe_listview_name);
        TextView description = (TextView) rowView.findViewById(R.id.recipe_listview_description);
        name.setText(values.get(position).getName());
        description.setText(values.get(position).getType());
       /* TextView textView = (TextView) rowView.findViewById(R.id.textView2);


        TextView categoryView = (TextView) rowView.findViewById(R.id.textView);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        textView.setText(names.get(position));
        categoryView.setText(categories.get(position));
*/
        if (values.get(position).getBitmap()==null){
            imageView.setImageDrawable(context.getDrawable(R.drawable.placeholder));
        }
        else {
            imageView.setImageBitmap(values.get(position).getBitmap());
        }
        return rowView;
    }


}
