package activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.heartshare.R;

import java.util.Calendar;

import db.MySqliteOpenHelper;
import inter.FinalValue;

/**
 * 项目名称：HeartShare
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/4/21 15:41
 * 修改人：Administrator
 * 修改时间：2016/4/21 15:41
 * 修改备注：
 */
public class NewShareActivity extends Activity {
    private MySqliteOpenHelper helper;
    private SQLiteDatabase database;
    private EditText et_content;
    private String content;
    private String tag;
    private int position;
    private TextView tv_title;
    private ImageView iv_back;

    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_share);
        LinearLayout toolbar = (LinearLayout) findViewById(R.id.toolbar);
        tv_title=((TextView) toolbar.findViewById(R.id.tv_title));
        iv_back=(ImageView) toolbar.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title.setText("心愿详情");
        et_content = (EditText) findViewById(R.id.et_content);
        initDB();
        intent = getIntent();

        if (intent != null) {
            tag = intent.getStringExtra("tag");
            if (tag.equals("2")) {
                position = intent.getIntExtra("position", 0);
                content = intent.getStringExtra("title");
                et_content.setText(content);
            }
        }
    }

    @Override
    public void onBackPressed() {
        int y=Calendar.getInstance().get(Calendar.YEAR);
        int m = Calendar.getInstance().get(Calendar.MONTH)+1;
        int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mm = Calendar.getInstance().get(Calendar.MINUTE);
        int ss=Calendar.getInstance().get(Calendar.SECOND);
        ContentValues values = new ContentValues();
        Intent intent = new Intent();
        if (tag.equals("1")) {
            if (et_content.getText().toString().equals("")) {
                intent.putExtra("flag", "0");
            } else {
                intent.putExtra("flag", "3");
                values.put("title", et_content.getText().toString());
                values.put("day", y+"/"+m + "/" + d);
                values.put("time", hour + ":" + mm+":"+ss);
                database.insert(FinalValue.TB_SHARE, null, values);
            }
        }
        if (tag.equals("2")) {
            values.put("title", et_content.getText().toString());
            if (!content.equals(et_content.getText().toString())) {
                intent.putExtra("flag", "1");
                values.put("day", y + "/" + m + "/" + d);
                values.put("time", hour + ":" + mm + ":" + ss);
                database.delete(FinalValue.TB_SHARE, "title=?", new String[]{
                        content + ""});
                database.insert(FinalValue.TB_SHARE, null, values);
            } else {
                intent.putExtra("flag", "2");
                database.update(FinalValue.TB_SHARE, values, "title=?", new String[]{content});
            }
        }
        intent.putExtra("name", et_content.getText().toString());
        intent.putExtra("tag", tag);
        intent.putExtra("position", position);
        intent.putExtra("day", y+"/"+m + "/" + d);
        intent.putExtra("time", hour + ":" + mm+":"+ss);
        setResult(200, intent);
        finish();
        super.onBackPressed();
    }

    private void initDB() {
        helper = new MySqliteOpenHelper(this, FinalValue.DB_NAME, null,
                FinalValue.DB_VERSION);
        database = helper.getReadableDatabase();
    }
}
