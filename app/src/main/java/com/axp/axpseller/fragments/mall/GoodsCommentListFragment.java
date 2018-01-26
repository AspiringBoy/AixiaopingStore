package com.axp.axpseller.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.models.Comment;
import com.axp.axpseller.models.bean.GetCommentListBean;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.CommentListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/8/16.
 * 评论列表
 */
public class GoodsCommentListFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_comment_list)
    SuperRecyclerView lvCommentList;
    CommentListAdapter mAdapter;
    DataList<Comment> comment = new DataList<>();
    GetCommentListBean commentRequest = new GetCommentListBean();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_comment_list, container, false);

        ButterKnife.bind(this, mView);

        init();
        return mView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        Bundle bundle = getArguments();
        commentRequest.setGoodsId(bundle.getString("goodsId")) ;
        mAdapter = new CommentListAdapter(getActivity(),comment);
        reLoadData();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvCommentList.setLayoutManager(manager);

        lvCommentList.setAdapter(mAdapter);

        lvCommentList.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getCommentList(), 2);

        lvCommentList.setRefreshListener(() -> reLoadData());

        lvCommentList.setDifferentSituationOptionListener(v -> reLoadData());
    }

    private void reLoadData(){
        commentRequest.setPageIndex(0);
        getCommentList();
    }

    private void getCommentList(){
        Request request = new Request();
        commentRequest.setPageIndex(commentRequest.getPageIndex()+1);

        request.setData(commentRequest);
        RXUtils.request(getActivity(), request, "getCommentList", new RecyclerViewSubscriber<Response<DataList<Comment>>>(mAdapter, comment) {
            @Override
            public void onSuccess(Response<DataList<Comment>> dataListResponse) {
                mAdapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });

    }
}
