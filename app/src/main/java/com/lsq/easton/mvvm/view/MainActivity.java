package com.lsq.easton.mvvm.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lsq.easton.mvvm.R;
import com.lsq.easton.mvvm.adapter.RepoListAdapter;

import com.lsq.easton.mvvm.databinding.ActivityMainBinding;
import com.lsq.easton.mvvm.model.Repo;
import com.lsq.easton.mvvm.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {
    private ActivityMainBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel viewModel = new MainViewModel(this);
        binding.setMainViewModel(viewModel);
        binding.textDescription.setText(R.string.github_java);

    }

    @Override
    public void repoDataChange(List<Repo> repoList) {
        RepoListAdapter adapter = new RepoListAdapter(this, repoList);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

    }
}
