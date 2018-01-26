package com.axp.axpseller.fragments.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonRectangle;
import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.core.UploadSubscriber;
import com.axp.axpseller.models.FeedBack;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.dialogs.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Dong on 2016/7/1.
 * 意见反馈
 */
public class FeedBackFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edt_feedback)
    EditText edtFeedback;
    @BindView(R.id.gv_photo)
    GridView gvPhoto;
    @BindView(R.id.iv_add_photo)
    ImageView ivAddPhoto;
    @BindView(R.id.btn_commint)
    ButtonRectangle btnCommint;

    private String content;

    List<String> datas;
    MultiImageSelector selector = MultiImageSelector.create(getActivity());
    private ArrayList<String> mSelectPath;
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, mView);
        init();
        selector.multi();
        selector.count(4);
        return mView;
    }

    private void init() {
        adapter = new MyAdapter();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void putFeedBack() {

        FeedBack feedBack = new FeedBack();
        List<String> path = new ArrayList<>();
        feedBack.setImages(path);
        feedBack.setContent(content);
        Request request = new Request<>();
        request.setData(feedBack);

        if (mSelectPath != null && mSelectPath.size() > 0) {
            List<UploadFileBean> uploadFileBeen = new ArrayList<>();
            for (int i = 0; i < mSelectPath.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);

                uploadFileBeen.add(bean);
            }

            RXUtils.uploadImages(getActivity(), uploadFileBeen, new UploadSubscriber() {

                LoadingDialog loadingDialog;

                @Override
                public void onStart() {
                    loadingDialog = new LoadingDialog(getActivity());
                    loadingDialog.show();
                }

                @Override
                public void onCompleted() {
                    loadingDialog.dismiss();
                }

                @Override
                public void onNext(List<Response<UploadFileBean>> responses) {
                    for (Response<UploadFileBean> uploadFileBeanResponse : responses) {
                        path.add(uploadFileBeanResponse.getData().getOppositeUrl());
                    }
                    reqFeedBack(request);
                }
            });
        } else {
            reqFeedBack(request);
        }


    }

    private void reqFeedBack(Request request) {
        RXUtils.request(getActivity(), request, "feedback", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(), response.getMessage());
                getActivity().finish();

            }
        });
    }

    @OnClick({R.id.iv_add_photo, R.id.btn_commint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_photo:
                selector.start(getActivity(), 2);
                break;
            case R.id.btn_commint:

                content = edtFeedback.getText().toString();

                if (StringUtils.isEmpty(content)) {
                    T.showShort(getActivity(), "您还没有填写内容哦~");
                    return;
                }

                putFeedBack();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);

                gvPhoto.setAdapter(adapter);
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSelectPath.size();
        }

        @Override
        public Object getItem(int position) {
            return mSelectPath.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_comment_image, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_photo);
            Glide.with(getActivity()).load(mSelectPath.get(position)).into(iv);
            return convertView;
        }
    }

}