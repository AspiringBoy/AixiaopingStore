package com.axp.axpseller.core;

import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.network.Response;

import java.util.List;

/**
 * Created by xu on 2016/7/8.
 * 图片上传Subscriber
 */
public abstract class UploadSubscriber extends SupportSubscriber<List<Response<UploadFileBean>>> {

}
