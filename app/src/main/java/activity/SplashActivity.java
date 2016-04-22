package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cc.heartshare.R;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/4/19 11:39
*/
public class SplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }});
        thread.start();
    }
}
