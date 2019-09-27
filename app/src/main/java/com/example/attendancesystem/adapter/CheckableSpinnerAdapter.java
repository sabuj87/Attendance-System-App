package com.example.attendancesystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.attendancesystem.R;

import java.util.List;
import java.util.Set;

public class CheckableSpinnerAdapter<T> extends BaseAdapter {

   public static class SpinnerItem<T> {
        private String txt;
        public T item;


       public SpinnerItem(T t, String s) {

            item = t;
            txt =s;

        }
    }  public static class SpinnerCode<T> {
        private String code;
        public T codeT;


       public SpinnerCode(T codeT, String code) {

           this.code=code;

           this.codeT=codeT;

        }
    }

    private Context context;
    private Set<T> selected_items;
    private Set<T> selected_course;
    private List<SpinnerItem<T>> all_items;
    private List<SpinnerCode<T>> course_code;
    private String headerText;

   public CheckableSpinnerAdapter(Context context,
                            String headerText,
                            List<SpinnerItem<T>> all_items,List<SpinnerCode<T>> course_code,
                            Set<T> selected_items,Set<T> selected_course) {
        this.context = context;
        this.headerText = headerText;
        this.all_items = all_items;
        this.course_code=course_code;
        this.selected_items = selected_items;
        this.selected_course=selected_course;
    }

    @Override
    public int getCount() {
        return all_items.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if( position < 1 ) {
            return null;
        }
        else {
            return all_items.get(position-1);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null ) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.spinner_item, parent, false);

            holder = new ViewHolder();
            holder.linearLayout=convertView.findViewById(R.id.llId);
            holder.mTextView = convertView.findViewById(R.id.text);
            holder.mCheckBox = convertView.findViewById(R.id.checkbox);
            holder.mCode=convertView.findViewById(R.id.code);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if( position < 1 ) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
            holder.mCode.setVisibility(View.INVISIBLE);
            holder.mTextView.setText(headerText);
        }
        else {
            final int listPos = position - 1;
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mCode.setVisibility(View.VISIBLE);
            holder.mTextView.setText(all_items.get(listPos).txt);
            holder.mCode.setText(course_code.get(listPos).code);

            final T item = all_items.get(listPos).item;
            final T codeT=course_code.get(listPos).codeT;
            boolean isSel = selected_items.contains(item);

            holder.mCheckBox.setOnCheckedChangeListener(null);
            holder.mCheckBox.setChecked(isSel);

            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if( isChecked ) {
                        selected_items.add(item);
                        selected_course.add(codeT);
                    }
                    else {
                        selected_items.remove(item);
                        selected_course.remove(codeT);
                    }
                }
            });

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mCheckBox.toggle();
                }
            });


        }

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
        private TextView mCode;
        private LinearLayout linearLayout;
    }
}