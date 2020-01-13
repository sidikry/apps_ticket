package firstbelajar.digitalsoftware.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyProfileAct extends AppCompatActivity {

    Button btn_edit_profile, btn_back, btn_sign_out;
    ImageView photo_profile;
    TextView nama_lengkap, bio;

    DatabaseReference reference, reference2;

    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    RecyclerView myticket_place;
    ArrayList<MyTicket> list;
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_back = findViewById(R.id.btn_back);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        photo_profile = findViewById(R.id.photo_profile);
        myticket_place = findViewById(R.id.myticket_place);
        myticket_place.setLayoutManager(new LinearLayoutManager(this));

            list = new ArrayList<MyTicket>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                try{
                    Picasso.with(MyProfileAct.this).load(dataSnapshot.child("url_photo_profile")
                            .getValue().toString())
                            .centerCrop().fit().into(photo_profile);
                }catch (NullPointerException ignored){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoedit = new Intent(MyProfileAct.this, EditProfilAct.class);
                startActivity(gotoedit);
                finish();
            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child("wisata");
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MyTicket p = dataSnapshot1.getValue(MyTicket.class);
                    list.add(p);
                }
                ticketAdapter = new TicketAdapter(MyProfileAct.this, list);
                myticket_place.setAdapter(ticketAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(MyProfileAct.this, HomeAct.class);
                startActivity(gotohome);
                finish();
            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menghapus Isi / Nilai / Value dari username lokal
                SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                //Pindah Activity
                Intent gotosignin = new Intent(MyProfileAct.this, SignInAct.class);
                startActivity(gotosignin);
                finish();
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
