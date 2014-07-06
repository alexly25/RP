package com.alex.rp.group;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.alex.rp.R;
import com.alex.rp.db.Color;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

/**
 * Created by alex on 11.06.2014.
 */
public class GroupAdapter extends BaseAdapter {

    private final static String LOG = "GroupAdapter";
    private Context context;
    private ArrayList<Group> groups;
    private static LayoutInflater inflater;
    private boolean[] checked;

    GroupAdapter(Context context, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        checked = new boolean[groups.size()];

        for(int i = 0; i < checked.length; i++){
            checked[i] = false;
        }
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Group getItem(int i) {
        return groups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.group_adapter_item, null);

        Group group = groups.get(i);

        int color = group.getColor();
        String name = group.getName();
        String commerce = (group.isCommerce()) ? "Коммерция" : "Бюджет";

        TextView tvColor = (TextView) vi.findViewById(R.id.tv_color);
        TextView tvName = (TextView) vi.findViewById(R.id.tv_name);
        TextView tvCommerce = (TextView) vi.findViewById(R.id.tv_commerce);
        final CheckBox checkBox = (CheckBox) vi.findViewById(R.id.cb_group_item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked[i] = !checked[i];
            }
        });

        tvColor.setBackgroundColor(color);
        tvName.setText(name);
        tvCommerce.setText(commerce);
        return vi;
    }

    public boolean isChecked(int i){
        return checked[i];
    }
}
