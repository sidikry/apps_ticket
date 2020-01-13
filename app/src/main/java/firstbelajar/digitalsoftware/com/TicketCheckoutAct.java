package firstbelajar.digitalsoftware.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_pay_now, btn_plus, btn_minus;
    ImageView notice_uang;
    TextView jumlah_tiket, harga_tiket, my_balance, nama_wisata, lokasi, ketentuan;
    Integer valuejumlahtiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisa_balance = 0;

    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    // Generate nomor integer secara random
    //Karena kita ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    DatabaseReference reference, reference2, reference3, reference4, ref_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        //Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_back = findViewById(R.id.btn_back);
        btn_pay_now = findViewById(R.id.btn_pay_now);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        jumlah_tiket = findViewById(R.id.jumlah_tiket);
        harga_tiket = findViewById(R.id.harga_tiket);
        my_balance   = findViewById(R.id.my_balance);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);
        nama_wisata = findViewById(R.id.nama_wisata);
        notice_uang = findViewById(R.id.notice_uang);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(TicketCheckoutAct.this, HomeAct.class);
                startActivity(gotohome);
                finish();
            }
        });

        //setting value baru untuk beberapa komponen
        jumlah_tiket.setText(valuejumlahtiket.toString());

        //Secara Default Hide Button Minus
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        //Mengambil data user dari firebase 2

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("userbalance").getValue().toString());
                my_balance.setText("US$"+mybalance+"");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //Ambil data dari firebase 1
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                harga_tiket.setText("US$"+valuetotalharga+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                valuejumlahtiket+=1;
                jumlah_tiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket > 1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                harga_tiket.setText("US $"+ valuetotalharga+"");
                if (valuetotalharga > mybalance){
                    btn_pay_now.animate().translationY(250)
                            .alpha(0).setDuration(350).start();
                    btn_pay_now.setEnabled(false);
                    my_balance.setTextColor(Color.parseColor("#d1206b"));

                    notice_uang.setVisibility(View.VISIBLE);
                }


            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahtiket-=1;
                jumlah_tiket.setText(valuejumlahtiket.toString());

                if (valuejumlahtiket < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                harga_tiket.setText("US $"+ valuetotalharga+"");
                if (valuetotalharga < mybalance){
                    btn_pay_now.animate().translationY(0)
                            .alpha(1).setDuration(350).start();
                    btn_pay_now.setEnabled(true);
                    my_balance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });


        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Menyimpan data user kepada firebase dan Membuat tabel baru " MyTickets
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                        .child(username_key_new).child("wisata").child(nama_wisata.getText().toString() + nomor_transaksi );
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valuejumlahtiket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        //Fungsi Intent
                        Intent gotosucces = new Intent(TicketCheckoutAct.this, SuccesBuyTicketAct.class);
                        startActivity(gotosucces);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref_username = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new)
                        .child("username");

                ref_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ref_username.getRef().setValue(username_key_new);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Update data balance kepada users (yang saat ini login)
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("userbalance").setValue(sisa_balance);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
