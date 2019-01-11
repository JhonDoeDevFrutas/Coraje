package standardsoft.com.coraje.ui.view.detailTaskModule.view.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Remark;

public class RequestAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Remark> mItems;

    public RequestAdapter(Context context, ArrayList<Remark> remarks) {
        this.mContext = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItems = remarks;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.item_remark, parent, false);
        }

        TextView txtUser    = (TextView)view.findViewById(R.id.message_user);
        TextView txtTime    = (TextView)view.findViewById(R.id.message_time);
        TextView txtMessage = (TextView)view.findViewById(R.id.message_text);

        Remark remark = (Remark)getItem(position);
        txtUser.setText(remark.getUser().toString());
        txtTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",remark.getDate()));
        txtMessage.setText(remark.getDescription().toString().trim());

        return view;
    }
}
