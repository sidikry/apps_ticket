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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTicketDetail extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_confirm;
    TextView nama_wisata, date_wisata, time_wisata, ketentuan, lokasi, jumlah_tiket;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);


        btn_back = findViewById(R.id.btn_back);
        nama_wisata = findViewById(R.id.nama_wisata);
        date_wisata = findViewById(R.id.date_wisata);
        time_wisata = findViewById(R.id.time_wisata);
        ketentuan = findViewById(R.id.ketentuan);
        lokasi = findViewById(R.id.lokasi);
        jumlah_tiket = findViewById(R.id.jumlah_tiket);
        btn_confirm = findViewById(R.id.btn_confirm);


        //Mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");

        //Database
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                date_wisata.setText(dataSnapshot.child("date_wisata").getValue().toString());
                time_wisata.setText(dataSnapshot.child("time_wisata").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosucces = new Intent(MyTicketDetail.this, SuccesRefundAct.class);
                startActivity(gotosucces);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototicket = new Intent(MyTicketDetail.this, MyProfileAct.class);
                startActivity(gototicket);
                finish();
            }
        });


    }
}
