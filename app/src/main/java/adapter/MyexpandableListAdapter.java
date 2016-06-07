package adapter;

/**
 * 项目名称：HeartShare
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/6/3 14:11
 * 修改人：Administrator
 * 修改时间：2016/6/3 14:11
 * 修改备注：
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.heartshare.R;

import java.util.List;

import model.Group;
import model.People;
/***
 * 适配器
 *
 * @author Administrator
 *
 */
public class MyexpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Group> groupList;
    private List<List<People>> childList;

    public MyexpandableListAdapter(Context context,List<Group> groupList,List<List<People>> childList) {
        this.context = context;
        this.groupList=groupList;
        this.childList=childList;
        inflater = LayoutInflater.from(context);
    }

    // 返回父列表个数
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    // 返回子列表个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = inflater.inflate(R.layout.group, null);
            groupHolder.textView = (TextView) convertView
                    .findViewById(R.id.group);
            groupHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.image);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView.setText(((Group) getGroup(groupPosition))
                .getTitle());
        if (isExpanded)// ture is Expanded or false is not isExpanded
            groupHolder.imageView.setImageResource(R.mipmap.iconup);
        else
            groupHolder.imageView.setImageResource(R.mipmap.icondown);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.child, null);

            childHolder.textName = (TextView) convertView
                    .findViewById(R.id.name);
            childHolder.textAge = (TextView) convertView
                    .findViewById(R.id.age);
            childHolder.textAddress = (TextView) convertView
                    .findViewById(R.id.address);
            childHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.image);
            Button button = (Button) convertView
                    .findViewById(R.id.button1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "clicked pos="+childPosition+"**pra**"+groupPosition, Toast.LENGTH_SHORT).show();
                }
            });

            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.textName.setText(((People) getChild(groupPosition,
                childPosition)).getName());
        childHolder.textAge.setText(String.valueOf(((People) getChild(
                groupPosition, childPosition)).getAge()));
        childHolder.textAddress.setText(((People) getChild(groupPosition,
                childPosition)).getAddress());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

    class ChildHolder {
        TextView textName;
        TextView textAge;
        TextView textAddress;
        ImageView imageView;
    }
}
