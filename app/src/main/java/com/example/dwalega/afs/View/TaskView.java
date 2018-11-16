package com.example.dwalega.afs.View;

import android.support.v7.widget.RecyclerView;

import com.example.dwalega.afs.Model.Task;
import com.example.dwalega.afs.R;
import com.example.dwalega.afs.ViewModel.MainActivityViewModel;
import com.example.dwalega.afs.databinding.TaskViewBinding;

import static com.example.dwalega.afs.Model.Task.OPEN;
import static com.example.dwalega.afs.Model.Task.TRAVELLING;
import static com.example.dwalega.afs.Model.Task.WORKING;

public class TaskView extends RecyclerView.ViewHolder {

    private TaskViewBinding binding;
    private MainActivityViewModel viewModel;

    public TaskView(TaskViewBinding binding, MainActivityViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        this.viewModel = viewModel;
    }

    public void bind(Task task) {
        binding.setTask(task);
        binding.setViewCallback(this);
        if(!task.getStatus().equals(OPEN)){
            viewModel.setLocked(true);
        }
        binding.executePendingBindings();
    }

    public void onClick(Task task) {
        String status = task.getStatus();
        if (!(viewModel.isLocked() && status.equals(OPEN))) {
            switch (status) {
                case OPEN:
                    viewModel.setLocked(true);
                    viewModel.changeTaskStatus(task, TRAVELLING, getAdapterPosition());
                    break;
                case TRAVELLING:
                    viewModel.changeTaskStatus(task, WORKING, getAdapterPosition());
                    break;
                case WORKING:
                    viewModel.setLocked(false);
                    viewModel.changeTaskStatus(task, OPEN, getAdapterPosition());
            }
        }
    }

    public String getButtonText(String status) {
        switch (status) {
            case OPEN:
                return binding.getRoot().getContext().getResources().getString(R.string.start_travel);
            case TRAVELLING:
                return binding.getRoot().getContext().getResources().getString(R.string.start_work);
            case WORKING:
                return binding.getRoot().getContext().getResources().getString(R.string.stop);
            default:
                return "";
        }
    }

    public int getBackgroundColor(String status) {
        switch (status) {
            case OPEN:
                return binding.getRoot().getContext().getResources().getColor(R.color.colorGreen);
            case TRAVELLING:
                return binding.getRoot().getContext().getResources().getColor(R.color.colorYellow);
            case WORKING:
                return binding.getRoot().getContext().getResources().getColor(R.color.colorRed);
            default:
                return 0;
        }
    }

}
