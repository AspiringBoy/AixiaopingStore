package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.models.bean.SpecificationModel;
import com.axp.axpseller.utils.DataUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStockActivity extends AppCompatActivity {

    @BindView(R.id.spec_lv)
    ListView specLv;
    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    private ArrayList<SpecificationModel> mSpecList;
    private SpecAdapter specAdapter;
    private String baseGoodsId, goodsOrder;
    private boolean hasSpecStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("添加库存");
        mSpecList = ((ArrayList<SpecificationModel>) getIntent().getSerializableExtra("spec"));
        baseGoodsId = getIntent().getStringExtra("baseGoodsId");
        goodsOrder = getIntent().getStringExtra("goodsOrder");
        hasSpecStr = getIntent().getBooleanExtra("hasSpecStr", false);
        specAdapter = new SpecAdapter();
        specLv.setAdapter(specAdapter);
    }

    @OnClick({R.id.save_btn,R.id.back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_btn:
                DataUtil.postStock(this, hasSpecStr, Constants.GOOD_MANAGE_SELLING_STATUS, baseGoodsId, goodsOrder, mSpecList);
                break;
        }
    }

    class SpecAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSpecList.size();
        }

        @Override
        public Object getItem(int i) {
            return mSpecList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyViewHolder holder;
            if (view == null) {
                holder = new MyViewHolder();
                view = LayoutInflater.from(AddStockActivity.this).inflate(R.layout.spec_lv_item, viewGroup, false);
                holder.specStr = (TextView) view.findViewById(R.id.spec_str);
                holder.specNum = (EditText) view.findViewById(R.id.stock_num_edt);
                view.setTag(holder);
            } else holder = (MyViewHolder) view.getTag();

            holder.specStr.setText(mSpecList.get(i).getSpecStr() + ":");
            holder.specNum.setText(mSpecList.get(i).getStock());
            holder.specNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mSpecList.get(i).setStock(editable.toString());
                }
            });
            return view;
        }

        class MyViewHolder {
            TextView specStr;
            EditText specNum;
        }
    }
}
