package com.dhakaiyacoder.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.dhakaiyacoder.tvshow.R;
import com.dhakaiyacoder.tvshow.databinding.ItemContainerImageSliderBinding;


public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewSlider> {

    private String[] sliderImages;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ImageSliderViewSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater ==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerImageSliderBinding imageSliderBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_image_slider,parent,false);

        return new ImageSliderViewSlider(imageSliderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewSlider holder, int position) {
        holder.bindSliderImage(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImages.length;
    }

    static class ImageSliderViewSlider extends RecyclerView.ViewHolder{

        private ItemContainerImageSliderBinding itemContainerImageSliderBinding;

        public ImageSliderViewSlider(ItemContainerImageSliderBinding itemContainerImageSliderBinding) {
            super(itemContainerImageSliderBinding.getRoot());
            this.itemContainerImageSliderBinding = itemContainerImageSliderBinding;
        }

        public void bindSliderImage(String imageURL){

            itemContainerImageSliderBinding.setImageURL(imageURL);

        }

    }

}
