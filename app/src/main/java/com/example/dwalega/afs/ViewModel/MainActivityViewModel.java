package com.example.dwalega.afs.ViewModel;

import android.arch.lifecycle.ViewModel;

import com.example.dwalega.afs.Model.Task;
import com.example.dwalega.afs.Utils.RecyclerViewTasksAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivityViewModel extends ViewModel {

    private RealmResults<Task> data;
    private Realm realm;
    private RecyclerViewTasksAdapter recyclerViewTasksAdapter;
    private boolean locked;

    public RealmResults<Task> downloadData() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        data = realm.where(Task.class).findAll();
        return data;
    }

    public void changeTaskStatus(Task task, String status, int position) {
        realm.beginTransaction();
        task.setStatus(status);
        realm.commitTransaction();
        recyclerViewTasksAdapter.notifyItemChanged(position);
    }

    public void setRecyclerViewTasksAdapter(RecyclerViewTasksAdapter recyclerViewTasksAdapter) {
        this.recyclerViewTasksAdapter = recyclerViewTasksAdapter;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
