package com.github.aarsy.notesplay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends Activity {


    private Context context = null;
    private AlertDialog alertDialog;
    private boolean play=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setFinishOnTouchOutside(false);

//        setContentView(R.layout.activity_main);
        context = this;
        alertDailog(context);


    }


    MediaPlayer mPlay;
    int i = 0;
    String text = "";
    ImageView play_stop;

    private void alertDailog(Context activity) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        // set title
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.activity_main, null);

        alertDialogBuilder.setView(view);

        final EditText notes = (EditText) view.findViewById(R.id.et_notes);
        notes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
                final PopupMenu popup = new PopupMenu(wrapper, v);
                popup.getMenuInflater().inflate(R.menu.pop_up_paste, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.paste) {
                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            notes.append(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu

                return true;

            }
        });
        LinearLayout ll_close = (LinearLayout) view.findViewById(R.id.ll_close);
        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });
        play_stop = (ImageView) view.findViewById(R.id.play_pause);
//        SoundPoolPlayer soundPoolPlayer=new SoundPoolPlayer(context);

        mPlay = new MediaPlayer();


        play_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = notes.getText().toString().toLowerCase();
                i = 0;
                if (play) {
                    mPlay.release();
                    play=false;
                    play_stop.setImageResource(R.drawable.ic_play_button);
                }else if (i < text.length()) {
                    play=true;
                    String label = ("" + text.charAt(i) + text.charAt(i + 1));
                    Log.d("label", "  " + label);
                    switch (label) {
                        case "a1":
                            mPlay = MediaPlayer.create(context, R.raw.a1);
                            break;
                        case "b1":
                            mPlay = MediaPlayer.create(context, R.raw.b1);
                            break;
                        case "c1":
                            mPlay = MediaPlayer.create(context, R.raw.c1);
                            break;
                        case "d1":
                            mPlay = MediaPlayer.create(context, R.raw.d1);
                            break;
                        case "e1":
                            mPlay = MediaPlayer.create(context, R.raw.e1);
                            break;
                        case "f1":
                            mPlay = MediaPlayer.create(context, R.raw.f1);
                            break;

                    }
                    mPlay.start();
                    play_stop.setImageResource(R.drawable.ic_stop_button);
                    mPlay.setOnCompletionListener(listener);
                    i += 2;
                }
            }
        });


        // set dialog message
        alertDialogBuilder
                .setCancelable(false);

        // create alert dialog
        alertDialog=alertDialogBuilder.create();
        alertDialog.show();


        // show it
    }

    MediaPlayer.OnCompletionListener listener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.d("label", "  aagya");
            try{
                mp.release();
            }catch (Exception e){

            }
            playSound();
        }
    };

    private void playSound() {

        if (i +1 < text.length()) {
            if (text.charAt(i) == '.') {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("aaagya", "dasd");
                        try {
                            Thread.sleep(50);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    playSound();
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                i++;
            }else{
                if(i+1< text.length()) {
                    try {
                        String label = ("" + text.charAt(i) + text.charAt(i + 1));
                        Log.d("label11", "  " + label);
                        switch (label) {
                            case "a1":
                                mPlay = MediaPlayer.create(context, R.raw.a1);
                                break;
                            case "b1":
                                mPlay = MediaPlayer.create(context, R.raw.b1);
                                break;
                            case "c1":
                                mPlay = MediaPlayer.create(context, R.raw.c1);
                                break;
                            case "d1":
                                mPlay = MediaPlayer.create(context, R.raw.d1);
                                break;
                            case "e1":
                                mPlay = MediaPlayer.create(context, R.raw.e1);
                                break;
                            case "f1":
                                mPlay = MediaPlayer.create(context, R.raw.f1);
                                break;
                        }
                        mPlay.start();
                        mPlay.setOnCompletionListener(listener);
                        i += 2;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }else{
            play_stop.setImageResource(R.drawable.ic_play_button);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //alertDialog.dismiss();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlay.release();
    }
}