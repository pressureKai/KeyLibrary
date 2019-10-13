package com.key.keylibrary.base;

import androidx.fragment.app.Fragment;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * created by key  on 2019/10/10
 */
public class BaseFragment extends Fragment  implements CustomAdapt {
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640f;
    }

}
