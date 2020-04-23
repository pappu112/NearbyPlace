package com.example.nearplace;

import android.content.Context;

public class ToastMgs {
    Context context;

    public ToastMgs(Context context) {
        this.context = context;
    }
    public void showToast(String mgs){
        android.widget.Toast.makeText(context,mgs, android.widget.Toast.LENGTH_SHORT).show();
    }
}
