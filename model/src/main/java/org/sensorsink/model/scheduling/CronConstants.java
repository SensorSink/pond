package org.sensorsink.model.scheduling;

public interface CronConstants
{
    String EVERY_MINUTE = "0 * * * * *";
    String EVERY_5_MINUTE = "0 */5 * * * *";
    String EVERY_15_MINUTE = "0 */15 * * * *";
    String EVERY_HOUR = "0 0 * * * *";
    String EVERY_4_HOURS = "0 0 */4 * * *";
    String EVERY_8_HOURS = "0 0 */8 * * *";
    String EVERY_12_HOURS = "0 0 */12 * * *";
    String EVERY_DAY_AT_MIDNIGHT = "0 0 0 * * *";
    String EVERY_DAY_AT_4AM = "0 0 0 * * *";
    String EVERY_DAY_AT_NOON = "0 0 0 * * *";
}
