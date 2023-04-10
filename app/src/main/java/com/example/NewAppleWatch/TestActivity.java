package com.example.NewAppleWatch;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dodola.bubblecloud.BubbleCloudView;
import com.dodola.bubblecloud.utils.FileManagerImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends Activity {

    private BubbleCloudView mListView;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FileManagerImageLoader.prepare(this.getApplication());

        final ArrayList<String> contacts = createContactList(19);
        final MyAdapter adapter = new MyAdapter(this, contacts);

        mListView = findViewById(R.id.my_list);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                final String message = "OnClick: " + contacts.get(position);
                Toast.makeText(TestActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private ArrayList<String> createContactList(final int size) {
        final ArrayList<String> contacts = new ArrayList<>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < size; i++) {
            final PackageInfo packageInfo = packages.get(i);
            contacts.add(packageInfo.applicationInfo.sourceDir);
        }
        return contacts;
    }

    private static class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(final Context context, final ArrayList<String> contacts) {
            super(context, 0, contacts);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
            }
            final CircularImageView itemRound = (CircularImageView) view.findViewById(R.id.item_round);
            FileManagerImageLoader.getInstance().addTask(getItem(position), itemRound, null, 48, 48, false);
            return view;
        }
    }
}
