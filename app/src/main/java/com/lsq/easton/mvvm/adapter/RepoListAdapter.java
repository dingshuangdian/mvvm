package com.lsq.easton.mvvm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsq.easton.mvvm.R;
import com.lsq.easton.mvvm.databinding.RepoListItemBinding;
import com.lsq.easton.mvvm.model.Repo;
import com.lsq.easton.mvvm.viewmodel.ItemRepoViewModel;

import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {
    private Context mContext;
    private List<Repo> repoList;

    public RepoListAdapter(Context mContext, List<Repo> repoList) {
        this.mContext = mContext;
        this.repoList = repoList;
    }

    public void setRepoList(List<Repo> repoList) {
        this.repoList = repoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RepoListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.repo_list_item, viewGroup, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Repo repo = repoList.get(i);
        viewHolder.bindData(repo);

    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RepoListItemBinding binding;

        public ViewHolder(RepoListItemBinding binding) {
            super(binding.repoCard);
            this.binding = binding;
        }

        public void bindData(Repo repo) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemRepoViewModel(mContext, repo));
            } else {
                binding.getViewModel().setRepo(repo);
            }
        }
    }
}
