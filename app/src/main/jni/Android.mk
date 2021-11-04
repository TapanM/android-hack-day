LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := cryptoPrices.c
LOCAL_MODULE    := cryptoPrices
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)