package firstbelajar.digitalsoftware.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GetStartedAct extends AppCompatActivity {

    Button btn_sign_in,btn_new_account_create;
    Animation ttb, btt;
    ImageView emblem_app;
    TextView landing_hero;

    //Variabel, String, boolean, integer, dll

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);
        emblem_app = findViewById(R.id.emblem_app);
        landing_hero = findViewById(R.id.landing_hero);
        //Load Animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //Run Animation
        emblem_app.startAnimation(ttb);
        landing_hero.startAnimation(ttb);
        btn_sign_in.startAnimation(btt);
        btn_new_account_create.startAnimation(btt);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosign = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(gotosign);
                finish();
            }
        });

        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gogetregister = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gogetregister);
                finish();
            }
        });



        //Load Element


    }
}
