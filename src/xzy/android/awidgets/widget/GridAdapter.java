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

package xzy.android.awidgets.widget;

import xzy.android.awidgets.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private class GridHolder {
        ImageView appImage;

        TextView appName;
    }

    private Context context;

    private ArrayList<GridInfo> list;

    private LayoutInflater mInflater;

    private int TransparentItemIndex = -1;

    public GridAdapter(Context c) {
        super();
        this.context = c;
    }

    public void setList(ArrayList<GridInfo> list) {
        this.list = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int index) {

        return list.get(index);
    }

    public void remove(Object object) {
        list.remove(object);
    }

    public void insert(Object object, int pos) {
        list.add(pos, (GridInfo) object);
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        GridHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_item, null);
            holder = new GridHolder();
            holder.appImage = (ImageView) convertView.findViewById(R.id.itemImage);
            holder.appName = (TextView) convertView.findViewById(R.id.itemText);
            convertView.setTag(holder);

        } else {
            holder = (GridHolder) convertView.getTag();
        }
        GridInfo info = list.get(index);
        if (info != null) {
            holder.appName.setText(info.getName());
        }
        if (index == TransparentItemIndex) {
            // convertView.setAlpha(0);
        } else {
            // convertView.setAlpha(255);
        }
        return convertView;
    }

    public void setTransparentItemIndex(int index) {
        TransparentItemIndex = index;
    }

}
