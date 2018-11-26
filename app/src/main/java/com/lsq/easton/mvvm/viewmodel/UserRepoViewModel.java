package com.lsq.easton.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.lsq.easton.mvvm.model.GithubService;
import com.lsq.easton.mvvm.model.Repository;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRepoViewModel {
    public ObservableInt searchButtonVisibility;
    public ObservableInt progressVisibility;
    public ObservableInt infoMessageVisibility;
    public ObservableInt recyclerViewVisibility;
    public String editTextUsernameValue;
    public List<Repository> repositoryList;
    private RepositoryDataListener mListener;
    private Context mContext;


    public UserRepoViewModel(RepositoryDataListener mListener, Context mContext) {
        this.mListener = mListener;
        this.mContext = mContext;
        searchButtonVisibility = new ObservableInt(View.VISIBLE);
        progressVisibility = new ObservableInt(View.VISIBLE);
        infoMessageVisibility = new ObservableInt(View.GONE);
        recyclerViewVisibility = new ObservableInt(View.GONE);
    }
    //搜索按钮的点击事件


    public void onClickSearch(View view) {
        loadGithubRepos(editTextUsernameValue);
    }

    public TextWatcher usernameEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextUsernameValue = s.toString();
                searchButtonVisibility.set(s.length() > 0 ? View.VISIBLE : View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public void loadGithubRepos(String editTextUsernameValue) {
        if (TextUtils.isEmpty(editTextUsernameValue)) return;
        progressVisibility.set(View.VISIBLE);
        infoMessageVisibility.set(View.GONE);
        recyclerViewVisibility.set(View.GONE);
        GithubService.Factory.create().publishRepositories(editTextUsernameValue)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        if (mListener != null) {
                            mListener.repositoryDataChange(repositoryList);
                            progressVisibility.set(View.GONE);
                            if (repositoryList != null && repositoryList.size() > 0) {
                                recyclerViewVisibility.set(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressVisibility.set(View.GONE);
                        recyclerViewVisibility.set(View.GONE);
                        infoMessageVisibility.set(View.VISIBLE);

                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        UserRepoViewModel.this.repositoryList = repositories;

                    }
                });
    }



    public interface RepositoryDataListener {
        void repositoryDataChange(List<Repository> repositories);
    }
}
