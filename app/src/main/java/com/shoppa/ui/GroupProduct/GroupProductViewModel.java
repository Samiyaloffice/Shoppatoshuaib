package com.shoppa.ui.GroupProduct;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.ProductGroupModel;

import java.util.ArrayList;

public class GroupProductViewModel extends ViewModel {

    MutableLiveData<ArrayList<ProductGroupModel>> data;
    Context context;
    ArrayList<ProductGroupModel> mProductGroupModelArrayList;

    public GroupProductViewModel() {
        mProductGroupModelArrayList = new ArrayList<>();
        data = new MutableLiveData<>();
        data.setValue(mProductGroupModelArrayList);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LiveData<ArrayList<ProductGroupModel>> getArrayListData() {
        return data;
    }

    public void setArrayListData(String GroupName) {
        mProductGroupModelArrayList.add(new ProductGroupModel(GroupName, "0"));
        data.setValue(mProductGroupModelArrayList);
    }

    public void removeGroup(int position) {
        mProductGroupModelArrayList.remove(position);
    }
}