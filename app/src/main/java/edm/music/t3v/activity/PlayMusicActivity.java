package edm.music.t3v.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edm.music.t3v.R;
import edm.music.t3v.controller.MusicPlayerController;
import edm.music.t3v.listener.MusicPlayView;
import edm.music.t3v.model.Mp3Link;
import edm.music.t3v.network.service.SongRequest;
import edm.music.t3v.util.Constant;

public class PlayMusicActivity extends AppCompatActivity implements MusicPlayView, View.OnClickListener, SeekBar.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {


    @BindView(R.id.imgScreenplayer)
    ImageView imgScreenplayer;
    @BindView(R.id.SeekBarplay)
    SeekBar SeekBarplay;
    @BindView(R.id.tvDur1)
    TextView tvDur1;
    @BindView(R.id.tvDur2)
    TextView tvDur2;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCat)
    TextView tvCat;
    @BindView(R.id.backbtn)
    ImageView backbtn;
    @BindView(R.id.btnShare)
    ImageView btnShare;
    @BindView(R.id.btnComment)
    ImageButton btnComment;
    @BindView(R.id.fabplay)
    ImageView fabplay;
    @BindView(R.id.btnPrev)
    ImageView btnPrev;
    @BindView(R.id.btnNext)
    ImageView btnNext;
    @BindView(R.id.bgplay)
    LinearLayout bgplay;


    private String link;
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();
    private int mediaFileLengthInMilliseconds;
    private MusicPlayerController playerController;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        ButterKnife.bind(this);
        initView();

        playerController = new MusicPlayerController(this);
        playerController.getMusicToPlay(new SongRequest(link));

    }

    /**
     * This method initialise all the views in project
     */
    private void initView() {
//        buttonPlayPause = (ImageButton) findViewById(R.id.ButtonTestPlayPause);
        fabplay.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            link = bundle.getString(Constant.BUNDLE_URL, "");
            tvTitle.setText(bundle.getString(Constant.BUNDLE_SONG_NAME, ""));
            tvCat.setText(bundle.getString(Constant.BUNDLE_AUTHOR_NAME, ""));
        }

        SeekBarplay.setOnTouchListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabplay) {
            playMusic();
        }
    }

    private void playMusic() {
        /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
        try {
            mediaPlayer.setDataSource(link); // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
            mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            fabplay.setImageResource(R.drawable.pausebtn);
        } else {
            mediaPlayer.pause();
            fabplay.setImageResource(R.drawable.playbtn);
        }

        SeekBarplay.setMax(mediaPlayer.getDuration());

        primarySeekBarProgressUpdater();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
        fabplay.setImageResource(R.drawable.pausebtn);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /** Method which updates the SeekBar secondary progress by current song loading from URL position*/
        SeekBarplay.setSecondaryProgress(percent);
    }

    /**
     * Method which updates the SeekBar primary progress by current song playing position
     */
    private void primarySeekBarProgressUpdater() {
        SeekBarplay.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.SeekBarplay) {

            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/

            if (mediaPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) view;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    @Override
    public void onGetMusicSuccess(List<Mp3Link> links) {
        link = links.get(0).getSrc();

        playMusic();
    }

    @Override
    public void onGetMusicFailure() {

    }
}