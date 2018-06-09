package de.hska.vis.webshop.core.util;

/**
 * StarterUtility consolidates the delayed starter methods
 * to provide a single point of configuration for the delays.
 */
public class StarterUtility {
    /**
     * The default delay for core services.
     */
    public static final int DELAY_CORE = 30;
    /**
     * The default delay for composite services.
     */
    public static final int DELAY_COMPOSITE = 60;

    /**
     * Sleep method with a configurable wait parameter in seconds.
     *
     * Can be made public if needed.
     *
     * !!! IMPORTANT !!!
     * Method can be interrupted and therefore the delay is cannot be guaranteed.
     * !!! IMPORTANT !!!
     *
     * @param seconds how long the method delays the thread
     */
    private static void delayedStart(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {}
    }

    /**
     * Utility method to delay a service {@link #DELAY_CORE} seconds.
     */
    public static void delayCore() {
        delayedStart(DELAY_CORE);
    }

    /**
     * Utility method to delay a service {@link #DELAY_COMPOSITE} seconds.
     */
    public static void delayComposite() {
        delayedStart(DELAY_COMPOSITE);
    }
}
