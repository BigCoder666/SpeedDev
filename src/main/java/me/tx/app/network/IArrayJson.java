package me.tx.app.network;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IArrayJson extends IArray {
    public IArrayJson(BaseActivity activity) {
        super(activity);
    }

    @Override
    public boolean iamJson() {
        return true;
    }

    @Override
    public boolean iamForm() {
        return false;
    }
}
