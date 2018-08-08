package yogesh.firzen.filelister;

import com.squareup.otto.Bus;

/**
 * Created by vladimir on 27/07/18.
 */

public class GlobalBus {
    private static Bus sBus;
    public static Bus getBus() {
        if (sBus == null)
            sBus = new Bus();
        return sBus;
    }
}
