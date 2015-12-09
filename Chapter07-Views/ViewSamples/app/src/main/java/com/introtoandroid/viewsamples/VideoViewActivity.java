package com.introtoandroid.viewsamples;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoview);
        VideoView vv = (VideoView) findViewById(R.id.videoView);

        MediaController mc = new MediaController(this);
        Uri video = Uri.parse("http://www.archive.org/download/Unexpect2001/Unexpect2001_512kb.mp4");
        vv.setMediaController(mc);
        vv.setVideoURI(video);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
