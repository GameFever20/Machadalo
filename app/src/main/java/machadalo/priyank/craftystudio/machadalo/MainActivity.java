package machadalo.priyank.craftystudio.machadalo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import utils.DatabaseHandler;
import utils.Student;
import utils.StudentListAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView mStudentListView;
    private ArrayList<Student> mStudentArrayList = new ArrayList<>();
    private StudentListAdapter mStudentListAdapter;
    int sortType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        initializeStudentList();

        mStudentListView = (ListView) findViewById(R.id.student_listView);

        Snackbar.make(mStudentListView, "Long click to change details", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

        mStudentListAdapter = new StudentListAdapter(mStudentArrayList, this);

        mStudentListView.setDividerHeight(0);
        mStudentListView.setAdapter(mStudentListAdapter);
        mStudentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                openStudentDetailEditDialog(position);

                return false;
            }
        });



    }

    private void openStudentDetailEditDialog(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.student_detail_edit_layout);
        dialog.setTitle(mStudentArrayList.get(position).getmName());

        //TextView mStudentNameTextView = (TextView) dialog.findViewById(R.id.student_detail_edit_studentname_textview);
        // mStudentNameTextView.setText(mStudentArrayList.get(position).getmName());

        final EditText mHindiMarksEditText = (EditText) dialog.findViewById(R.id.student_detail_edit_hindimarks_edittext);
        final EditText mEnglishMarksEditText = (EditText) dialog.findViewById(R.id.student_detail_edit_englishmarks_edittext);

        mHindiMarksEditText.setText(String.valueOf(mStudentArrayList.get(position).getmHindiMarks()));
        mEnglishMarksEditText.setText(String.valueOf(mStudentArrayList.get(position).getmEnglishMarks()));


        Button mDoneButton = (Button) dialog.findViewById(R.id.student_detail_edit_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!mStudentArrayList.get(position).setmHindiMarks(Integer.parseInt(mHindiMarksEditText.getText().toString()))) {
                    Toast.makeText(MainActivity.this, "Invaid Hindi Marks", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mStudentArrayList.get(position).setmEnglishMarks(Integer.parseInt(mEnglishMarksEditText.getText().toString()))) {

                    Toast.makeText(MainActivity.this, "Invaid English Marks", Toast.LENGTH_SHORT).show();
                    return;

                }

                dialog.cancel();
                checkSortType();
                mStudentListAdapter.notifyDataSetChanged();


            }
        });


        Button mCancelButton = (Button) dialog.findViewById(R.id.student_detail_edit_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });

        Button mDeleteButton = (Button) dialog.findViewById(R.id.student_detail_edit_delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.cancel();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Want to Delete ");
                builder.setMessage(mStudentArrayList.get(position).toString());
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mStudentArrayList.remove(position);

                        mStudentListAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });


        dialog.show();

    }

    private void initializeStudentList() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        mStudentArrayList = databaseHandler.getAllStudents();
        setStudentListMarks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case R.id.action_orderBy_hindiMarks:
                sortbyHindiMarks();
                sortType = 1;
                break;
            case R.id.action_orderBy_englishMarks:
                sortbyEnglishMarks();
                sortType = 2;
                break;
            case R.id.action_orderBy_gender:
                sortbyGender();
                sortType = 3;
                break;
            case R.id.action_topPerformer_hindiMarks:
                topPerformerHindiMarks();
                break;
            case R.id.action_topPerformer_englishMarks:
                topPerformerEnglishMarks();
                break;
            case R.id.action_topPerformer_averageMarks:
                topPerformerAverageMarks();
                break;


        }

        return super.onOptionsItemSelected(item);
    }


    public void setStudentListMarks() {

        for (int position = mStudentArrayList.size() - 1; position >= 0; position--) {


            mStudentArrayList.get(position).setmHindiMarks((int) (Math.random() * 100));
            mStudentArrayList.get(position).setmEnglishMarks((int) (Math.random() * 100));
        }

    }

    public void checkSortType() {
        switch (sortType) {
            case 1:
                sortbyHindiMarks();
                break;
            case 2:
                sortbyEnglishMarks();
                break;
            case 3:
                sortbyGender();
                break;

        }
    }

    public void sortbyHindiMarks() {


        for (int i = 0; i < mStudentArrayList.size(); i++) {

            Student student = mStudentArrayList.get(i);
            int topMarksIndex = i;
            for (int j = i + 1; j < mStudentArrayList.size(); j++) {

                if (mStudentArrayList.get(j).getmHindiMarks() > mStudentArrayList.get(topMarksIndex).getmHindiMarks()) {
                    topMarksIndex = j;
                }

            }


            Student tempStudent = mStudentArrayList.get(topMarksIndex);

            mStudentArrayList.set(i, tempStudent);
            mStudentArrayList.set(topMarksIndex, student);


        }


        mStudentListAdapter.notifyDataSetChanged();

    }

    public void sortbyEnglishMarks() {

        for (int i = 0; i < mStudentArrayList.size(); i++) {

            Student student = mStudentArrayList.get(i);
            int topMarksIndex = i;
            for (int j = i + 1; j < mStudentArrayList.size(); j++) {

                if (mStudentArrayList.get(j).getmEnglishMarks() > mStudentArrayList.get(topMarksIndex).getmEnglishMarks()) {
                    topMarksIndex = j;
                }

            }


            Student tempStudent = mStudentArrayList.get(topMarksIndex);

            mStudentArrayList.set(i, tempStudent);
            mStudentArrayList.set(topMarksIndex, student);


        }


        mStudentListAdapter.notifyDataSetChanged();

    }

    public void sortbyGender() {

        for (int i = 0; i < mStudentArrayList.size(); i++) {

            Student student = mStudentArrayList.get(i);
            int topMarksIndex = i;
            for (int j = i + 1; j < mStudentArrayList.size(); j++) {

                if (mStudentArrayList.get(topMarksIndex).getmGender().compareTo(mStudentArrayList.get(j).getmGender()) > 0) {
                    topMarksIndex = j;
                }

            }


            Student tempStudent = mStudentArrayList.get(topMarksIndex);

            mStudentArrayList.set(i, tempStudent);
            mStudentArrayList.set(topMarksIndex, student);


        }


        mStudentListAdapter.notifyDataSetChanged();

    }

    public void topPerformerHindiMarks() {

        int topIndex = 0;
        for (int i = 0; i < mStudentArrayList.size(); i++) {
            if (mStudentArrayList.get(i).getmHindiMarks() > mStudentArrayList.get(topIndex).getmHindiMarks()) {
                topIndex = i;
            }

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Top Performer in Hindi");
        builder.setMessage(mStudentArrayList.get(topIndex).toString());
        builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }

    public void topPerformerEnglishMarks() {
        int topIndex = 0;
        for (int i = 0; i < mStudentArrayList.size(); i++) {
            if (mStudentArrayList.get(i).getmEnglishMarks() > mStudentArrayList.get(topIndex).getmEnglishMarks()) {
                topIndex = i;
            }

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Top Performer in English");
        builder.setMessage(mStudentArrayList.get(topIndex).toString());
        builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void topPerformerAverageMarks() {

        int topIndex = 0;
        for (int i = 0; i < mStudentArrayList.size(); i++) {
            if (mStudentArrayList.get(i).getmAverageMarks() > mStudentArrayList.get(topIndex).getmAverageMarks()) {
                topIndex = i;
            }

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Top Performer by Average Marks");
        builder.setMessage(mStudentArrayList.get(topIndex).toString());
        builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


}
