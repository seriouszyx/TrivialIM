package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.seriouszyx.trivialim.R;

public class GroupListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_list);
    }
}
