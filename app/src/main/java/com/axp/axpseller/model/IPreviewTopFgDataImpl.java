package com.axp.axpseller.model;

import android.content.Context;

import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Comment;
import com.axp.axpseller.models.Goods;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.view.IPreviewFgDataListenner;
import com.axp.axpseller.views.adapters.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2018/1/2.
 */
public class IPreviewTopFgDataImpl implements IPreviewTopFgData {

    @Override
    public void getGoodData(Context context,String goodId, IPreviewFgDataListenner listenner) {
        Goods goods = new Goods();
        goods.setGoodsId(goodId);
        Request<Goods> request = new Request<>();
        request.setData(goods);
        RXUtils.request(context, request, "getGoods", new SupportSubscriber<Response<Goods>>() {
            @Override
            public void onNext(Response<Goods> goodsResponse) {
                DataList<RecyclerViewModel> dataList = new DataList<RecyclerViewModel>();
                dataList.getDataList().addAll(handleGood(goodsResponse.getData()));
                dataList.setPageSize(1);
                listenner.onSuccess(dataList);
            }
        });
    }

    /**
     * 解析处理成列表能显示的数据
     *
     * @return
     */
    private List<RecyclerViewModel> handleGood(Goods goods) {
        List<RecyclerViewModel> list = new ArrayList<>();
        RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
        bannerRecyclerViewModel.setItemType(GoodsAdapter.TYPE_BANNER);
        bannerRecyclerViewModel.setData(goods);
        list.add(bannerRecyclerViewModel);

        RecyclerViewModel infoRecyclerViewModel = new RecyclerViewModel();
        infoRecyclerViewModel.setItemType(GoodsAdapter.TYPE_INFO);
        infoRecyclerViewModel.setData(goods);
        list.add(infoRecyclerViewModel);

        if (goods.getCommentList() != null && goods.getCommentList().getDataList() != null && goods.getCommentList().getDataList().size() > 0) {
            RecyclerViewModel commentTitleRecyclerViewModel = new RecyclerViewModel();
            commentTitleRecyclerViewModel.setItemType(GoodsAdapter.TYPE_COMMENT_TITLE);
            commentTitleRecyclerViewModel.setData(goods);
            list.add(commentTitleRecyclerViewModel);

            for (Comment comment : goods.getCommentList().getDataList()) {
                RecyclerViewModel commentItemRecyclerViewModel = new RecyclerViewModel();
                commentItemRecyclerViewModel.setItemType(GoodsAdapter.TYPE_COMMENT_ITEM);
                commentItemRecyclerViewModel.setData(comment);
                list.add(commentItemRecyclerViewModel);
            }

        }
        RecyclerViewModel loadDetailsRecyclerViewModel = new RecyclerViewModel();
        loadDetailsRecyclerViewModel.setItemType(GoodsAdapter.TYPE_LOAD_DETAIL);
        list.add(loadDetailsRecyclerViewModel);
        return list;
    }
}
