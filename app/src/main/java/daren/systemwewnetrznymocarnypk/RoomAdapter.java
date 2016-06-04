package daren.systemwewnetrznymocarnypk;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
            holder.txtOwner = (TextView)row.findViewById(R.id.txtOwner);

            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }

        RowRoom object = data[position];
        holder.txtTitle.setText(object.name);
        holder.txtDescription.setText(object.description);
        holder.txtOwner.setText(object.owner);

        return row;
    }

    static class RowBeanHolder
    {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtOwner;
    }
}
