package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cc.heartshare.R;

/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/4/21 11:13
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    LinearLayout ll_name, ll_sign, ll_setting, ll_reback, ll_share;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ll_name = (LinearLayout) view.findViewById(R.id.ll_name);
        ll_sign = (LinearLayout) view.findViewById(R.id.ll_sign);
        ll_setting = (LinearLayout) view.findViewById(R.id.ll_setting);
        ll_reback = (LinearLayout) view.findViewById(R.id.ll_reback);
        ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
        ll_name.setOnClickListener(this);
        ll_sign.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_reback.setOnClickListener(this);
        ll_share.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_name:
                break;
            case R.id.ll_sign:
                break;
            case R.id.ll_setting:
                break;
            case R.id.ll_reback:
                break;
            case R.id.ll_share:
                break;
        }

    }
}
