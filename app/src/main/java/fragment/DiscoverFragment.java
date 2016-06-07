package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cc.heartshare.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyexpandableListAdapter;
import model.Group;
import model.People;
import view.PinnedHeaderExpandableListView;
import view.StickyLayout;

/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/4/21 11:12
*/
public class DiscoverFragment extends Fragment implements
        ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener,
        PinnedHeaderExpandableListView.OnHeaderUpdateListener, StickyLayout.OnGiveUpTouchEventListener {
    private PinnedHeaderExpandableListView expandableListView;
    private StickyLayout stickyLayout;
    private ArrayList<Group> groupList;
    private ArrayList<List<People>> childList;
    private MyexpandableListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_discover, container, false);
        init(view);
        return view;
    }
    //初始化控件
    private void init(View view) {
        expandableListView = (PinnedHeaderExpandableListView) view.findViewById(R.id.expandablelist);
        stickyLayout = (StickyLayout) view.findViewById(R.id.sticky_layout);
        initData();

        adapter = new MyexpandableListAdapter(getActivity(), groupList, childList);
        expandableListView.setAdapter(adapter);

        // 展开所有group
        for (int i = 0, count = expandableListView.getCount(); i < count; i++) {
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnHeaderUpdateListener(this);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        stickyLayout.setOnGiveUpTouchEventListener(this);

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }


    @Override
    public View getPinnedHeader() {
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.group, null);
        headerView.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        Group firstVisibleGroup = (Group) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(firstVisibleGroup.getTitle());
    }

    /**
     * 创建人：吴聪聪
     * 邮箱:cc112837@163.com
     * 数据源
     * 创建时间：2016/6/3 14:16
     */
    void initData() {
        groupList = new ArrayList<>();
        Group group;
        for (int i = 0; i < 3; i++) {
            group = new Group();
            group.setTitle("分组的内容是-" + i);
            groupList.add(group);
        }

        childList = new ArrayList<>();
        for (int i = 0; i < groupList.size(); i++) {
            ArrayList<People> childTemp;
            if (i == 0) {
                childTemp = new ArrayList<>();
                for (int j = 0; j < 13; j++) {
                    People people = new People();
                    people.setName("名字是" + j);
                    people.setAge(30);
                    people.setAddress("住址-" + j);

                    childTemp.add(people);
                }
            } else if (i == 1) {
                childTemp = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    People people = new People();
                    people.setName("姓名是" + j);
                    people.setAge(40);
                    people.setAddress("住址" + j);

                    childTemp.add(people);
                }
            } else {
                childTemp = new ArrayList<>();
                for (int j = 0; j < 23; j++) {
                    People people = new People();
                    people.setName("姓名是" + j);
                    people.setAge(20);
                    people.setAddress("地址是" + j);

                    childTemp.add(people);
                }
            }
            childList.add(childTemp);
        }

    }


}
