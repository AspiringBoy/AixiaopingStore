package com.axp.axpseller.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2017/12/7.
 */
public class PostGoodTypeView extends FrameLayout {
    @BindView(R.id.now_price)
    EditText nowPrice;
    @BindView(R.id.stock_edt)
    EditText stockEdt;
    @BindView(R.id.type_num_tv)
    TextView typeNumTv;
    @BindView(R.id.type_edt)
    EditText typeEdt;
    @BindView(R.id.good_type_ll)
    LinearLayout goodTypeLl;
    private Context mContext;
    private int pos = 0;

    public void setOnCutListenner(IDelete onCutListenner) {
        this.onCutListenner = onCutListenner;
    }

    private IDelete onCutListenner;

    public PostGoodTypeView(Context context) {
        this(context, null);
    }

    public PostGoodTypeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PostGoodTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.post_good_add_type_ll, this, true);
        ButterKnife.bind(this);
    }

    public String getNowPrice() {
        if (nowPrice.getText().toString() == null || nowPrice.getText().toString().length() == 0) {
            return null;
        }
        return nowPrice.getText().toString();
    }

    public String getStock() {
        if (stockEdt.getText().toString() == null || stockEdt.getText().toString().length() == 0) {
            return null;
        }
        return stockEdt.getText().toString();
    }

    public String getGoodType() {
        if (typeEdt.getText().toString() == null || typeEdt.getText().toString().length() == 0) {
            return null;
        }
        return typeEdt.getText().toString();
    }

    public void setTypeNum(int num) {
        pos = num -2;
        typeNumTv.setText("型号" + num + "");
    }

    public void setNowPrice(String val) {
        nowPrice.setText(val);
    }

    public void setStock(String val) {
        stockEdt.setText(val);
    }

    public void setType(String val) {
        typeEdt.setText(val);
    }

    @OnClick(R.id.cut_iv)
    public void onClick() {
        if (onCutListenner != null) {
            onCutListenner.onCut(this);
        }
    }

    public interface IDelete{
        void onCut(PostGoodTypeView view);
    }
}
