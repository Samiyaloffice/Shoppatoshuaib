package com.shoppa.Model;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class DashboardButtonModel {

    MaterialButton materialButton;
    MaterialTextView materialTextView;

    public DashboardButtonModel(MaterialButton materialButton, MaterialTextView materialTextView) {
        this.materialButton = materialButton;
        this.materialTextView = materialTextView;
    }

    public MaterialButton getMaterialButton() {
        return materialButton;
    }

    public void setMaterialButton(MaterialButton materialButton) {
        this.materialButton = materialButton;
    }

    public MaterialTextView getMaterialTextView() {
        return materialTextView;
    }

    public void setMaterialTextView(MaterialTextView materialTextView) {
        this.materialTextView = materialTextView;
    }
}
