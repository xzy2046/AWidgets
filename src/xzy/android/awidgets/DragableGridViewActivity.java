/*
 *  This program is a widget library base on Android platform
 *  Copyright (C) 2012  Xu Zhengyang
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package xzy.android.awidgets;

import xzy.android.awidgets.widget.GridAdapter;
import xzy.android.awidgets.widget.GridInfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class DragableGridViewActivity extends Activity {
    private GridView gridview;
    private List<GridInfo> list;
    private GridAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlayout);
        gridview = (GridView) findViewById(R.id.gridview);
        list = new ArrayList<GridInfo>();
        list.add(new GridInfo("name0"));
        list.add(new GridInfo("name1"));
        list.add(new GridInfo("name2"));
        list.add(new GridInfo("name3"));
        list.add(new GridInfo("name4"));
        list.add(new GridInfo("name5"));
        list.add(new GridInfo("name6"));
        list.add(new GridInfo("name7"));
        list.add(new GridInfo("name8"));
        list.add(new GridInfo("name9"));
        list.add(new GridInfo("name10"));
        list.add(new GridInfo("name11"));
        list.add(new GridInfo("name12"));
        list.add(new GridInfo("name13"));
        list.add(new GridInfo("name14"));
        list.add(new GridInfo("name15"));
        list.add(new GridInfo("name16"));
        list.add(new GridInfo("name17"));
        list.add(new GridInfo("name18"));
        list.add(new GridInfo("name19"));
        list.add(new GridInfo("name20"));
        list.add(new GridInfo("name21"));
        list.add(new GridInfo("name22"));
        list.add(new GridInfo("name23"));
        list.add(new GridInfo("name24"));
        list.add(new GridInfo("name25"));
        list.add(new GridInfo("name26"));
        list.add(new GridInfo("name27"));
        list.add(new GridInfo("name28"));
        list.add(new GridInfo("name29"));
        list.add(new GridInfo("name30"));
        adapter = new GridAdapter(this);
        adapter.setList((ArrayList<GridInfo>) list);
        gridview.setAdapter(adapter);
    }
}
