package com.xentaqsys.oet;

/**
 * Created by Swatantra.Singh on 9/21/17.
 */

public class OptionItems {
    private String value1,value2,value3;
    private boolean isSelected;


    public OptionItems(){

    }


    public OptionItems(String value1, String value2, String value3, boolean isSelected) {


        this.value1=value1;
        this.value2=value2;
        this.value3 = value3;
        this.isSelected =isSelected;

    }



    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



}

