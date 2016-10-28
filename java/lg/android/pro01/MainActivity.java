package lg.android.pro01;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ye on 10/28/16.
 */

public class MainActivity extends AppCompatActivity {
    private final String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 检查当前网络
     *
     * @param view
     */
    public void checkNetStatus(View view) {

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            Log.i(tag, "无网络连接！");
            Toast.makeText(this, "提醒：无网络连接!", Toast.LENGTH_SHORT).show();
            return;
        }
        NetworkInfo.DetailedState detailedState = info.getDetailedState();
        NetworkInfo.State state = info.getState();
        // detailedState.ts:CONNECTED...detailedState.name:CONNECTED...state.name:CONNECTED...getExtraInfo:cmnet...getReason:connected...getTypeName:MOBILE...getSubtypeName:LTE
        Log.i(tag, "detailedState.ts:" + detailedState.toString() + "...detailedState.name:" + detailedState.name() + "...state.name:" + state.name()  //等效
                + "...getExtraInfo:" + info.getExtraInfo() + "...getReason:" + info.getReason() + "...getTypeName:" + info.getTypeName() //wifi名字，reason连接wifi时为null,流量则是connected,wifi/mobile
                + "...getSubtypeName:" + info.getSubtypeName());//4G:LTE

        Toast.makeText(this, "detailedState.ts:" + detailedState.toString() + "\r\ndetailedState.name:" + detailedState.name() + "\r\nstate.name:" + state.name()  //等效
                + "\ngetExtraInfo:" + info.getExtraInfo() + "\ngetReason:" + info.getReason() + "\ngetTypeName:" + info.getTypeName() //wifi名字，reason连接wifi时为null,流量则是connected,wifi/mobile
                + "\ngetSubtypeName:" + info.getSubtypeName(), Toast.LENGTH_LONG).show();

    }

    /**
     * 使用摄像头拍照或者摄影
     *
     * @param view
     */
    public void useCamera(View view) {
        Toast.makeText(this, "摄像头", Toast.LENGTH_SHORT).show();
    }


}
