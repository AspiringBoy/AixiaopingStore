package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.model.widget.PreviewFgView;
import com.axp.axpseller.presenter.PreviewFgPresenter;
import com.axp.axpseller.view.IShowPreviewFg;

/**
 * Created by YY on 2018/1/3.
 */
public class PreviewFragment extends BaseFragment implements IShowPreviewFg{
    private View fgView;
    private PreviewFgPresenter presenter;
    private PreviewFgView mPreviewFgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_preview,container,false);
        presenter = new PreviewFgPresenter(this);
        presenter.showFgView();
        return fgView;
    }

    @Override
    public void operateView() {

    }

    @Override
    public void initView() {
        mPreviewFgView = new PreviewFgView(fgView,getActivity());
    }

    @Override
    public void initData() {

    }
}
