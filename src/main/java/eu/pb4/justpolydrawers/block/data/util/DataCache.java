package eu.pb4.justpolydrawers.block.data.util;

import eu.pb4.justpolydrawers.data.DataContainer;
import org.jetbrains.annotations.Nullable;

public interface DataCache {
    @Nullable
    DataContainer getCachedData();

    void setCachedData(DataContainer lastData);
}
