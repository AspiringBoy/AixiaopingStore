package com.axp.axpseller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.OrderMsgActivity;
import com.axp.axpseller.activitys.SystemMsgActivity;
import com.axp.axpseller.adapter.MsgHomeLvAdapter;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.MsgHomeModel;
import com.axp.axpseller.models.MsgHomeObModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/5/8.
 */
public class MsgHomeFragment extends Fragment {
    private View view;
    private PullToRefreshListView lv;
    private List<MsgHomeObModel> list = new ArrayList<>();
    private MsgHomeLvAdapter mAdapter;
    private LoadingDialog loadingDialog;
    private Toolbar toolbar;
    private LinearLayout empty_layout;
    private Button reload_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg_home, container, false);
        initView();
        loadingDialog = new LoadingDialog(getActivity());
        initData();
        mAdapter = new MsgHomeLvAdapter(getActivity(), list);
        lv.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLvList();
    }

    private void initData() {
        getLvList();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int typeId = list.get(i - 1).getTypeId();
                Bundle bundle = new Bundle();
                bundle.putInt("typeId", typeId);
                if (typeId == 1 || typeId == 3) {
                    AppUtils.toActivity(getActivity(), SystemMsgActivity.class, bundle);
                } else if (typeId == 2) {
                    AppUtils.toActivity(getActivity(), OrderMsgActivity.class, bundle);
                }
                /*if (Integer.valueOf(list.get(i - 1).getUnread()) > 0) {
                    lv.getRefreshableView().getChildAt(i).findViewById(R.id.system_msg_unread_count).setVisibility(View.GONE);
                }*/
            }
        });
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getLvList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLvList();
            }
        });
    }

    private void getLvList() {
        loadingDialog.show();
        Request<MsgHomeModel> request = new Request();
        MsgHomeModel msgHomeModel = new MsgHomeModel();
        msgHomeModel.setUserId(ContextParameter.getUserInfo().getUserId());
        request.setData(msgHomeModel);
        RXUtils.request(getActivity(), request, "getMsgTypes", new SupportSubscriber<Response<MsgHomeModel>>() {
            @Override
            public void onNext(Response<MsgHomeModel> msgHomeModelResponse) {
                list.clear();
                list.addAll(msgHomeModelResponse.getData().getMsgTypes());
                loadingDialog.dismiss();
                if (list.size() == 0 || list == null) {
                    lv.setVisibility(View.GONE);
                    empty_layout.setVisibility(View.VISIBLE);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    empty_layout.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
                lv.onRefreshComplete();
            }
        });
    }

    private void initView() {
        lv = ((PullToRefreshListView) view.findViewById(R.id.all_msg_type_lv));
        toolbar = ((Toolbar) view.findViewById(R.id.msg_home_toolbar));
        empty_layout = ((LinearLayout) view.findViewById(R.id.msg_home_empty_layout));
        reload_btn = ((Button) view.findViewById(R.id.btn_empty_reload));
    }
}
