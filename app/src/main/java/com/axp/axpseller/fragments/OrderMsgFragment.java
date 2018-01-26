package com.axp.axpseller.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.OrderMsgDtActivity;
import com.axp.axpseller.adapter.OrderMsgLvAdapter;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.DeleteMsgModel;
import com.axp.axpseller.models.OrderMsgDtOb;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.presenter.OrderMsgFgPresenter;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.view.IShowOrderMsgFg;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public class OrderMsgFragment extends BaseFragment implements IShowOrderMsgFg {
    private OrderMsgFgPresenter presenter;
    private int typeId;
    private View mView;
    private PullToRefreshListView lv;
    private OrderMsgLvAdapter mAdapter;
    private Toolbar toolbar;
    private List<OrderMsgDtOb> mList = new ArrayList<>();
    private ImageView del_all;
    private LinearLayout empty_layout;
    private Button reload_btn;
    private LoadingDialog loadingDialog;
    private View pup_item;
    private TextView delete_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_order_msg, container, false);
        typeId = getActivity().getIntent().getExtras().getInt("typeId");
        presenter = new OrderMsgFgPresenter(this);
        presenter.showOrderMsgFgView(getActivity(), typeId);
        return mView;
    }

    @Override
    public void initView() {
        lv = ((PullToRefreshListView) mView.findViewById(R.id.order_msg_lv));
        toolbar = ((Toolbar) mView.findViewById(R.id.order_msg_toolbar));
        del_all = ((ImageView) mView.findViewById(R.id.order_msg_del_all));
        empty_layout = ((LinearLayout) mView.findViewById(R.id.msg_home_empty_layout));
        reload_btn = ((Button) mView.findViewById(R.id.btn_empty_reload));
        pup_item = LayoutInflater.from(getActivity()).inflate(R.layout.delete_pup_item, null);
        delete_tv = ((TextView) pup_item.findViewById(R.id.delete_tv));
    }

    @Override
    public void initData() {
        mAdapter = new OrderMsgLvAdapter(getActivity(), mList);
        lv.setAdapter(mAdapter);
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        del_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllData();
            }
        });
    }

    @Override
    public void showView(List<OrderMsgDtOb> list) {
        mList.clear();
        mList.addAll(list);
        if (list.size() == 0 || list == null) {
            lv.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
        } else {
            lv.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
        loadingDialog.dismiss();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("typeId", typeId);
                bundle.putInt("msgId", list.get(i - 1).getMsgId());
                AppUtils.toActivity(getActivity(), OrderMsgDtActivity.class, bundle);
            }
        });
        lv.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupWindow pw = new PopupWindow(pup_item, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                pw.setFocusable(true);
                pw.setOutsideTouchable(true);
                pw.setBackgroundDrawable(new BitmapDrawable());
                pw.showAsDropDown(view, view.getWidth() / 2, -view.getHeight());
                delete_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteOneData(i);
                        pw.dismiss();
                    }
                });
                return true;
            }
        });
        reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.upDateMsgList(getActivity(), typeId);
            }
        });
    }

    private void deleteOneData(int i) {
        Request<DeleteMsgModel> request = new Request();
        DeleteMsgModel deleteMsgModel = new DeleteMsgModel();
        deleteMsgModel.setUserId(ContextParameter.getUserInfo().getUserId());
        deleteMsgModel.setTypeId(typeId + "");
        deleteMsgModel.setMsgId(mList.get(i - 1).getMsgId() + "");
        request.setData(deleteMsgModel);
        RXUtils.request(getActivity(), request, "deleteMsg", new SupportSubscriber<Response<DeleteMsgModel>>() {
            @Override
            public void onNext(Response<DeleteMsgModel> deleteMsgModelResponse) {
                mList.remove(i - 1);
                mAdapter.notifyDataSetChanged();
                if (mList.size() == 0) {
                    lv.setVisibility(View.GONE);
                    empty_layout.setVisibility(View.VISIBLE);
                }
//                Toast.makeText(OrderMsgFragment.this.getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAllData() {
        Request<DeleteMsgModel> request = new Request();
        DeleteMsgModel deleteMsgModel = new DeleteMsgModel();
        deleteMsgModel.setUserId(ContextParameter.getUserInfo().getUserId());
        deleteMsgModel.setTypeId(typeId + "");
        deleteMsgModel.setMsgId("-1");
        request.setData(deleteMsgModel);
        RXUtils.request(getActivity(), request, "deleteMsg", new SupportSubscriber<Response<DeleteMsgModel>>() {
            @Override
            public void onNext(Response<DeleteMsgModel> deleteMsgModelResponse) {
                mList.clear();
                mAdapter.notifyDataSetChanged();
                lv.setVisibility(View.GONE);
                empty_layout.setVisibility(View.VISIBLE);
            }
        });
    }
}
