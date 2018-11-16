package com.example.dwalega.afs.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.dwalega.afs.Model.Task;
import com.example.dwalega.afs.R;
import com.example.dwalega.afs.Utils.RecyclerViewTasksAdapter;
import com.example.dwalega.afs.ViewModel.MainActivityViewModel;
import com.example.dwalega.afs.databinding.ActivityMainBinding;

import io.realm.Realm;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private RecyclerViewTasksAdapter recyclerViewTasksAdapter;
    private Realm realm;
    private SharedPreferences sharedPreferences;

    private final String[] names = {"Warsaw", "London", "New York", "Sydney", "Helsinki", "Paris", "Berlin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        sharedPreferences = getSharedPreferences("com.example.dwalega.afs", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("firstload", true)) {
            initData();
            sharedPreferences.edit().putBoolean("firstload", false).apply();
        }

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        binding.setLifecycleOwner(this);
        initRecyclerView();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            realm.beginTransaction();
            Task task = realm.createObject(Task.class, i);
            task.setName(names[i % 7]);
            realm.commitTransaction();
        }
    }

    private void initRecyclerView(){
        recyclerViewTasksAdapter = new RecyclerViewTasksAdapter(viewModel);
        LinearLayoutManager recyclerViewManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewManager.setOrientation(VERTICAL);
        binding.recyclerView.setLayoutManager(recyclerViewManager);
        binding.recyclerView.setAdapter(recyclerViewTasksAdapter);
        viewModel.setRecyclerViewTasksAdapter(recyclerViewTasksAdapter);
    }
}
