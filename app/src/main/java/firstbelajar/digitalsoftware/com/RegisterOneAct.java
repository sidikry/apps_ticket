package firstbelajar.digitalsoftware.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;

    EditText username, password, email_address;
    DatabaseReference reference;

    String USER_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btn_continue = findViewById(R.id.btn_continue);
        btn_back = findViewById(R.id.btn_back);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosignin = new Intent(RegisterOneAct.this, SignInAct.class);
                startActivity(gotosignin);
                finish();
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_continue.setEnabled(false);
                btn_continue.setText("Loading ...");

                //Simpan DAta Ke Lokal
                SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, username.getText().toString());
                editor.apply();

                //Simpan kedatabase
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                        dataSnapshot.getRef().child("userbalance").setValue(800);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                //Berpindah Activity
                Intent gotoselfie = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(gotoselfie);
                finish();
            }
        });
    }
}
