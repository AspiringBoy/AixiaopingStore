package com.axp.axpseller.activitys.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanToSendActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.score_left_tv)
    TextView scoreLeftTv;
    @BindView(R.id.send_num_edt)
    EditText sendNumEdt;
    @BindView(R.id.notice_tv)
    TextView noticeTv;
    @BindView(R.id.send_btn)
    Button send_btn;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_to_send);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            score = bundle.getString("score");
            scoreLeftTv.setText(score);
        }
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        sendNumEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                double leftScore = Double.parseDouble(scoreLeftTv.getText().toString());
                double sendNum = Double.parseDouble(editable.toString());
                if (sendNum > leftScore) {
                    noticeTv.setVisibility(View.VISIBLE);
                    send_btn.setBackgroundResource(R.drawable.btn_rec_gray_bg);
                    send_btn.setEnabled(false);
                }else {
                    noticeTv.setVisibility(View.GONE);
                    send_btn.setBackgroundResource(R.drawable.btn_main_color_low);
                    send_btn.setEnabled(true);
                }
            }
        });
    }

    @OnClick(R.id.send_btn)
    public void onClick() {
        String value = sendNumEdt.getText().toString();
        if (!StringUtils.isBlank(value)) {
            Bundle bundle = new Bundle();
            bundle.putString("score", value);
            AppUtils.toActivity(this,SendScoreQrActivity.class,bundle);
        }else {
            DialogUtil.showNoticDialog(this,"请填写赠送数量!");
        }
    }
}
