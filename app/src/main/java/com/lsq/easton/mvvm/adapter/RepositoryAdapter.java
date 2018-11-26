package com.lsq.easton.mvvm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsq.easton.mvvm.R;
import com.lsq.easton.mvvm.databinding.ItemRepoBinding;
import com.lsq.easton.mvvm.model.Repository;
import com.lsq.easton.mvvm.viewmodel.RepositoryItemViewModel;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {
    private Context mContext;
    private List<Repository> repositoryList;

    public RepositoryAdapter(Context mContext, List<Repository> repositoryList) {
        this.mContext = mContext;
        this.repositoryList = repositoryList;
    }


    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRepoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_repo, viewGroup, false);
        return new RepositoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder repositoryViewHolder, int i) {
        Repository repository = repositoryList.get(i);
        repositoryViewHolder.bindData(repository);

    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {

        ItemRepoBinding binding;

        public RepositoryViewHolder(@NonNull ItemRepoBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        public void bindData(Repository repository) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new RepositoryItemViewModel(mContext, repository));
            } else {
                binding.getViewModel().setRepository(repository);
            }
        }
    }
}
