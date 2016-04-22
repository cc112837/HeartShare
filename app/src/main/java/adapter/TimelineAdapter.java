package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.heartshare.R;

import java.util.List;
import java.util.Map;

/**
 * 项目名称：HeartShare
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/4/21 13:23
 */
public class TimelineAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> list;
    private LayoutInflater inflater;

    public TimelineAdapter(Context context, List<Map<String, String>> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.list_show_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.show_month);
            viewHolder.day = (TextView) convertView.findViewById(R.id.show_year);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String titleStr = list.get(position).get("title");
        viewHolder.day.setText(list.get(position).get("day"));
        viewHolder.time.setText(list.get(position).get("time"));
        viewHolder.title.setText(titleStr);

        return convertView;
    }

    static class ViewHolder {
        public TextView day;
        public TextView time;
        public TextView title;
    }
}
