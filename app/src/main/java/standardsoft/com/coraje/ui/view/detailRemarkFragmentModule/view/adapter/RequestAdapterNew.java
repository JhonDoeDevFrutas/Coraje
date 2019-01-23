package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Remark;

public class RequestAdapterNew extends BaseAdapter {

    private static final int TYPE_HEADER    = 0;
    private static final int TYPE_ITEM      = 1;

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Object> mItems;

    public RequestAdapterNew(Context context, ArrayList<Object> items) {
        this.mContext = context;
        this.mItems = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        int type = getItemViewType(position);
        if (view == null){
            switch (type){
                case TYPE_HEADER:
                    view = inflater.inflate(R.layout.item_header_remark, parent, false);
                    break;

                case TYPE_ITEM:
                    view = inflater.inflate(R.layout.item_detail_remark_new, parent, false);
                    break;
            }
        }

        switch (type){
            case TYPE_HEADER:
                String key = getItem(position).toString();

                TextView txtHeader = (TextView)view.findViewById(R.id.header_id);
                txtHeader.setText(key);
                break;

            case TYPE_ITEM:
                Remark remark = (Remark)getItem(position);
                String status = remark.getSubPlanning().getStatus().getDescription();
                long date = remark.getDate();

                TextView txtUser    = (TextView)view.findViewById(R.id.item_user);
                TextView txtTask    = (TextView)view.findViewById(R.id.item_task);
                TextView txtStatus  = (TextView)view.findViewById(R.id.item_status);
                TextView txtContent = (TextView)view.findViewById(R.id.item_content);
                TextView txtTime    = (TextView)view.findViewById(R.id.item_time);

                switch (status){
                    case "ESPERANDO REVISION":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorBlack));

                        break;
                    case "EN PROCESO":
                        //txtStatus.setTextColor(Color.parseColor("#F1AD54"));
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorOrange));


                        break;
                    case "LISTO":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                        break;
                }


                txtUser.setText(remark.getUser());
                txtTask.setText(remark.getSubPlanning().getTask());
                txtStatus.setText(remark.getSubPlanning().getStatus().getDescription());
                txtContent.setText(remark.getDescription());
                txtTime.setText(new SimpleDateFormat("hh:mm a", Locale.ROOT).format(date));
                break;
        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof String)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_ITEM);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
