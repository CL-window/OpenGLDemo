package com.cl.slack.opengldemo;

import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    /**
     * 寻找Manifest里activity文件
     * acrivity add   label and
     * <intent-filter>
             <action android:name="android.intent.action.MAIN" />
             <category android:name="com.cl.slack.opengldemo.SAMPLE_CODE" />
       </intent-filter>
     */
    private static final String CategoryName="com.cl.slack.opengldemo.SAMPLE_CODE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
    }

    protected List getData() {
        List<Map> myData = new ArrayList<Map>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(CategoryName);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath = null;

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString()
                    : info.activityInfo.name;

            String[] labelPath = label.split("/");

            String nextLabel = prefixPath == null ? labelPath[0]
                    : labelPath[prefixPath.length];

            if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                addItem(myData,
                        nextLabel,
                        activityIntent(
                                info.activityInfo.applicationInfo.packageName,
                                info.activityInfo.name));
            } else {
                if (entries.get(nextLabel) == null) {
                    addItem(myData, nextLabel, browseIntent(nextLabel));
                    entries.put(nextLabel, true);
                }
            }

        }

//        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    private final static Comparator<Map> sDisplayNameComparator = new Comparator<Map>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Map map1, Map map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        return result;
    }

    protected void addItem(List<Map> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map map = (Map) l.getItemAtPosition(position);
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }







    // 下面这种方式也可以,不需要处理 Manifest里activity
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        setContentView(R.layout.activity_main);
//
//        String[] entities = {"DrawPoint", "DrawLine", "DrawTriangle", "DrawIcosahedron",
//        "DrawSolarSystem", "DrawSphere","DrawSquare","DrawRotateSquare" };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entities);
//        setListAdapter(adapter);
//
//    }
//
//    public void onListItemClick(ListView parent, View v, int position, long id) {
//        String item = (String) getListAdapter().getItem(position);
//        Intent intent = new Intent();
//        intent.setClassName(getPackageName(),getPackageName()+"."+item);
//        startActivity(intent);
//    }

}
