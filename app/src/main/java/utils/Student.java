package utils;

/**
 * Created by bunny on 26/05/17.
 */


public class Student {

    private String mName ="";
    private int mAge =0;
    private String mGender;
    private int mHindiMarks=0;
    private int mEnglishMarks =0 ;
    private int mAverageMarks =0;
    private int mTotalMarks =0 ;


    private Student(String mName, int mAge, String mGender) {
        this.mName = mName;
        this.mAge = mAge;
        this.mGender = mGender;
    }

    public static Student createStudent(String name , int age , String gender){
        if(age >0 && age <115) {
            return new Student(name, age, gender);
        }
        return null;
    }

    public String getmName() {

        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public int getmHindiMarks() {
        return mHindiMarks;
    }

    public boolean setmHindiMarks(int mHindiMarks) {
        if(mHindiMarks>=0 && mHindiMarks<= 100) {
            this.mHindiMarks = mHindiMarks;
            calculatemAverageMarks();
            calculatemTotalMarks();
            return true;
        }else
            return false;

    }

    public int getmEnglishMarks() {
        return mEnglishMarks;
    }


    public boolean setmEnglishMarks(int mEnglishMarks) {
        if (mEnglishMarks>=0 && mEnglishMarks<=100) {
            this.mEnglishMarks = mEnglishMarks;
            calculatemAverageMarks();
            calculatemTotalMarks();

            return true;
        } else
            return false;
    }

    public int getmAverageMarks() {
        return mAverageMarks;
    }

    public void calculatemAverageMarks() {

        this.mAverageMarks=(mHindiMarks+mEnglishMarks)/2;
    }

    public int getmTotalMarks() {
        return mTotalMarks;
    }

    public void calculatemTotalMarks() {
        this.mTotalMarks=mHindiMarks+mEnglishMarks;
    }

    @Override
    public String toString() {
        return
                "Name='" + mName + '\'' +
                "\nAge=" + mAge +
                "\nGender='" + mGender + '\'' +
                "\nHindiMarks=" + mHindiMarks +
                "\nEnglishMarks=" + mEnglishMarks +
                "\nAverageMarks=" + mAverageMarks +
                "\nTotalMarks=" + mTotalMarks ;
    }

}
