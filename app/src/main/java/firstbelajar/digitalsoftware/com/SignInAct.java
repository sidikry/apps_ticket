package firstbelajar.digitalsoftware.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView create_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;

    //Deklarasi Variabel Untuk Menyimpan Data ke Lokal
    String USER_KEY = "usernamekey";
    String username_key = "";

    //Deklarasi Firebase
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        create_new_account = findViewById(R.id.create_new_account);

        create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gogetregister = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(gogetregister);
                finish();
            }
        });


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Ubah State menjadi Loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                //Valiadasi Login

                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Isi Username Dulu ya :)", Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }else {
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Isi Password Dahulu ya :)", Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }else {
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(username);


                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){

                                    //Ambil Data Password dari Firebase
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //Validasi Password dengan password firebase
                                    if (password.equals(passwordFromFirebase)){

                                        //Simpan User(Key ke Local)
                                        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        //Berpindah Activity
                                        Intent gotohome = new Intent(SignInAct.this, HomeAct.class);
                                        startActivity(gotohome);
                                        finish();

                                    }else {
                                        Toast.makeText(getApplicationContext(),"Password Salah !",
                                                Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(),"Username / Password Tidak Terdaftar",
                                            Toast.LENGTH_SHORT).show();
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Database Error !",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }
            }
        });
    }
}
