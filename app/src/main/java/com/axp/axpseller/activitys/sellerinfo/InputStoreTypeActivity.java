package com.axp.axpseller.activitys.sellerinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.fragments.sellerinfo.InputStoreTypeFragment;
import com.axp.axpseller.models.Shopcate;
import com.axp.axpseller.models.ShopcategoryInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.MyShopcategoryAdapter;
import com.axp.axpseller.views.dialogs.LoadingDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/2/28.
 */
public class InputStoreTypeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    List<Shopcate> data = new ArrayList<>();
    @BindView(R.id.listview)
    ListView listView;
    public static int mPosition;
    MyShopcategoryAdapter adapter;
    InputStoreTypeFragment myFragment;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    Intent intent;
    ShopcategoryInfo shopcategoryInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopcategory);

        ButterKnife.bind(this);

        getShopcategoryInfo();

        adapter = new MyShopcategoryAdapter(this, data);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        intent = getIntent();

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopcategoryInfo != null) {
                    setResult(6, intent);
                    InputStoreTypeActivity.this.finish();
                } else {
                    InputStoreTypeActivity.this.finish();
                }
            }
        });

    }

    private void getShopcategoryInfo() {

        if (getIntent().getStringExtra("type") != null && "PostGoodActivity".equals(getIntent().getStringExtra("type"))) {
            RXUtils.request(InputStoreTypeActivity.this, new Request(), "getPublishGoodsType", new SupportSubscriber<Response<List<Shopcate>>>() {

                LoadingDialog dialog;

                @Override
                public void onStart() {
                    dialog = new LoadingDialog(InputStoreTypeActivity.this);
                    dialog.show();
                }

                @Override
                public void onCompleted() {
                    dialog.dismiss();
                }

                @Override
                public void onNext(Response<List<Shopcate>> listResponse) {
                    data.addAll(listResponse.getData());
                    //创建MyFragment对象
                    myFragment = new InputStoreTypeFragment();

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, myFragment);
                    //通过bundle传值给MyFragment
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(InputStoreTypeFragment.TAG, (Serializable) data.get(mPosition));
                    myFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                    myFragment.setLinsener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb);
                            if (!checkBox.isChecked()) {
                                checkBox.setChecked(true);
                                view.setBackgroundColor(Color.parseColor("#f4f4f4"));
                                TextView textView = (TextView) view.findViewById(R.id.tv);
                                textView.setTextColor(InputStoreTypeActivity.this.getResources().getColor(R.color.main_color));
                                myFragment.adapter.changeStatus(i);
                                adapter.notifyDataSetChanged();
                                shopcategoryInfo = data.get(mPosition).getCategoryItems().get(i);
                                intent.putExtra("type", shopcategoryInfo);
                            } else {
                                checkBox.setChecked(false);
                                shopcategoryInfo = null;
                            }
                        }
                    });
                    adapter.notifyDataSetChanged();
                }

            });
        }else {
            RXUtils.request(InputStoreTypeActivity.this, new Request(), "getShopcategoryInfo", new SupportSubscriber<Response<List<Shopcate>>>() {

                LoadingDialog dialog;

                @Override
                public void onStart() {
                    dialog = new LoadingDialog(InputStoreTypeActivity.this);
                    dialog.show();
                }

                @Override
                public void onCompleted() {
                    dialog.dismiss();
                }

                @Override
                public void onNext(Response<List<Shopcate>> listResponse) {
                    data.addAll(listResponse.getData());
                    //创建MyFragment对象
                    myFragment = new InputStoreTypeFragment();

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, myFragment);
                    //通过bundle传值给MyFragment
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(InputStoreTypeFragment.TAG, (Serializable) data.get(mPosition));
                    myFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                    myFragment.setLinsener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb);
                            if (!checkBox.isChecked()) {
                                checkBox.setChecked(true);
                                view.setBackgroundColor(Color.parseColor("#f4f4f4"));
                                TextView textView = (TextView) view.findViewById(R.id.tv);
                                textView.setTextColor(InputStoreTypeActivity.this.getResources().getColor(R.color.main_color));
                                myFragment.adapter.changeStatus(i);
                                adapter.notifyDataSetChanged();
                                shopcategoryInfo = data.get(mPosition).getCategoryItems().get(i);
                                intent.putExtra("type", shopcategoryInfo);
                            } else {
                                checkBox.setChecked(false);
                                shopcategoryInfo = null;
                            }
                        }
                    });
                    adapter.notifyDataSetChanged();
                }

            });
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();
        for (int i = 0; i < data.size(); i++) {
            myFragment = new InputStoreTypeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, myFragment);
            Bundle bundle = new Bundle();
            bundle.putSerializable(InputStoreTypeFragment.TAG, (Serializable) data.get(mPosition));
            myFragment.setArguments(bundle);
            fragmentTransaction.commit();
        }
        myFragment.setLinsener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb);
                checkBox.setChecked(true);
                view.setBackgroundColor(Color.parseColor("#f4f4f4"));
                TextView textView = (TextView) view.findViewById(R.id.tv);
                textView.setTextColor(InputStoreTypeActivity.this.getResources().getColor(R.color.main_color));
                adapter.notifyDataSetChanged();
                intent.putExtra("type", (Serializable) data.get(mPosition).getCategoryItems().get(i));
                Toast.makeText(InputStoreTypeActivity.this, data.get(position).getCategoryItems().get(i).getCategoryId(), Toast.LENGTH_SHORT).show();*/

                CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb);
                if (!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                    view.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    TextView textView = (TextView) view.findViewById(R.id.tv);
                    textView.setTextColor(InputStoreTypeActivity.this.getResources().getColor(R.color.main_color));
                    myFragment.adapter.changeStatus(i);
                    adapter.notifyDataSetChanged();
                    shopcategoryInfo = data.get(mPosition).getCategoryItems().get(i);
                    intent.putExtra("type", shopcategoryInfo);
                } else {
                    checkBox.setChecked(false);
                    shopcategoryInfo = null;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (shopcategoryInfo != null) {
            setResult(6, intent);
        }
        return super.onKeyDown(keyCode, event);

    }

    @OnClick({R.id.sure_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn:
                if (shopcategoryInfo != null) {
                    setResult(6, intent);
                    finish();
                }
                break;
        }
    }
}
