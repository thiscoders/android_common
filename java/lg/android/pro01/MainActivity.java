package lg.android.pro01;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by ye on 10/28/16.
 */

public class MainActivity extends AppCompatActivity {
    private final String tag = MainActivity.class.getSimpleName();

    private EditText et_filePath;

    private int picCode = 0;//拍照码
    private int shootCode = 1;//摄影码
    private int pickPicCode = 2;//选择图片码
    private int pickVidCode = 3;//选择视频码


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_filePath = (EditText) findViewById(R.id.et_filePath);
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

        Toast.makeText(this, "detailedState.ts:" + detailedState.toString() + "\ndetailedState.name:" + detailedState.name() + "\nstate.name:" + state.name()  //等效
                + "\ngetExtraInfo:" + info.getExtraInfo() + "\ngetReason:" + info.getReason() + "\ngetTypeName:" + info.getTypeName() //wifi名字，reason连接wifi时为null,流量则是connected,wifi/mobile
                + "\ngetSubtypeName:" + info.getSubtypeName(), Toast.LENGTH_LONG).show();

    }

    /**
     * 使用摄像头拍照
     *
     * @param view
     */
    public void useCamera01(View view) {
        String path = et_filePath.getText().toString();
        File file = getDownFile(path, this.picCode);
        if (file == null) return;
        Log.i(tag, file.toString());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        this.startActivityForResult(intent, this.picCode);
    }

    /**
     * 使用摄像头拍摄
     *
     * @param view
     */
    public void useCamera02(View view) {
        String path = et_filePath.getText().toString();
        File file = getDownFile(path, this.shootCode);
        if (file == null) return;
        Log.i(tag, file.toString());

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        this.startActivityForResult(intent, this.shootCode);
    }

    /**
     * 选择图片
     *
     * @param view
     */
    public void pickPic(View view) {
        Intent intent = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, this.pickPicCode);
    }

    /**
     * 选择视频
     *
     * @param view
     */
    public void pickVid(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent, this.pickVidCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.picCode) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "拍照完成！", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "拍照取消！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == this.shootCode) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "摄影完成！", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "摄影取消！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == this.pickPicCode) {
            if (resultCode == RESULT_OK) {
                Uri selectImageUri = data.getData();
                String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};//要查询的列
                Cursor cursor = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
                String pirPath = null;
                while (cursor.moveToNext()) {
                    pirPath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));//所选择的图片路径
                }
                cursor.close();

                Toast.makeText(this, pirPath, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "没有选择图片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == this.pickVidCode) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null,
                        null, null);
                cursor.moveToFirst();
                String imgNo = cursor.getString(0); // 图片编号
                String v_path = cursor.getString(1); // 图片文件路径
                String v_size = cursor.getString(2); // 图片大小
                String v_name = cursor.getString(3); // 图片文件名
                cursor.close();
                Log.i(tag, imgNo + "\r\n" + v_path + "..." + v_size + "..." + v_name);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "没有选择视频！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 1、/sdcard/abc/de    len=14 index=11
     * 2、/sdcard/abc/de/  len=15 index=14
     * 3、/sdcard/abc/de/11.jpg
     *
     * @param path
     * @return
     */
    private File getDownFile(String path, int code) {
        //pathweinull，默认放到/storage/emulated/0/Android/data/packname/下，并且以当前时间毫秒数命名
        if (path == null || path.isEmpty()) {
            if (code == this.picCode) {
                File dir = this.getExternalFilesDir("picture");
                return new File(dir, System.currentTimeMillis() + ".jpg");
            } else if (code == this.shootCode) {
                File dir = this.getExternalFilesDir("video");
                return new File(dir, System.currentTimeMillis() + ".mp4");
            }
        }

        int len = path.length();
        int index = path.lastIndexOf("/");
        boolean ispoint = path.contains(".");
        if (!ispoint) {//不包含文件名,则将文件放到用户指定的地方，以毫秒数命名文件
            if (len > index + 1) //path不以'/'结尾
                path += "/";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            return new File(dir, System.currentTimeMillis() + (code == this.picCode ? ".jpg" : ".mp4"));
        }

        int pindex = path.indexOf('.');
        if (pindex < index) {
            Toast.makeText(this, "路径中不能有'.',请重新输入！", Toast.LENGTH_SHORT).show();
            return null;
        }
        //path包含文件名
        String dirs = path.substring(0, index);
        String title = path.substring(index + 1);

        File dir = new File(dirs);
        if (!dir.exists())
            dir.mkdirs();
        return new File(dir, title);
    }

}
