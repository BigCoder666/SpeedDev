package me.tx.app.common.widget;

import com.flyco.tablayout.listener.CustomTabEntity;

public abstract class TabEnty implements CustomTabEntity {
    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}
