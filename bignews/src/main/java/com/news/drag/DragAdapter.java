package com.news.drag;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.news.activity.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author xiaanming
 * @blog http://blog.csdn.net/xiaanming
 */
public class DragAdapter extends BaseAdapter implements DragGridBaseAdapter {
    //	private List<HashMap<String, Object>> list;
    private ArrayList<String> list;
    private LayoutInflater mInflater;
    private int mHidePosition = -1;
    private Handler handler;
    private ArrayList<Fragment> fragments;
    public DragAdapter(Context context, ArrayList<String> list, Handler handler, ArrayList<Fragment> fragments) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.handler = handler;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * ���ڸ���convertView����ĳЩitem��ʧ�ˣ��������ﲻ����item��
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.grid_item, null);
        //TODO 添加item点击事件
        Button button = (Button) convertView.findViewById(R.id.item_draggrid_button);
        button.setText(list.get(position).substring(1));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 2;
                message.arg1 = position;
                handler.sendMessage(message);
            }
        });
        ViewGroup.LayoutParams params = button.getLayoutParams();
        params.width = 150;
        params.height = 60;
        button.setLayoutParams(params);
        if (position == mHidePosition) {
            convertView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }



    @Override
    public void reorderItems(int oldPosition, int newPosition) {
//        HashMap<String, Object> temp = list.get(oldPosition);
        Fragment fragment = fragments.get(oldPosition);
            String temp = list.get(oldPosition);
            if (oldPosition < newPosition) {
                for (int i = oldPosition; i < newPosition; i++) {
                    Collections.swap(list, i, i + 1);
                    Collections.swap(fragments, i, i + 1);
                }
            } else if (oldPosition > newPosition) {
                for (int i = oldPosition; i > newPosition; i--) {
                    Collections.swap(list, i, i - 1);
                    Collections.swap(fragments, i, i - 1);
                }
            }
        fragments.set(newPosition,fragment);
        list.set(newPosition, temp);
        handler.sendEmptyMessage(1);

    }

    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }


}
