package com.lsq.easton.mvvm.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.inputmethod.InputMethodManager;

import com.lsq.easton.mvvm.R;
import com.lsq.easton.mvvm.adapter.RepositoryAdapter;
import com.lsq.easton.mvvm.databinding.ActivityUserRepoBinding;
import com.lsq.easton.mvvm.model.Repository;
import com.lsq.easton.mvvm.viewmodel.UserRepoViewModel;

import java.util.List;

public class UserRepoActivity extends AppCompatActivity implements UserRepoViewModel.RepositoryDataListener {
    private UserRepoViewModel viewModel;
    private ActivityUserRepoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_repo);
        viewModel = new UserRepoViewModel(this, this);
        binding.setUserViewModel(viewModel);
        String username = getIntent().getStringExtra("username");
        viewModel.loadGithubRepos(username);
        binding.editTextUsername.setText(username);


    }

    @Override
    public void repositoryDataChange(List<Repository> repositories) {
        RepositoryAdapter adapter = new RepositoryAdapter(this, repositories);
        binding.reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.reposRecyclerView.setAdapter(adapter);
        hideSoftKeyboard();

    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(), 0);
    }
}
