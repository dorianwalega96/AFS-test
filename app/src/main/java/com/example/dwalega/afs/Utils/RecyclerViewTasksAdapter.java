package com.example.dwalega.afs.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dwalega.afs.Model.Task;
import com.example.dwalega.afs.View.TaskView;
import com.example.dwalega.afs.ViewModel.MainActivityViewModel;
import com.example.dwalega.afs.databinding.TaskViewBinding;

import io.realm.RealmResults;

public class RecyclerViewTasksAdapter extends RecyclerView.Adapter<TaskView> {

    private RealmResults<Task> data;
    private MainActivityViewModel viewModel;

    public RecyclerViewTasksAdapter(MainActivityViewModel viewModel) {
        setHasStableIds(true);
        this.viewModel = viewModel;
        this.data = viewModel.downloadData();
    }

    @NonNull
    @Override
    public TaskView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        TaskViewBinding binding = TaskViewBinding.inflate(inflater, viewGroup, false);
        return new TaskView(binding, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskView taskView, int i) {
        taskView.bind(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

}
