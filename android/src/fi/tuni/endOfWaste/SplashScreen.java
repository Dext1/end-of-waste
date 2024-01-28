package fi.tuni.endOfWaste;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import fi.tuni.endOfWaste.R;

/**
 * @author Viljami Pietarila
 */
public class SplashScreen extends Activity implements MediaPlayer.OnCompletionListener, View.OnClickListener {
    VideoView videoView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //The splash video file is here at R.raw.filename. In this case it's splash.
        //It is the file name without the file type extension in the folder:
        //"android/res/raw/"
        String videoURI = "android.resource://" + getPackageName() + "/" + R.raw.splash;
        videoView = this.findViewById(R.id.videoPlayer);
        videoView.setVideoURI(Uri.parse(videoURI));
        //Comment the onClickListener out if you do not want the splash to be skippable
        videoView.setOnClickListener(this);
        videoView.setOnCompletionListener(this);
        videoView.start();
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        launchLibGdx();
    }
    @Override
    public void onClick(View v) {
        videoView.stopPlayback();
        launchLibGdx();
    }
    private void launchLibGdx() {
        videoView = null;
        Intent intent = new Intent(this, AndroidLauncher.class);
        startActivity(intent);
        finish();
    }
}