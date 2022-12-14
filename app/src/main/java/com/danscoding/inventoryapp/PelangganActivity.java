package com.danscoding.inventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PelangganActivity extends AppCompatActivity implements PelangganAdapter.FirebaseDataListener {

    private ExtendedFloatingActionButton mFloatingActionButton;
    private EditText mEditNama;
    private EditText mEditDomisili;
    private EditText mEditGender;
    private RecyclerView mRecyclerView;
    private PelangganAdapter mAdapter;
    private ArrayList<ModelPelanggan> daftarPelanggan;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);

//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                getWindow().getDecorView()
//                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        }

//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("pelanggan");
        mDatabaseReference.child("data_pelanggan").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                daftarPelanggan = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    ModelPelanggan pelanggan = mDataSnapshot.getValue(ModelPelanggan.class);
                    pelanggan.setKey(mDataSnapshot.getKey());
                    daftarPelanggan.add(pelanggan);
                }

                mAdapter = new PelangganAdapter(PelangganActivity.this, daftarPelanggan);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PelangganActivity.this,
                        databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        mFloatingActionButton = findViewById(R.id.tambah_pelanggan);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTambahPelanggan();
            }
        });

    }

    private void dialogTambahPelanggan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Pelanggan");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_pelanggan, null);

        mEditNama = view.findViewById(R.id.nama_lengkap);
        mEditDomisili = view.findViewById(R.id.domisili);
        mEditGender = view.findViewById(R.id.gender);
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String namaPelanggan = mEditNama.getText().toString();
                String domisiliPelanggan = mEditDomisili.getText().toString();
                String genderPelanggan = mEditGender.getText().toString();

                if (!namaPelanggan.isEmpty() && !domisiliPelanggan.isEmpty() && !genderPelanggan.isEmpty()) {
                    submitDataPelanggan(new ModelPelanggan(namaPelanggan, domisiliPelanggan, genderPelanggan));
                } else {
                    Toast.makeText(PelangganActivity.this, "Data Harus Diisi!", Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void submitDataPelanggan(ModelPelanggan pelanggan) {
        mDatabaseReference.child("data_pelanggan").push()
                .setValue(pelanggan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void mVoid) {
                        Toast.makeText(PelangganActivity.this, "Data pelanggan berhasil disimpan!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onDataClick(@Nullable ModelPelanggan pelanggan, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdatePelanggan(pelanggan);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataPelanggan(pelanggan);
            }
        });

        builder.setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void hapusDataPelanggan(ModelPelanggan pelanggan) {
        if (mDatabaseReference != null){
            mDatabaseReference.child("data_pelanggan").child(pelanggan.getKey())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void mVoid) {
                            Toast.makeText(PelangganActivity.this, "Data berhasil dihapus!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void dialogUpdatePelanggan(final ModelPelanggan pelanggan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Pelanggan");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_pelanggan, null);

        mEditNama = view.findViewById(R.id.nama_lengkap);
        mEditDomisili = view.findViewById(R.id.domisili);
        mEditGender = view.findViewById(R.id.gender);

        mEditNama.setText(pelanggan.getNama());
        mEditDomisili.setText(pelanggan.getDomisili());
        mEditGender.setText(pelanggan.getGender());
        builder.setView(view);

        if (pelanggan != null){
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    pelanggan.setNama(mEditNama.getText().toString());
                    pelanggan.setDomisili(mEditDomisili.getText().toString());
                    pelanggan.setGender(mEditGender.getText().toString());
                    updateDataPelanggan(pelanggan);
                }
            });
        }

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataPelanggan(ModelPelanggan pelanggan) {
        mDatabaseReference.child("data_pelanggan").child(pelanggan.getKey())
                .setValue(pelanggan).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void mVoid) {
                        Toast.makeText(PelangganActivity.this, "Data berhasil diupdate!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}