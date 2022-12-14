package com.danscoding.inventoryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {

        }

    }

    @Override
    public void onDataClick(@Nullable ModelPelanggan pelanggan, int position) {

    }
}