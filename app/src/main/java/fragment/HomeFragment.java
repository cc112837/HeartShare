package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.cc.heartshare.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import view.LocalImageHolderView;


/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/4/21 11:12
 */
public class HomeFragment extends Fragment {
    private ListView lv_showid;
    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        lv_showid=(ListView) view.findViewById(R.id.lv_showid);

        List<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        SimpleAdapter sa = new SimpleAdapter(getContext(), list, R.layout.list_home_item,
                           new String[] { "cc" }, new int[] { R.id.tv_title });
        lv_showid.setAdapter(sa);
        loadLocalImage();
        View headview = LayoutInflater.from(getContext()).inflate(R.layout.headview, null);
        convenientBanner=(ConvenientBanner) headview.findViewById(R.id.cv_descid);
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dot_white, R.drawable.dots_gray})
                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        lv_showid.addHeaderView(headview);


    }
    private void loadLocalImage() {
        localImages.add(R.mipmap.for1);
        localImages.add(R.mipmap.for2);
        localImages.add(R.mipmap.for3);
    }

    @Override

    public void onResume() {

        super.onResume();

        //开始自动翻页
        convenientBanner.startTurning(2000);
    }


    // 停止自动翻页

    @Override

    public void onPause() {

        super.onPause();

        //停止翻页
        convenientBanner.stopTurning();

    }

}
