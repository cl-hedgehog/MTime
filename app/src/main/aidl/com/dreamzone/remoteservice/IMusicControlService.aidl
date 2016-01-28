// IMusicControlService.aidl
package com.dreamzone.remoteservice;

// Declare any non-default types here with import statements

interface IMusicControlService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void play();
    void stop();
    void pause();

    int remoteAdd(int a, int b);

}
