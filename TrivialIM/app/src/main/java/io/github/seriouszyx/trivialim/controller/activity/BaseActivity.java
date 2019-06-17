package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;

import io.github.seriouszyx.trivialim.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
