package com.axp.axpseller.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.axp.axpseller.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2017/12/8.
 */
public class GoodDtTextView extends FrameLayout {
    @BindView(R.id.good_dt_text_edt)
    EditText goodDtTextEdt;
    private Context mContext;
    private int mIndex;
    private IClick onBtnClickListenner;

    public void setOnBtnClickListenner(IClick onBtnClickListenner) {
        this.onBtnClickListenner = onBtnClickListenner;
    }

    public GoodDtTextView(Context context) {
        this(context, null);
    }

    public GoodDtTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodDtTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        addView();
    }

    private void addView() {
        LayoutInflater.from(mContext).inflate(R.layout.good_dt_text_item, this, true);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.text_del_btn, R.id.text_move_down_btn, R.id.text_insert_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //删除
            case R.id.text_del_btn:
                if (onBtnClickListenner != null) {
                    onBtnClickListenner.deletTxt(this);
                }
                break;
            //下移
            case R.id.text_move_down_btn:

                break;
            //插入
            case R.id.text_insert_btn:
                if (onBtnClickListenner != null) {
                    onBtnClickListenner.addTxt(mIndex);
                }
                break;
        }
    }

    public String getTextMsg(){
        return goodDtTextEdt.getText().toString();
    }

    public void setIndex(int index){
        mIndex = index;
    }

    public interface IClick{
        void deletTxt(GoodDtTextView goodDtTextView);

        void addTxt(int index);
    }
}
