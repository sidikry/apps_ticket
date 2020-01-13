package firstbelajar.digitalsoftware.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_ticket_pisa, btn_ticket_monas,
            btn_ticket_candi, btn_ticket_sphinx, btn_ticket_pagoda, btn_ticket_torri;
    CircleView photo_user;
    ImageView pic_photo_home_user;
    TextView bio, user_balance, nama_lengkap;

    DatabaseReference reference;

    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        photo_user = findViewById(R.id.photo_user);
        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_ticket_candi = findViewById(R.id.btn_ticket_candi);
        btn_ticket_monas = findViewById(R.id.btn_ticket_monas);
        btn_ticket_pagoda = findViewById(R.id.btn_ticket_pagoda);
        btn_ticket_sphinx = findViewById(R.id.btn_ticket_sphinx);
        btn_ticket_torri = findViewById(R.id.btn_ticket_torri);
        pic_photo_home_user = findViewById(R.id.pic_photo_home_user);
        bio = findViewById(R.id.bio);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("US$ " + dataSnapshot.child("userbalance").getValue().toString());
                try{
                    Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile")
                            .getValue().toString())
                            .centerCrop()
                            .fit().into(pic_photo_home_user);
                }catch(NullPointerException ignored){
                    System.out.println("Something went wrong.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_ticket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Pisa");
                startActivity(gotopisaticket);
                finish();
            }
        });

        btn_ticket_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototorriticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gototorriticket.putExtra("jenis_tiket", "Torri");
                startActivity(gototorriticket);
                finish();
            }
        });

        btn_ticket_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomonasticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotomonasticket.putExtra("jenis_tiket", "Monas");
                startActivity(gotomonasticket);
                finish();
            }
        });

        btn_ticket_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocanditicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotocanditicket.putExtra("jenis_tiket", "Candi");
                startActivity(gotocanditicket);
                finish();
            }
        });

        btn_ticket_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosphinxticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotosphinxticket.putExtra("jenis_tiket", "Sphinx");
                startActivity(gotosphinxticket);
                finish();
            }
        });

        btn_ticket_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopagodaticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotopagodaticket.putExtra("jenis_tiket", "Pagoda");
                startActivity(gotopagodaticket);
                finish();
            }
        });

        photo_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
                finish();
            }
        });

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
