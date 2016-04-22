package fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cc.heartshare.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.MainActivity;
import activity.NewShareActivity;
import adapter.TimelineAdapter;
import db.MySqliteOpenHelper;
import inter.FinalValue;

/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/4/21
 */
public class LoveFragment extends Fragment implements MainActivity.OnShareClickListener {
    private ListView lv_listid;
    List<Map<String, String>> list;//数据源
    private TimelineAdapter timelineAdapter;
    private MySqliteOpenHelper helper;// 数据库连接对象
    private SQLiteDatabase database;// 数据库
    private Cursor cursor;// 游标

    public LoveFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_love, container, false);
        initView(inflate);
        initdata();//初始化数据库
        aboutList();//关于listview的一些操作
        if (!cursor.moveToFirst()) {
        } else {
            dataDB();
        }
        return inflate;

    }

    private void dataDB() {
        cursor = database.query(FinalValue.TB_SHARE, null, null, null, null,
                null, null);
        while (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex("title"));
            String day = cursor.getString(cursor.getColumnIndex("day"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            HashMap<String, String> map = new HashMap<>();
            map.put("title", content);
            map.put("day", day);
            map.put("time", time);
            list.add(0, map);
        }
        timelineAdapter.notifyDataSetChanged();
    }

    private void aboutList() {
        lv_listid.setDividerHeight(0);
        timelineAdapter = new TimelineAdapter(getActivity(), getData());
        lv_listid.setAdapter(timelineAdapter);
        lv_listid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> itemcontent = list.get(position);
                String content = itemcontent.get("title");
                Intent intent = new Intent(getActivity(), NewShareActivity.class);
                intent.putExtra("tag", "2");
                intent.putExtra("position", position);
                intent.putExtra("title", content);
                startActivityForResult(intent, 200);

            }
        });
        lv_listid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("删除内容");// 对话框标题
                builder.setMessage("你确定要删除吗");// 对话框内容
                DialogButtonOnClickListener listener = new DialogButtonOnClickListener(position);
                builder.setNegativeButton("取消", listener);
                builder.setPositiveButton("确定", listener);
                builder.create().show();
                return true;
            }
        });

    }

    @Override
    public void onClicked() {
        Intent intent = new Intent(getActivity(), NewShareActivity.class);
        intent.putExtra("tag", "1");
        startActivityForResult(intent, 200);

    }


    private class DialogButtonOnClickListener implements DialogInterface.OnClickListener {
        private int position;

        public DialogButtonOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    Map<String, String> map = list.get(position);
                    String content = map.get("title");
                    database.delete(FinalValue.TB_SHARE, "title=?", new String[]{
                            content + ""});
                    list.remove(position);
                    timelineAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private void initdata() {
        helper = new MySqliteOpenHelper(getActivity(), FinalValue.DB_NAME, null,
                FinalValue.DB_VERSION);
        database = helper.getReadableDatabase();
        cursor = database.query(FinalValue.TB_SHARE, null, null, null, null,
                null, null);
    }

    private void initView(View inflate) {
        lv_listid = (ListView) inflate.findViewById(R.id.lv_listid);
    }

    private List<Map<String, String>> getData() {
        list = new ArrayList<>();
        return list;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent d) {
        String string = d.getStringExtra("name");
        String tag = d.getStringExtra("tag");
        String day = d.getStringExtra("day");
        String time = d.getStringExtra("time");
        String flag = d.getStringExtra("flag");
        HashMap<String, String> map = new HashMap<>();
        map.put("title", string);

        if (tag.equals("1")) {
            if (flag.equals("3")) {
                map.put("time", time);
                map.put("day", day);
                list.add(0, map);
            }
        }
        if (tag.equals("2")) {
            int pos = d.getIntExtra("position", 0);
            if (flag.equals("2")) {
                map.put("time", list.get(pos).get("time"));
                map.put("day",  list.get(pos).get("day"));
                list.set(pos, map);
            }
            if (flag.equals("1")) {
                list.remove(pos);
                map.put("time", time);
                map.put("day", day);
                list.add(0, map);
            }
        }
        timelineAdapter.notifyDataSetChanged();
    }
}
