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

public class SuccesBuyTicketAct extends AppCompatActivity {

    Button btn_my_dashboard, btn_view_ticket;
    Animation app_splash, btt, ttb;
    ImageView logo_success_buy;
    TextView  title_success_buy, subtitle_success_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy_ticket);

        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        logo_success_buy = findViewById(R.id.logo_succes_buy);
        title_success_buy = findViewById(R.id.title_success_buy);
        subtitle_success_buy = findViewById(R.id.subtitle_success_buy);

        //Load Animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodashboard = new Intent(SuccesBuyTicketAct.this, HomeAct.class);
                startActivity(gotodashboard);
                finish();
            }
        });

        //Run Animation
        logo_success_buy.startAnimation(app_splash);
        title_success_buy.startAnimation(ttb);
        subtitle_success_buy.startAnimation(ttb);
        btn_my_dashboard.startAnimation(btt);
        btn_my_dashboard.startAnimation(btt);
    }
}
