package com.serhatmerak.spacewars.helpers;

import com.serhatmerak.spacewars.custom_actors.Box;

/**
 * Created by Serhat Merak on 15.06.2018.
 */

public interface BoxListener {
    void boxClicked(Box box);
    void boxLongPress(Box box);
}
