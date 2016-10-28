package lg.android.pro01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ye on 10/28/16.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 检查当前网络
     * @param view
     */
    public void checkNetStatus(View view){
        Toast.makeText(this,"网络状态",Toast.LENGTH_SHORT).show();

    }

    /**
     * 使用摄像头拍照或者摄影
     * @param view
     */
    public void useCamera(View view){
        Toast.makeText(this,"摄像头",Toast.LENGTH_SHORT).show();
    }



}
