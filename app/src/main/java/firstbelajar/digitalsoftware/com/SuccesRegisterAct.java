package firstbelajar.digitalsoftware.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccesRegisterAct extends AppCompatActivity {
    Button btn_explore;
    Animation app_splash, btt, ttb;
    ImageView logo_succes_register;
    TextView title_succes_register, subtitle_succes_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_register);

        btn_explore = findViewById(R.id.btn_explore);

        //Load Animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        logo_succes_register = findViewById(R.id.logo_succes_register);
        title_succes_register = findViewById(R.id.title_success_register);
        subtitle_succes_register = findViewById(R.id.subtitle_success_register);


        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccesRegisterAct.this, HomeAct.class);
                startActivity(gotohome);
                finish();
            }
        });

         //Run animation
        btn_explore.startAnimation(btt);
        logo_succes_register.startAnimation(app_splash);
        title_succes_register.startAnimation(ttb);
        subtitle_succes_register.startAnimation(ttb);

    }
}
