package in.doris.sharq.db.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import in.doris.sharq.activities.R;

/**
 * Created by ridharm on 16-10-2016.
 */
public class nameListAdapter extends CursorAdapter {
    public nameListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.namelist, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tsno = (TextView) view.findViewById(R.id.sno);
        TextView tname = (TextView) view.findViewById(R.id.name);
        TextView tstar = (TextView) view.findViewById(R.id.star);
        TextView tloc = (TextView) view.findViewById(R.id.loc);
        TextView tcat = (TextView) view.findViewById(R.id.cat);
        TextView tphone = (TextView) view.findViewById(R.id.phone);

        // Extract properties from cursor
        int _id = cursor.getInt(cursor.getColumnIndex("COLUMN_ID"));
        String name = cursor.getString(cursor.getColumnIndex("COLUMN_NAME"));
        String star = cursor.getString(cursor.getColumnIndex("COLUMN_STAR"));
        String loc = cursor.getString(cursor.getColumnIndex("COLUMN_PLACE"));
        String cat = cursor.getString(cursor.getColumnIndex("COLUMN_CAT"));
        String phone = cursor.getString(cursor.getColumnIndex("COLUMN_PHONE"));

        // Populate fields with extracted properties
        tsno.setText(_id);
        tname.setText(name);
        tstar.setText(star);
        tloc.setText(loc);
        tcat.setText(cat);
        tphone.setText(phone);

    }
}