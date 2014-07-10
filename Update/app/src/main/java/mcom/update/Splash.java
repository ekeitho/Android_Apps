package mcom.update;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;



public class Splash extends Activity{
    MediaPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash); //this is how we make the java class use our splash layout we created


        sound = MediaPlayer.create(Splash.this, R.raw.splash);
        sound.start();


        Thread timer = new Thread() { // threads allow you to do multiple things at the same time
           public void run() { //thread looks for this method run
              try {
                 sleep(1000); //5000ms or 5 seconds
              } catch (InterruptedException e) {
                 e.printStackTrace();
              } finally {
                 Intent intent = new Intent("mcom.update.MENU"); //intent of the action name
                 startActivity(intent);
              }
           }
        };
        timer.start(); //always have to do start
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sound.release(); //say that we are just done with the music
        finish();
    }
}
