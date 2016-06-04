package daren.systemwewnetrznymocarnypk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by daren on 04.06.16.
 */
public class RoomAdapter extends ArrayAdapter<RowRoom> {

    Context context;
    int layoutResourceId;
    RowRoom data[] = null;

    public RoomAdapter(Context context, int layoutResourceId, RowRoom[] data) {
        super(context, layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        //new WebServiceHandler().execute("http://192.168.1.150:8000/api/rest/v1/rooms/66b5f696-e01c-46f0-a55f-16b393f6ea84/");

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RowBeanHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtDescription = (TextView)row.findViewById(R.id.txtDescription);

            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }

        final RowRoom object = data[position];

        holder.txtTitle.setText(object.name);
        holder.txtDescription.setText(object.description);
        holder.button = (Button) row.findViewById(R.id.zarezerwuj_batton);
        holder.but2= (Button) row.findViewById(R.id.anuluj_button);
        final RowBeanHolder finalHolder = holder;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.button.setText("Gotowe");
                finalHolder.but2.setVisibility(View.VISIBLE);
            }
        });
        holder.but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.but2.setVisibility(View.INVISIBLE);
                finalHolder.button.setText("Zarezerwuj");
            }
        });


        return row;
    }

    static class RowBeanHolder
    {
        TextView txtTitle;
        TextView txtDescription;
        Button button;
        Button but2;
    }

}
