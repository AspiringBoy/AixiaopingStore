package com.axp.axpseller.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.model.widget.MyScoreFgView;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.presenter.MyScorePresenter;
import com.axp.axpseller.view.IShowPreviewTopFg;

/**
 * Created by YY on 2018/1/10.
 */
public class MyScoreFragment extends BaseFragment implements IShowPreviewTopFg{
    private View fgView;
    private MyScorePresenter presenter;
    private MyScoreFgView myScoreFgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_my_score,container,false);
        presenter = new MyScorePresenter(this);
        presenter.showFgView(getActivity());
        return fgView;
    }

    @Override
    public void showView(DataList<RecyclerViewModel> dataList) {
        myScoreFgView.bindView(dataList);
    }

    @Override
    public void initView() {
        myScoreFgView = new MyScoreFgView(this,fgView);
    }

    @Override
    public void initData() {
        myScoreFgView.initData();
    }

    public void refreshData(){
        presenter.refreshData();
    }

    public void loadMore(){
        presenter.loadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshData();
    }
}
