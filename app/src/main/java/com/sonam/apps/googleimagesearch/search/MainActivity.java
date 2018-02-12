package com.sonam.apps.googleimagesearch.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sonam.apps.googleimagesearch.utils.EndlessRecyclerViewScrollListener;
import com.sonam.apps.googleimagesearch.R;
import com.sonam.apps.googleimagesearch.model.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ImagesContractor.View {

    private ImagesAdapter imagesAdapter;
    private ImagesPresenter imagesPresenter;
    ArrayList<Image> mImages;
    String query;

    @BindView(R.id.rvImages)
    RecyclerView rvImages;
    @BindView(R.id.etSearch)
    EditText etSearch;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mImages = new ArrayList<Image>();
        imagesPresenter = new ImagesPresenter(this);
        imagesAdapter = new ImagesAdapter(this, mImages);

        configureLayout();

    }

    @OnClick(R.id.btnSubmit)
    public void onClick() {
        query = etSearch.getText().toString();
        resetAdapter();
        imagesPresenter.getImages(query, 1);
    }

    @Override
    public void showImages(List<Image> images) {
        if (images != null) {
            closeSoftKeyboard();
            mImages.addAll(images);
            imagesAdapter.notifyDataSetChanged();
        }
    }

    private void configureLayout() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvImages.setLayoutManager(gridLayoutManager);
        rvImages.setAdapter(imagesAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                imagesPresenter.getImages(query, getNextPageIndex());
            }
        };
        rvImages.addOnScrollListener(scrollListener);
    }

    private void closeSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private int getNextPageIndex() {
        if (imagesAdapter.getItemCount() == 0) {
            return 0;
        } else {
            return imagesAdapter.getItemCount() + 1;
        }

    }

    private void resetAdapter() {
        mImages.clear();
        imagesAdapter.notifyDataSetChanged();
        scrollListener.onScrolled(rvImages, 0, 0);
    }

}
