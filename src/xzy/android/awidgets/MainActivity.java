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

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

/**
 * (C) 2012 zhengyang.xu
 *
 * @author zhengyang.xu
 * @version 0.1
 * @since 11:14:55 AM Apr 17, 2012
 */
public class MainActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preference);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        // TODO Auto-generated method stub
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }


}
