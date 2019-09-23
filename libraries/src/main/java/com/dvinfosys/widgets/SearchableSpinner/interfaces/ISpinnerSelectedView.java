package com.dvinfosys.widgets.SearchableSpinner.interfaces;

import android.view.View;

public interface ISpinnerSelectedView {
    View getNoSelectionView();
    View getSelectedView(int position);
}
