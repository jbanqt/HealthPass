package com.lorenzana.hackafest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolBar;
    Button generate;
    private EditText editText_fname, editText_mname, editText_lname, editText_gender, editText_bday, editText_contact, editText_address;
    private FirebaseDatabase rootNode;
    private FirebaseUser firebaseUserr;
    private DatabaseReference reference;
    private String userID;



    @BindView(R.id.etF_Name)
    EditText firstName;
    @BindView(R.id.etM_Name)
    EditText middleName;
    @BindView(R.id.etL_Name)
    EditText lastName;
    @BindView(R.id.etContact)
    EditText contactNo;
    @BindView(R.id.etAddress)
    EditText Address;
    @BindView(R.id.etGender)
    EditText Gender;
    @BindView(R.id.etBirthday)
    EditText Bday;

    @BindView(R.id.image_view)
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        ButterKnife.bind(this);


        editText_fname = (EditText) findViewById(R.id.etF_Name);
        editText_mname = (EditText) findViewById(R.id.etM_Name);
        editText_lname = (EditText) findViewById(R.id.etL_Name);
        editText_gender = (EditText) findViewById(R.id.etGender);
        editText_bday= (EditText) findViewById(R.id.etBirthday);
        editText_contact= (EditText) findViewById(R.id.etContact);
        editText_address = (EditText) findViewById(R.id.etAddress);

        //initialize generate button
        generate =(Button) findViewById(R.id.btn_generate);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolBar,
                R.string.nav_drawer_open,
                R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);



    }




    void initQRCode(){

        String name = "Name : " + firstName.getText().toString() + " " + middleName.getText().toString() + " " +lastName.getText().toString();
        String gender = "Gender: " + Gender.getText().toString();
        String bday = "Birthday: " + Bday.getText().toString();
        String address = "Address: " + Address.getText().toString();
        String contact = "Contact No: " + contactNo.getText().toString();

        StringBuilder textToSend = new StringBuilder();
        textToSend.append(name + " | " + gender + " | " + bday + " | " + address + " | " + contact);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textToSend.toString(), BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }



    @OnClick(R.id.btn_generate)
    public void onViewClicked() {
        initQRCode();

    }





    // for drawer/nav
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.home:
                break;
            case R.id.qrCode:
                startActivity(new Intent(this, MainActivityUser.class));
                finish();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(MainActivityUser.this, "Successfully logged out.", Toast.LENGTH_LONG).show();
                finish();
                break;
        }


        return true;
    }
}