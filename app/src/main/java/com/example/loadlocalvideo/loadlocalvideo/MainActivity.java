package com.example.loadlocalvideo.loadlocalvideo;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvVideos;
    private CommonAdapter<LocalVideoBean> adapter;
    private List<LocalVideoBean> datas = new ArrayList<>();
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        datas = getList(getApplicationContext());
        rvVideos = (RecyclerView) findViewById(R.id.rv_videos);
        videoView = (VideoView) findViewById(R.id.videoview);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVisibility(View.GONE);
            }
        });

        setAdapter();
    }

    private void setAdapter(){

        rvVideos.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        adapter = new CommonAdapter<LocalVideoBean>(getApplicationContext(),R.layout.item_choosevideo,datas) {

            @Override
            protected void convert(ViewHolder holder, LocalVideoBean localVideoBean, int position) {
                ImageView ivCover = holder.getView(R.id.iv_video);
                TextView tvDuration = holder.getView(R.id.tv_videoname);

                Glide.with(getApplicationContext()).load(localVideoBean.getPath()).into(ivCover);

                int minSec = (int) (localVideoBean.getDuration()/1000);
                String minSecDuration = parseTime(minSec);
                tvDuration.setText(minSecDuration);
            }

        };

        rvVideos.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                videoView.setVisibility(View.VISIBLE);
                playVideo(datas.get(position).getPath());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void playVideo(String path){
        videoView.setVideoPath(path);
        videoView.start();
    }

    public List<LocalVideoBean> getList(Context context) {
        List<LocalVideoBean> sysVideoList = new ArrayList<>();
        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID};
        // 视频其他信息的查询条件
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media
                        .EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);

        if (cursor == null) {
            return sysVideoList;
        }
        if (cursor.moveToFirst()) {
            do {
                LocalVideoBean info = new LocalVideoBean();

                info.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media
                        .DATA)));
                info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.DURATION)));
                sysVideoList.add(info);
            } while (cursor.moveToNext());
        }
        return sysVideoList;
    }

    /**
     * 将秒转为 00:00显示模式
     */
    public static String parseTime(int seconds) {
        StringBuilder stringBuilder = new StringBuilder();
        int min = seconds / 60;
        int sec = seconds % 60;
        int minHeigh = min / 10;
        int minLow = min % 10;
        int secHeight = sec / 10;
        int secLow = sec % 10;
        stringBuilder.append(minHeigh).append(minLow).append(":").append(secHeight).append(secLow);
        return stringBuilder.toString();
    }
}
