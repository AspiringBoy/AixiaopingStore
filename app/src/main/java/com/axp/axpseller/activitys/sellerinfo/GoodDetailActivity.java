package com.axp.axpseller.activitys.sellerinfo;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.models.bean.GoodDetailModel;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.axp.axpseller.views.custom.GoodDtImgView;
import com.axp.axpseller.views.custom.GoodDtTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodDetailActivity extends AppCompatActivity implements GoodDtTextView.IClick, GoodDtImgView.IClick {

    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.add_iv)
    ImageView add_iv;
    @BindView(R.id.good_dt_containner)
    LinearLayout goodDtContainner;
    private Intent intent;
    private View pwView;
    private PopupWindow pw;
    private ImageView add_text_iv, add_img_iv;
    private Button cal_btn;
    private int viewIndex = 0;
    private ArrayList<GoodDetailModel> mList = new ArrayList<>();
    private int insertType;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("商品详情");
        intent = getIntent();
    }

    @OnClick({R.id.back_iv, R.id.add_iv,R.id.back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back_iv:
            case R.id.back_btn:
                backSetting();
                finish();
                break;
            //添加文字描述或图片
            case R.id.add_iv:
                insertType = 1;
                showPw();
                break;
        }
    }

    private void backSetting() {
        int childCount = goodDtContainner.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View childAt = goodDtContainner.getChildAt(i);
                GoodDetailModel goodDetailModel = new GoodDetailModel();
                if (childAt instanceof GoodDtTextView) {
                    GoodDtTextView textView = (GoodDtTextView) childAt;
                    goodDetailModel.setDetailType("text");
                    goodDetailModel.setText(textView.getTextMsg());
                } else {
                    GoodDtImgView imgView = (GoodDtImgView) childAt;
                    goodDetailModel.setPicture(imgView.getImgMsg());
                    goodDetailModel.setDetailType("picture");
                    goodDetailModel.setPictureHeight(imgView.getImgHeight());
                    goodDetailModel.setPictureWidth(imgView.getImgWidth());
                }
                mList.add(goodDetailModel);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("goodDtList", mList);
        intent.putExtras(bundle);
        setResult(8, intent);
    }

    private void showPw() {
        ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
        if (pw == null) {
            pwView = LayoutInflater.from(this).inflate(R.layout.add_imgtext_pw_item, null);
            add_text_iv = (ImageView) pwView.findViewById(R.id.add_text_iv);
            add_img_iv = (ImageView) pwView.findViewById(R.id.add_img_iv);
            cal_btn = (Button) pwView.findViewById(R.id.cal_btn);
            add_text_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (insertType == 1) {
                        GoodDtTextView goodDtTextView = new GoodDtTextView(GoodDetailActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
                        goodDtTextView.setIndex(viewIndex);
                        goodDtTextView.setOnBtnClickListenner(GoodDetailActivity.this);
                        goodDtContainner.addView(goodDtTextView, params);
                        viewIndex++;
                    } else if (insertType == 2) {
                        GoodDtTextView goodDtTextView = new GoodDtTextView(GoodDetailActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
                        goodDtTextView.setIndex(mIndex + 1);
                        goodDtTextView.setOnBtnClickListenner(GoodDetailActivity.this);
                        goodDtContainner.addView(goodDtTextView, mIndex + 1, params);
                        viewIndex++;
                    }
                    add_iv.setVisibility(View.GONE);
                    pw.dismiss();
                }
            });
            add_img_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (insertType == 1) {
                        GoodDtImgView goodDtImgView = new GoodDtImgView(GoodDetailActivity.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
                        goodDtImgView.setIndex(viewIndex);
                        goodDtImgView.setOnBtnClickListenner(GoodDetailActivity.this);
                        goodDtContainner.addView(goodDtImgView, layoutParams);
                    } else if (insertType == 2) {
                        GoodDtImgView goodDtImgView = new GoodDtImgView(GoodDetailActivity.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
                        goodDtImgView.setIndex(mIndex + 1);
                        goodDtImgView.setOnBtnClickListenner(GoodDetailActivity.this);
                        goodDtContainner.addView(goodDtImgView, mIndex + 1, layoutParams);
                    }
                    add_iv.setVisibility(View.GONE);
                    pw.dismiss();
                }
            });
            cal_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });
            pw = new PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pw.setBackgroundDrawable(new ColorDrawable());
            pw.setFocusable(true);
            pw.setOutsideTouchable(false);
            pw.setAnimationStyle(R.style.PwAnimBottom);
            pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(GoodDetailActivity.this, 1.0f);
                }
            });
        }
        pw.showAtLocation(goodDtContainner, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onBackPressed() {
        backSetting();
        super.onBackPressed();
    }

    //删除文本
    @Override
    public void deletTxt(GoodDtTextView goodDtTextView) {
        goodDtContainner.removeView(goodDtTextView);
    }

    //插入文本
    @Override
    public void addTxt(int index) {
        insertType = 2;
        mIndex = index;
        showPw();
    }

    //删除图片
    @Override
    public void deleteImg(GoodDtImgView goodDtImgView) {
        goodDtContainner.removeView(goodDtImgView);
    }

    //插入图片
    @Override
    public void addImg(int index) {
        insertType = 2;
        mIndex = index;
        showPw();
    }
}
