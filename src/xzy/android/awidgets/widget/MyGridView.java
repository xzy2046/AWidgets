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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class MyGridView extends GridView {

    private ImageView mDragImageView;

    private int mDragSrcPosition;

    private int mDragPosition;

    private int mDragXPoint;

    private int mDragXOffset;

    private int mDragYPoint;

    private int mDragYOffset;

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mWindowParams;

    private int mScaledTouchSlop;

    private int mUpScrollBounce;

    private int mDownScrollBounce;

    private static boolean DRAG_MODE = false;

    private int mDownX = -1;

    private int mDownY = -1;

    private int mDownRawX = -1;

    private int mDownRawY = -1;

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.setBackgroundColor(Color.WHITE);
        this.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
                Log.i("xzy", "---------------position----------------------" + position);

                mDragSrcPosition = mDragPosition = pointToPosition(mDownX, mDownY);
                Log.i("xzy", " -------------mDownX is : " + mDownX + "--------------mDownY is : "
                        + mDownY);
                if (mDragPosition == AdapterView.INVALID_POSITION) {
                    return false;
                }

                Log.i("xzy1", "dragPosition is : " + mDragPosition);
                ViewGroup itemView = (ViewGroup) view;
                if (itemView != null) {
                    mDragYPoint = mDownY - itemView.getTop();
                    mDragYOffset = (int) (mDownRawY - mDownY);

                    //TODO
                    mDragXPoint = mDownX - itemView.getLeft() + itemView.getWidth() + 16;
                    mDragXOffset = (int) (mDownRawX - mDownX);
                    Log.i("xzy", "dragXOffset is " + mDragXOffset);
                    mUpScrollBounce = Math.min(mDownY - mScaledTouchSlop,
                            MyGridView.this.getHeight() / 3);
                    mDownScrollBounce = Math.max(mDownY + mScaledTouchSlop,
                            MyGridView.this.getHeight() * 2 / 3);

                    itemView.setDrawingCacheEnabled(true);
                    Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
                    startDrag(bm, mDownX, mDownY);
                    ((GridAdapter) MyGridView.this.getAdapter()).setTransparentItemIndex(position);
                }
                mDownX = -1;
                mDownY = -1;
                mDownRawX = -1;
                mDownRawY = -1;
                return false;
            }

        });
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (mDragImageView != null && mDragPosition != INVALID_POSITION) {
                    if (DRAG_MODE) {
                        int upY = (int) ev.getY();
                        int upX = (int) ev.getX();
                        stopDrag();
                        onDrop(upX, upY);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDragImageView != null && mDragPosition != INVALID_POSITION) {
                    if (DRAG_MODE) {
                        int moveY = (int) ev.getY();
                        int moveX = (int) ev.getX();
                        onDrag(moveX, moveY);
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                mDownRawX = (int) ev.getRawX();
                mDownRawY = (int) ev.getRawY();
                Log.i("xzy", "mDownX is " + mDownX + " mDownY is " + mDownY);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public synchronized void startDrag(Bitmap bm, int downX, int downY) {
        stopDrag();

        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.gravity = Gravity.TOP;
        mWindowParams.x = downX - mDragXPoint + mDragXOffset;
        mWindowParams.y = downY - mDragYPoint + mDragYOffset;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mWindowParams.format = PixelFormat.TRANSLUCENT;

        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bm);
        mWindowManager = (WindowManager) getContext().getSystemService("window");
        mWindowManager.addView(imageView, mWindowParams);
        mDragImageView = imageView;
        DRAG_MODE = true;
        this.invalidate();
    }

    public synchronized void stopDrag() {
        if (mDragImageView != null) {
            mWindowManager.removeViewImmediate(mDragImageView);
            mDragImageView = null;
        }
        DRAG_MODE = false;
        this.invalidate();
    }

    public void onDrag(int x, int y) {
        if (mDragImageView != null) {
            mWindowParams.alpha = 0.8f;
            mWindowParams.x = x - mDragXPoint + mDragXOffset;
            mWindowParams.y = y - mDragYPoint + mDragYOffset;
            mWindowManager.updateViewLayout(mDragImageView, mWindowParams);
        }

        int tempPosition = pointToPosition(x, y);
        if (tempPosition != INVALID_POSITION) {
            mDragPosition = tempPosition;
        }

        if (y < getChildAt(1).getTop()) {
            mDragPosition = 0;
        } else if (y > getChildAt(getChildCount() - 1).getBottom()) {
            mDragPosition = getAdapter().getCount() - 1;
        }
        this.invalidate();

        int scrollHeight = 0;
        if (y < mUpScrollBounce) {
            scrollHeight = 8;
        } else if (y > mDownScrollBounce) {
            scrollHeight = -8;
        }

        if (scrollHeight != 0) {
            ViewGroup itemView = (ViewGroup) getChildAt(mDragPosition - getFirstVisiblePosition());
            if (itemView != null) {
                smoothScrollToPosition(itemView.getTop() + scrollHeight);
            }
        }
    }

    public void onDrop(int x, int y) {

        int tempPosition = pointToPosition(x, y);
        if (tempPosition != INVALID_POSITION) {
            mDragPosition = tempPosition;
        }

        if (y < getChildAt(1).getTop()) {
            mDragPosition = 0;
        } else if (y > getChildAt(getChildCount() - 1).getBottom()) {
            mDragPosition = getAdapter().getCount() - 1;
        }
        Log.i("xzy", "---------------dragSrcPosition is : " + mDragSrcPosition);
        ViewGroup itemView = (ViewGroup) getChildAt(mDragSrcPosition); // maybe
                                                                      // has bug
        if (itemView != null) {
            ((GridAdapter) MyGridView.this.getAdapter()).setTransparentItemIndex(-1);
        }

        if (mDragPosition >= 0 && mDragPosition < getAdapter().getCount()) {
            @SuppressWarnings("unchecked")
            GridAdapter adapter = (GridAdapter) getAdapter();
            Object dragItem = adapter.getItem(mDragSrcPosition);
            adapter.remove(dragItem);
            adapter.insert(dragItem, mDragPosition);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DRAG_MODE) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            ViewGroup itemView = (ViewGroup) getChildAt(mDragPosition - getFirstVisiblePosition());
            if (itemView != null && itemView.getVisibility() != View.INVISIBLE) {

                Path path = new Path();
                path.moveTo(itemView.getLeft() - 2, itemView.getTop());
                path.lineTo(itemView.getLeft() - 2, itemView.getBottom());
                PathEffect effects = new DashPathEffect(new float[] {
                        5, 5, 5, 5
                }, 5);
                paint.setPathEffect(effects);
                canvas.drawPath(path, paint);
                Log.i("xzy", mDragPosition + "draw----------------");
            } else {

            }
        }
        super.onDraw(canvas);
    }

}
