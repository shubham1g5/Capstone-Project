package com.example.shubham.sixfourfantasy.matchdetail;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

public class ScoreHeaderViewModel extends BaseObservable {

    public static final int RUN_CARD_HEADER_TYPE = 1;
    public static final int WICKET_CARD_HEADER_TYPE = 2;

    public final ObservableField<Integer> mCardType = new ObservableField<>();

    public ScoreHeaderViewModel(int cardType) {
        mCardType.set(cardType);
    }
}
