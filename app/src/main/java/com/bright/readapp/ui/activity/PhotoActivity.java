package com.bright.readapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseActivity;
import com.bright.readapp.ui.base.BasePresenter;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoActivity extends BaseActivity {

    public static final String TRANSIT_PIC = "picture";

    private String imgUrl;
    private String desc;

    @Bind(R.id.iv_meizhi_pic)
    ImageView iv_meizhi_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();

        ViewCompat.setTransitionName(iv_meizhi_pic, TRANSIT_PIC);
        Glide.with(this).load(imgUrl).centerCrop().into(iv_meizhi_pic);
        new PhotoViewAttacher(iv_meizhi_pic);
    }

    public static Intent newIntent(Context context, String imgUrl, String desc){
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("imgUrl",imgUrl);
        intent.putExtra("desc", desc);
        return intent;
    }

    public void saveImg(View v){
        iv_meizhi_pic.buildDrawingCache();
        Bitmap bitmap = iv_meizhi_pic.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/xianxia");
        if (!path.exists()){
            path.mkdir();
        }
        try {
            File file = new File(path, desc.substring(0,10)+".png");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            fos.close();

            //广播 通知相册更新
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            PhotoActivity.this.sendBroadcast(intent);
            Toast.makeText(PhotoActivity.this, "保存成功~", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photo;
    }

    public void getData() {
        imgUrl = getIntent().getStringExtra("imgUrl");
        desc = getIntent().getStringExtra("desc");
    }
}
