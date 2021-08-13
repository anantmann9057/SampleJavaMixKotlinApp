package com.example.samplekotlinapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.samplekotlinapplication.adapters.JavaGenericAdapter;
import com.example.samplekotlinapplication.apiservices.ServiceBuilder;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class JavaActivity extends AppCompatActivity implements JavaGenericAdapter.OnItemClickListener {
    JavaGenericAdapter javaAdapter;
    CompositeDisposable mCompositeDisposable;
    RecyclerView rvJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        rvJava = findViewById(R.id.rvJava);
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(ServiceBuilder.INSTANCE.buildService().getPics(1, 100)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setPicsAdapter, this::handleError));
    }

    public void setPicsAdapter(ArrayList<SpecialistsModel> picsList) {
        javaAdapter = new JavaGenericAdapter(picsList, this::onItemClick, R.layout.row_background_java);
        rvJava.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        rvJava.setAdapter(javaAdapter);
    }

    public void handleError(Throwable t) {
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(@Nullable View view, int position, @NonNull Object object) {

    }
}