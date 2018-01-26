package com.axp.axpseller.activitys.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.models.bean.QrCodeContentModel;
import com.axp.axpseller.utils.DensityUtils;
import com.axp.axpseller.utils.QRCodeUtil;
import com.axp.axpseller.views.custom.CustomToolbar;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendScoreQrActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.qr_iv)
    ImageView qrIv;
    private String score = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_score_qr);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            score = bundle.getString("score");
        }
        showQrCode();
    }

    private void showQrCode() {
        QrCodeContentModel model = new QrCodeContentModel();
        model.setPresenterId(ContextParameter.getUserInfo().getAdminuserId());
        model.setScore(score);
        model.setTime(System.currentTimeMillis()+"");
        String jsonModel = new Gson().toJson(model);
        Bitmap qrImage = QRCodeUtil.createQRImage("ScorePresent:" + jsonModel, DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200));
        qrIv.setImageBitmap(qrImage);
    }
}
