package com.axp.axpseller.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.UploadSubscriber;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by YY on 2017/12/8.
 */
public class GoodDtImgView extends FrameLayout {
    @BindView(R.id.good_dt_img_iv)
    ImageView goodDtImgIv;
    @BindView(R.id.add_img_display_iv)
    ImageView addImgDisplayIv;
    private Context mContext;
    private String imgUrl;
    private List<PhotoInfo> picInfoList = new ArrayList<>();
    private int mIndex;
    private IClick onBtnClickListenner;

    public void setOnBtnClickListenner(IClick onBtnClickListenner) {
        this.onBtnClickListenner = onBtnClickListenner;
    }

    public GoodDtImgView(Context context) {
        this(context, null);
    }

    public GoodDtImgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodDtImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        addView();
    }

    private void addView() {
        LayoutInflater.from(mContext).inflate(R.layout.good_dt_img_item, this, true);
        ButterKnife.bind(this);
    }

    public String getImgMsg() {
        return imgUrl;
    }

    public String getImgWidth() {
        return goodDtImgIv.getWidth() + "";
    }

    public String getImgHeight() {
        return goodDtImgIv.getHeight() + "";
    }

    @OnClick({R.id.img_del_btn, R.id.img_move_up_btn, R.id.img_move_down_btn, R.id.img_insert_btn, R.id.open_pic_rll})
    public void onClick(View view) {
        switch (view.getId()) {
            //删除
            case R.id.img_del_btn:
                if (onBtnClickListenner != null) {
                    onBtnClickListenner.deleteImg(this);
                }
                break;
            //上移
            case R.id.img_move_up_btn:

                break;
            //下移
            case R.id.img_move_down_btn:

                break;
            //插入
            case R.id.img_insert_btn:
                if (onBtnClickListenner != null) {
                    onBtnClickListenner.addImg(mIndex);
                }
                break;
            //调用相册
            case R.id.open_pic_rll:
                openPic();
                break;
        }
    }

    public void setIndex(int index){
        mIndex = index;
    }

    public void openPic() {
        FunctionConfig config = new FunctionConfig.Builder().setEnableCamera(true).setMutiSelectMaxSize(1).setEnableCrop(true).setEnableEdit(true).build();
        GalleryFinal.openGalleryMuti(0, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null && resultList.size() > 0) {
                    picInfoList.clear();
                    picInfoList.addAll(resultList);
                    updateImg();
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    private void updateImg() {
        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (picInfoList != null && picInfoList.size() > 0) {
            for (int i = 0; i < picInfoList.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(picInfoList.get(i).getPhotoPath()));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);
                uploadFileBeen.add(bean);
            }
        }
        RXUtils.uploadImages(mContext, uploadFileBeen, new UploadSubscriber() {
            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(mContext);
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                imgUrl = responses.get(0).getData().getOppositeUrl();
                addImgDisplayIv.setVisibility(GONE);
                Glide.with(mContext).load(responses.get(0).getData().getAbsoluteUrl()).into(goodDtImgIv);
//                Bitmap bitmap = BitmapUtil.scaleImg(picInfoList.get(0).getPhotoPath(), ScreenSizeUtil.getScreenSize(mContext)[0], DensityUtils.dp2px(mContext, 250));
//                goodDtImgIv.setImageBitmap(bitmap);
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                L.e("==上传图片返回的错误码:" + response.getMessage());
                Log.d("雨落无痕丶", "上传图片返回的错误码: " + response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "上传图片出现的异常: " + e.toString());
            }
        });
    }

    public interface IClick{
        void deleteImg(GoodDtImgView goodDtImgView);

        void addImg(int index);
    }

}
