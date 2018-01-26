package com.axp.axpseller.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;

/**
 * Created by xu on 2016/6/15.
 * 评论展示Fragment
 */
public class CommentFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_comment, container, false);
    }
}
