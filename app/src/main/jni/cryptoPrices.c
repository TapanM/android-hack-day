#include <jni.h>
#include <stdio.h>
#include <android/log.h>
#include <time.h>

// from android samples
/* return current time in milliseconds */
static double now_ms(void) {

     struct timespec res;
     clock_gettime(CLOCK_REALTIME, &res);
     return 1000.0 * res.tv_sec + (double) res.tv_nsec / 1e6;
}

JNIEXPORT jstring JNICALL
Java_com_example_androidhackday_activity_MainActivity_getLogsData(JNIEnv *env, jobject instance, jstring symbol, jstring priceUsd) {

     const char *coinSymbol = (*env)->GetStringUTFChars(env, symbol, 0);
     const char *price = (*env)->GetStringUTFChars(env, priceUsd, 0);
     char formattedString[1024];
     sprintf(formattedString, "<%f><%s><%s>\n", now_ms(), coinSymbol, price);
     return (*env)->NewStringUTF(env, formattedString);
}