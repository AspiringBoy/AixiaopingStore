package com.axp.axpseller.fragments.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.core.UploadSubscriber;
import com.axp.axpseller.models.Comment;
import com.axp.axpseller.models.Order;
import com.axp.axpseller.models.OrderItem;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.models.eventbus_message.UpdateOrderListMessage;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.utils.Utils;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.axp.axpseller.views.order.EvaluateOrderView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/6/18.
 * 评论
 */
public class EvaluateFragment extends BaseFragment {

    /**
     * 订单所属的订单状态,用于刷新列表
     */
    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_LIST_ORDER = "KEY_LIST_ORDER";

    String status;

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.layout_container)
    LinearLayout layoutContainer;

    List<EvaluateOrderView> evaluateOrderViews = new ArrayList<>();

    //使用Order主要是为了拿Seller方便而已
    List<Order> orders = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_evaluate, container, false);
        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        loadBundle();

        for (Order order : orders) {

            EvaluateOrderView view = new EvaluateOrderView(getActivity());
            view.bindView(order);
            layoutContainer.addView(view);
            evaluateOrderViews.add(view);

        }

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            status = bundle.getString(KEY_STATUS);
            orders = (List<Order>) bundle.getSerializable(KEY_LIST_ORDER);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (int i = 0; i < evaluateOrderViews.size(); i++) {
            evaluateOrderViews.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.btn_post)
    public void onClick() {

        if(Utils.isFastClick()){
            return;
        }

        List<Comment> comments = new ArrayList<>();

        for (int i = 0; i < evaluateOrderViews.size(); i++) {
            if (StringUtils.isEmpty(evaluateOrderViews.get(i).getContent())) {
                T.showShort(getActivity(), "请完善评价信息");
                return;
            }

            Comment comment = new Comment();
            comment.setCommentContent(evaluateOrderViews.get(i).getContent());
            comment.setCommentGoal(evaluateOrderViews.get(i).getScore());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(orders.get(i).getOrderItems().get(0).getOrderItemId());
            comment.setOrderItem(orderItem);
            comment.setCommentImages(evaluateOrderViews.get(i).getSelectPath());

            comments.add(comment);
        }


        List<UploadFileBean> uploadFileBeans = new ArrayList<>();
        //图片上传
        for (Comment comment : comments) {
            for (String img : comment.getCommentImages()) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(img));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_COMMENT_IMAGE);

                uploadFileBeans.add(bean);
            }
        }

        LoadingDialog dialog = new LoadingDialog(getActivity());

        RXUtils.uploadImages(getActivity(), uploadFileBeans, new UploadSubscriber() {

            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onStop() {
                super.onStop();
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                T.showShort(getActivity(), "未知错误");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), "上传失败");
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                for (Comment comment : comments) {
                    for (int i = 0; i < comment.getCommentImages().size(); i++) {
                        for (Response<UploadFileBean> resonse : responses) {
                            if (comment.getCommentImages().get(i).equals(resonse.getData().getFile().getPath())) {
                                comment.getCommentImages().set(i, resonse.getData().getOppositeUrl());
                                break;
                            }
                        }
                    }
                }

                Map<String, List<Comment>> value = new HashMap<>();
                value.put("dataList", comments);

                Request request = new Request();
                request.setData(value);

                RXUtils.request(getActivity(), request, "putComment", new SupportSubscriber<Response>() {

                    @Override
                    public void onStop() {
                        super.onStop();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Response response) {

                        if (!StringUtils.isEmpty(status)) {
                            //发送列表数据更新消息
                            UpdateOrderListMessage message = new UpdateOrderListMessage();
                            message.setStatus(status);
                            EventBus.getDefault().post(message);
                        }

                        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
                        Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                            @Override
                            public void onPositiveActionClicked(DialogFragment fragment) {
                                super.onPositiveActionClicked(fragment);
                                getDialog().dismiss();

                            }

                            @Override
                            public void onNegativeActionClicked(DialogFragment fragment) {
                                super.onNegativeActionClicked(fragment);
                            }

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                super.onDismiss(dialog);
                                getActivity().finish();
                            }
                        };

                        ((SimpleDialog.Builder) builder).message("您的评价已经生效啦，谢谢您对我们的评价~")
                                .title("评价晒单")
                                .positiveAction("好哒");
                        DialogFragment fragment = DialogFragment.newInstance(builder);
                        fragment.show(getFragmentManager(), null);

                    }
                });
            }
        });

    }
}

