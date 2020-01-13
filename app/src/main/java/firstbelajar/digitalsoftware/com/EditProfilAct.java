package firstbelajar.digitalsoftware.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfilAct extends AppCompatActivity {

    EditText nama_lengkap, bio, username, password, email_address;
    ImageView photo_edit_profile;
    Button btn_add_photo, btn_save;
    LinearLayout btn_back;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storageReference;
    String USER_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        getUsernameLocal();

        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);
        photo_edit_profile = findViewById(R.id.photo_edit_profile);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);

        storageReference = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                username.setText(dataSnapshot.child("username").getValue().toString());
                password.setText(dataSnapshot.child("password").getValue().toString());
                email_address.setText(dataSnapshot.child("email_address").getValue().toString());
                try{
                    Picasso.with(EditProfilAct.this).load(dataSnapshot.child("url_photo_profile")
                            .getValue().toString())
                            .centerCrop()
                            .fit().into(photo_edit_profile);
                }catch (NullPointerException ignored){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ubah State Menjadi Loading
                btn_save.setEnabled(false);
                btn_save.setText("Loading ...");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(bio.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (photo_location !=null){
                    final StorageReference storageReference1 = storageReference
                            .child(System.currentTimeMillis()
                                    + "." + getFileExtension(photo_location));

                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uri_photo = taskSnapshot.getUploadSessionUri().toString();
                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });
                }

                //Berpindah Activity
                Intent gotomyprofile = new Intent(EditProfilAct.this, MyProfileAct.class);
                startActivity(gotomyprofile);
                finish();

            }
        });

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(EditProfilAct.this, HomeAct.class);
                startActivity(gotohome);
                finish();
            }
        });



    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data !=null && data.getData() !=null);
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_edit_profile);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
