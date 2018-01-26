package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.fragments.sellerinfo.BankListFragment;
import com.axp.axpseller.models.Apply;
import com.axp.axpseller.models.Bank;
import com.axp.axpseller.network.DataList;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/4.
 */
public class MyBankAdapter extends SuperRecyclerViewAdapter<Bank,MyBankAdapter.MyApplysListViewHoler> {
    BankListFragment fragment;
    public MyBankAdapter(Context context, DataList<Bank> dataList, BankListFragment fragment) {
        super(context, dataList);
        this.fragment = fragment;
    }

    @Override
    public MyApplysListViewHoler onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        MyApplysListViewHoler viewHoler = new MyApplysListViewHoler(LayoutInflater.from(mContext).inflate(R.layout.item_my_bank,parent,false));
        return viewHoler;
    }

    @Override
    public void onSuperBindViewHolder(MyApplysListViewHoler holder, int position) {
        Bank bank = mList.get(position);
        holder.setData(bank);
        /*holder.name.setText(bank.getBankName()+"("+bank.getCardNo()+")");
        holder.free.setText("提现到"+bank.getBankName()+"，手续费"+bank.getCounterFee()+"%");
        holder.select.setChecked(Boolean.parseBoolean(bank.getIsDefault()));
        holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fragment.setDefultBank(bank.getBankId());
            }
        });
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragment.show(bank.getBankId());
            }
        });*/
    }
    class MyApplysListViewHoler extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_free)
        TextView free;
        @BindView(R.id.cb_select)
        CheckBox select;
        @BindView(R.id.iv_delet)
        ImageView delet;

        public MyApplysListViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Bank bank){
            name.setText(bank.getBankName()+"("+bank.getCardNo()+")");
            free.setText("提现到"+bank.getBankName()+"，手续费"+bank.getCounterFee()+"%");
            select.setChecked(Boolean.parseBoolean(bank.getIsDefault()));
            select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    fragment.setDefultBank(bank.getBankId());
                }
            });
            delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.show(bank.getBankId());
                }
            });
        }
    }
}
