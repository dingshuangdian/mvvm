package com.lsq.easton.mvvm.viewmodel;
import android.databinding.ObservableInt;
import android.view.View;
import com.lsq.easton.mvvm.model.GithubService;
import com.lsq.easton.mvvm.model.Repo;
import java.util.List;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewModel {
    public ObservableInt progressVisibility = new ObservableInt(View.GONE);
    public ObservableInt errorMessageVisibility = new ObservableInt(View.GONE);
    public ObservableInt recycleViewVisibility = new ObservableInt(View.GONE);
    private List<Repo> repoList;
    private DataListener mListener;

    public MainViewModel(DataListener mListener) {
        this.mListener = mListener;
        loadGitHubJava();
    }

    private void loadGitHubJava() {
        progressVisibility.set(View.VISIBLE);
        String url = "http://github.laowch.com/json/java_daily";
        GithubService.Factory.create().javaRepositories(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        progressVisibility.set(View.GONE);
                        errorMessageVisibility.set(View.GONE);
                        recycleViewVisibility.set(View.VISIBLE);
                        if (mListener != null && repoList != null) {
                            mListener.repoDataChange(repoList);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressVisibility.set(View.GONE);
                        errorMessageVisibility.set(View.VISIBLE);
                        recycleViewVisibility.set(View.GONE);

                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        repoList = repos;

                    }
                });

    }

    public interface DataListener {
        void repoDataChange(List<Repo> repoList);

    }
}
