package com.sonam.apps.googleimagesearch.search;

import com.sonam.apps.googleimagesearch.model.Image;

import java.util.List;

/**
 * Created by sonam on 2/11/2018.
 */

public interface ImagesContractor {

    interface View {

        void showImages(List<Image> images);

    }
}
