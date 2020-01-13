package firstbelajar.digitalsoftware.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccesRefundAct extends AppCompatActivity {

    Button btn_my_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_refund);

        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccesRefundAct.this, HomeAct.class);
                startActivity(gotohome);
                finish();
            }
        });
    }
}
