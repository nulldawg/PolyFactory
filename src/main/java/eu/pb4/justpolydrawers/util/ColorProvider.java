package eu.pb4.justpolydrawers.util;

public interface ColorProvider {
    int getColor();
    void setColor(int color);

    boolean isDefaultColor();
    interface Consumer {
        void setColor(int color);
    }
}
