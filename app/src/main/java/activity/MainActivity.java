package activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cc.heartshare.R;

import fragment.DiscoverFragment;
import fragment.HomeFragment;
import fragment.LoveFragment;
import fragment.MyFragment;
import util.ChangeFragmentHelper;

/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/4/19 11:50
 */

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;
    TextView tv_title;
    ImageView iv_add;
    OnShareClickListener shareListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout toolbar = (FrameLayout) findViewById(R.id.toolbar);
        tv_title = ((TextView) toolbar.findViewById(R.id.tv_title));
        iv_add = (ImageView) toolbar.findViewById(R.id.iv_add);
        tv_title.setText("首页");

        Fragment fragment = new HomeFragment();

        ChangeFragmentHelper helper = new ChangeFragmentHelper();
        helper.setTargetFragment(fragment);
        helper.setIsClearStackBack(true);

        changeFragment(helper);

        initView();


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }


    public interface OnShareClickListener {
        void onClicked();
    }

    private void initView() {

        //盛放Fragment的容器
        FrameLayout main_container = ((FrameLayout) findViewById(R.id.main_container));

        RadioGroup main_tabBar = ((RadioGroup) findViewById(R.id.main_tabBar));

        main_tabBar.check(R.id.main_home);

        fragment = null;

        main_tabBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_home:
                        tv_title.setText("首页");
                        iv_add.setVisibility(View.INVISIBLE);
                        fragment = new HomeFragment();
                        break;
                    case R.id.main_share:
                        tv_title.setText("心愿");
                        iv_add.setVisibility(View.VISIBLE);
                        fragment = new LoveFragment();
                        shareListener = (OnShareClickListener) fragment;
                        iv_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareListener.onClicked();
                            }
                        });
                        break;
                    case R.id.main_discover:
                        iv_add.setVisibility(View.INVISIBLE);
                        tv_title.setText("发现");
                        fragment = new DiscoverFragment();
                        break;
                    case R.id.main_person:
                        iv_add.setVisibility(View.INVISIBLE);
                        tv_title.setText("我的");
                        fragment = new MyFragment();
                        break;

                }

                ChangeFragmentHelper helper = new ChangeFragmentHelper();
                helper.setTargetFragment(fragment);
                helper.setIsClearStackBack(true);
                changeFragment(helper);
            }
        });

    }

    public void changeFragment(ChangeFragmentHelper helper) {

        //获取需要跳转的Fragment
        Fragment targetFragment = helper.getTargetFragment();
        //获取是否清空回退栈
        boolean clearStackBack = helper.isClearStackBack();
        //获取是否加入回退栈
        String targetFragmentTag = helper.getTargetFragmentTag();
        //获取Bundle
        Bundle bundle = helper.getBundle();
        //是否给Fragment传值
        if (bundle != null) {
            targetFragment.setArguments(bundle);
        }

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (targetFragment != null) {
            fragmentTransaction.replace(R.id.main_container, targetFragment);
        }

        //是否添加到回退栈
        if (targetFragmentTag != null) {
            fragmentTransaction.addToBackStack(targetFragmentTag);
        }

        //是否清空回退栈
        if (clearStackBack) {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        fragmentTransaction.commit();
    }
}
