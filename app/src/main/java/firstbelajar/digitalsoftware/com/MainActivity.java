package firstbelajar.digitalsoftware.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation app_splash,btt;
    ImageView app_logo;
    TextView app_subtitle;

    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUsernameLocal();

        //Load Animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //Load element
        app_logo = findViewById(R.id.app_logo);
        app_subtitle = findViewById(R.id.app_sub_title);

        //run animation
        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(btt);
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

        if (username_key_new.isEmpty()){
            // Membuat timer untuk pindah activity secara otomatis
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Menuju halaman lain
                    Intent abc=new Intent(MainActivity.this, GetStartedAct.class);
                    startActivity(abc);
                    finish();
                }
            },2000); //2000 ms = 2s

        }else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogethome = new Intent(MainActivity.this, HomeAct.class);
                    startActivity(gogethome);
                    finish();
                }
            }, 2000);
        }
    }
}
