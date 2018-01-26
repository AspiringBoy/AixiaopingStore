package yy.custombanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/12/7.
 */
public class BannerVp extends FrameLayout {
    private Context mContext;
    private View mView;
    private ViewPager mVp;
    private LinearLayout mIndicaterContainner;
    private VpAdapter mVpAdapter;
    private List<String> mImgList = new ArrayList<>();
    private int mPrePosition = 0;
    private IPageChangeListener iPageChangeListener;
    private int curPos;

    public void setiBannerClickListener(VpAdapter.IBannerClickListener iBannerClickListener) {
        if (mVpAdapter == null) return;
        mVpAdapter.setiBannerClickListener(iBannerClickListener);
    }

    public BannerVp(Context context) {
        this(context, null);
    }

    public BannerVp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerVp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public IPageChangeListener getiPageChangeListener() {
        return iPageChangeListener;
    }

    public void setiPageChangeListener(IPageChangeListener iPageChangeListener) {
        this.iPageChangeListener = iPageChangeListener;
    }

    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.banner_vp_layout, this, true);
        mVp = ((ViewPager) mView.findViewById(R.id.vp));
        mIndicaterContainner = ((LinearLayout) mView.findViewById(R.id.indicater_containner));
        mVpAdapter = new VpAdapter(mContext, mImgList);
        mVp.setAdapter(mVpAdapter);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndicaterContainner.getChildAt(mPrePosition).setEnabled(false);
                mIndicaterContainner.getChildAt(position).setEnabled(true);
                mPrePosition = position;
                if (iPageChangeListener != null) {
                    iPageChangeListener.pageSelect(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setBanner(List<String> list, int pos) {
        int oldSize = mImgList.size();
        mImgList.clear();
        mImgList.addAll(list);
        mVpAdapter.notifyDataSetChanged();
        addIndicaterView(list,oldSize);
        setCurPos(pos);
    }

    public void setCurPos(int pos) {
        curPos = pos;
        mVp.setCurrentItem(pos);
    }

    private void addIndicaterView(List<String> list,int oldSize) {
        int sub = list.size() - oldSize;
        if (sub >= 0) {
            for (int i = 0; i < sub; i++) {
                View view = new View(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                lp.leftMargin = 10;
                //设置view的宽高左边距等参数
                view.setLayoutParams(lp);
                //默认情况下所有设置View的所有属性为false
                view.setEnabled(false);
                //给view设置背景选择器，enable属性为true时背景为红色，否则为白色
                view.setBackgroundResource(R.drawable.dot_bg);
                //将View添加到容器中
                mIndicaterContainner.addView(view);
            }
        }
        mIndicaterContainner.getChildAt(mPrePosition).setEnabled(false);
        mIndicaterContainner.getChildAt(curPos).setEnabled(true);
        mPrePosition = curPos;
    }

    /*public void setIndicaterPos(int pos){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (pos == ConfigConstans.INDICATER_LEFT) {
            params.addRule(ALIGN_PARENT_RIGHT);
        }
    }*/

    public interface IPageChangeListener {
        void pageSelect(int pos);
    }
}
