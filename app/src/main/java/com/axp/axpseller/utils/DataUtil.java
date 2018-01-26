package com.axp.axpseller.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.axp.axpseller.activitys.sellerinfo.AddStockActivity;
import com.axp.axpseller.activitys.sellerinfo.PostGoodActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.models.bean.PostGoodResModel;
import com.axp.axpseller.models.bean.SpecificationModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.view.IBaseListenner;
import com.axp.axpseller.views.dialogs.LoadingDialog;

import java.util.ArrayList;

/**
 * Created by YY on 2017/12/12.
 */
public class DataUtil {

    //删除商品
    public static void deleteGood(Context context, String statusId,String typeId, String baseGoodId, String goodsOrder, IBaseListenner updateListenner) {
        Request<PostGoodResModel> request = new Request<>();
        PostGoodResModel model = new PostGoodResModel();
        model.setBaseGoodsId(baseGoodId);
        model.setGoodsOrder(goodsOrder);
        model.setStatusId(statusId);
        model.setTypeId(typeId);
        request.setData(model);
        RXUtils.request(context, request, "delMallOfGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(context, postGoodResModelResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                    if (updateListenner != null) {
                        updateListenner.updateData();
                    }
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    //商品编辑
    public static void toEditActivity(Context context,String baseGoodsId,String goodsOrder){
        Bundle bundle = new Bundle();
        bundle.putString("baseGoodsId", baseGoodsId);
        bundle.putString("goodsOrder", goodsOrder);
        AppUtils.toActivity(context, PostGoodActivity.class, bundle);
    }

    //商品上架
    public static void putOnGood(Context context,String statusId,String baseGoodsId, String goodsOrder, IBaseListenner updateListenner) {
        Request<PostGoodResModel> request = new Request<>();
        PostGoodResModel model = new PostGoodResModel();
        model.setStatusId(statusId);
        model.setBaseGoodsId(baseGoodsId);
        model.setGoodsOrder(goodsOrder);
        request.setData(model);
        RXUtils.request(context, request, "soldUpGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(context, "" + postGoodResModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if (updateListenner != null) {
                        updateListenner.updateData();
                    }
                }
            }
        });
    }

    //商品下架
    public static void soldOutGood(Context context,String statusId,String baseGoodId, String goodsOrder,IBaseListenner updateDataListenner) {
        Request<PostGoodResModel> request = new Request<>();
        PostGoodResModel model = new PostGoodResModel();
        model.setBaseGoodsId(baseGoodId);
        model.setGoodsOrder(goodsOrder);
        model.setStatusId(statusId);
        request.setData(model);
        RXUtils.request(context,request,"soldOutGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(context, postGoodResModelResponse.getMessage()+"", Toast.LENGTH_SHORT).show();
                    if (updateDataListenner != null) {
                        updateDataListenner.updateData();
                    }
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(context, ""+response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    //添加库存
    public static void toAddStock(Context context,boolean hasSpecStr, ArrayList<SpecificationModel> specificationModels,String baseGoodsId,String goodsOrder){
        Intent intent = new Intent(context, AddStockActivity.class);
        intent.putExtra("spec",specificationModels);
        intent.putExtra("baseGoodsId",baseGoodsId);
        intent.putExtra("goodsOrder",goodsOrder);
        intent.putExtra("hasSpecStr",hasSpecStr);
        context.startActivity(intent);
    }

    //提交库存(有规格的时候提交一个规格数组)
    public static void postStock(Context context,boolean hasSpecStr,String statusId,String baseGoodsId,String goodsOrder,ArrayList<SpecificationModel> specificationModels) {
        Request<GoodsManageModel> request = new Request<>();
        GoodsManageModel goodsManageModel = new GoodsManageModel();
        goodsManageModel.setStatusId(Integer.parseInt(statusId));
        goodsManageModel.setBaseGoodsId(baseGoodsId);
        goodsManageModel.setGoodsOrder(goodsOrder);
        goodsManageModel.setHasSpecStr(hasSpecStr);
        goodsManageModel.setSpecifications(specificationModels);
        request.setData(goodsManageModel);
        RXUtils.request(context, request, "reStock", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(context, "" + postGoodResModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    //提交库存(无规格的时候提交库存数)
    public static void postStock(Context context,boolean hasSpecStr,String statusId,String baseGoodsId,String goodsOrder,String stockNum,IBaseListenner updateDataListenner) {
        Request<GoodsManageModel> request = new Request<>();
        GoodsManageModel goodsManageModel = new GoodsManageModel();
        goodsManageModel.setStatusId(Integer.parseInt(statusId));
        goodsManageModel.setBaseGoodsId(baseGoodsId);
        goodsManageModel.setGoodsOrder(goodsOrder);
        goodsManageModel.setReStockNum(stockNum);
        goodsManageModel.setHasSpecStr(hasSpecStr);
        request.setData(goodsManageModel);
        RXUtils.request(context, request, "reStock", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    if (updateDataListenner != null) {
                        updateDataListenner.updateData();
                    }
                    DialogUtil.showNoticDialog(context,postGoodResModelResponse.getMessage());
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

}
