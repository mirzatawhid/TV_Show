package com.dhakaiyacoder.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dhakaiyacoder.tvshow.R;
import com.dhakaiyacoder.tvshow.databinding.ItemContainerTvshowsBinding;
import com.dhakaiyacoder.tvshow.models.TvShow;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {

    private List<TvShow> tvShows;

    private LayoutInflater layoutInflater;

    public TVShowAdapter(List<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvshowsBinding tvshowsBinding = DataBindingUtil.inflate(
                layoutInflater,R.layout.item_container_tvshows,parent,false
        );
        return new TVShowViewHolder(tvshowsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {

        holder.bindTVShow(tvShows.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }


    static class TVShowViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerTvshowsBinding itemContainerTvshowsBinding;

        public TVShowViewHolder(ItemContainerTvshowsBinding itemContainerTvshowsBinding) {
            super(itemContainerTvshowsBinding.getRoot());
            this.itemContainerTvshowsBinding = itemContainerTvshowsBinding;
        }

        public void bindTVShow(TvShow tvShow){
            itemContainerTvshowsBinding.setTvShow(tvShow);
            itemContainerTvshowsBinding.executePendingBindings();
        }

    }

}
