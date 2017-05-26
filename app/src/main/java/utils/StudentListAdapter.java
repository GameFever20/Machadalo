package utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import machadalo.priyank.craftystudio.machadalo.R;

/**
 * Created by bunny on 26/05/17.
 */

public class StudentListAdapter extends BaseAdapter {

    private ArrayList<Student> mStudentList;
    private Context mContext;
    private LayoutInflater mInflater;

    public StudentListAdapter(ArrayList<Student> mStudentList, Context mContext) {
        this.mStudentList = mStudentList;
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private static class ViewHolder {

        TextView mNameTextView;
        TextView mAgeTextView;
        TextView mGenderTextView;
        TextView mHindiMarks;
        TextView mEnglishMarks;
        TextView mAverageMarks;
        TextView mTotalMarks;


    }

    @Override
    public int getCount() {
        return mStudentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStudentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view ;
        ViewHolder holder;

        if (convertView == null) {

            view = mInflater.inflate(R.layout.student_listview_layout, null);


            holder = new ViewHolder();
            holder.mNameTextView = (TextView) view.findViewById(R.id.student_layout_name_textview);
            holder.mAgeTextView = (TextView) view.findViewById(R.id.student_layout_age_textview);
            holder.mGenderTextView = (TextView) view.findViewById(R.id.student_layout_gender_textview);
            holder.mHindiMarks = (TextView) view.findViewById(R.id.student_layout_hindimarks_textview);
            holder.mEnglishMarks = (TextView) view.findViewById(R.id.student_layout_englishmarks_textview);
            holder.mAverageMarks = (TextView) view.findViewById(R.id.student_layout_averagemarks_textview);
            holder.mTotalMarks = (TextView) view.findViewById(R.id.student_layout_totalmarks_textview);

            /************  Set holder with LayoutInflater ************/
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }


        if (mStudentList.size() <= 0) {
            //holder.text.setText("No Data");

        } else {
            Student student = mStudentList.get(position);

            holder.mNameTextView.setText(student.getmName());
            holder.mAgeTextView.setText("Age :"+String.valueOf(student.getmAge()));
            holder.mGenderTextView.setText("Gender :"+student.getmGender());
            holder.mHindiMarks.setText("Hindi Marks :"+student.getmHindiMarks());
            holder.mEnglishMarks.setText("English Marks :" +student.getmEnglishMarks());
            holder.mAverageMarks.setText("Average Marks :"+student.getmAverageMarks());
            holder.mTotalMarks.setText("Total Marks :"+student.getmTotalMarks());




        }


        return view;
    }


}
