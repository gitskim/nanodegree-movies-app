package com.bgirlogic.movies.ui.view;

import com.bgirlogic.movies.ui.model.DevilViewModel;

import java.util.List;

/**
 * Created by Senpai on 1/10/16.
 */
public interface DevilListView {

    void add(DevilViewModel model);

    void add(List<DevilViewModel> model);

    void remove(DevilViewModel model);
}
