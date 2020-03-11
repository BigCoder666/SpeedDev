package me.tx.app.network;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IArrayForm extends IArray {
    public IArrayForm(BaseActivity activity) {
        super(activity);
    }

    @Override
    public boolean iamJson() {
        return false;
    }

    @Override
    public boolean iamForm() {
        return true;
    }
}
