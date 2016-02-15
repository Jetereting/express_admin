package us.eiyou.express_admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import us.eiyou.express_admin.model.Ad;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_ad;
    Button b_ad, b_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        b_ad.setOnClickListener(this);
        b_query.setOnClickListener(this);
    }

    private void initView() {
        Bmob.initialize(this, "579a7850db3695c6b21115ca2c508009");
        et_ad = (EditText) findViewById(R.id.et_ad);
        b_ad = (Button) findViewById(R.id.b_ad);
        b_query = (Button) findViewById(R.id.b_query);
        BmobQuery<Ad> bmobQuery=new BmobQuery<>();
        bmobQuery.getObject(getApplicationContext(), "37703e9700", new GetListener<Ad>() {
            @Override
            public void onSuccess(Ad ad) {
                et_ad.setText(ad.getAd());
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_ad:
                Ad ad = new Ad();
                ad.setAd(et_ad.getText().toString());
                if (!TextUtils.isEmpty(et_ad.getText().toString())) {
                    ad.update(getApplicationContext(), "37703e9700", new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(), "设置成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getApplicationContext(), "设置失败！" + s, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "还没输东西呢！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.b_query:
                startActivity(new Intent(getApplicationContext(),QueryActivity.class));
        }
    }
}
