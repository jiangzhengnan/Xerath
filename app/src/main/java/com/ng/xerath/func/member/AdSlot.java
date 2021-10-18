package com.ng.xerath.func.member;


import android.text.TextUtils;

import org.json.JSONObject;

public class AdSlot {
    public static final int TYPE_BANNER = 1;
    public static final int TYPE_INTERACTION_AD = 2;
    public static final int TYPE_SPLASH = 3;
    public static final int TYPE_CACHED_SPLASH = 4;
    public static final int TYPE_FEED = 5;
    public static final int TYPE_STREAM = 6;
    public static final int TYPE_REWARD_VIDEO = 7;
    public static final int TYPE_FULL_SCREEN_VIDEO = 8;
    public static final int TYPE_DRAW_FEED = 9;
    private String a;
    private int b;
    private int c;
    private float d;
    private float e;
    private int f;
    private boolean g;
    private boolean h;
    private String i;
    private int j;
    private String k;
    private String l;
    private int m;
    private int n;
    private int o;
    private int p;
    private boolean q;
    private int[] r;
    private String s;
    private int t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    private AdSlot() {
        this.m = 2;
        this.q = true;
    }

    public int getAdCount() {
        return this.f;
    }

    public String getAdId() {
        return this.w;
    }

    public int getAdType() {
        return this.n;
    }

    public int getAdloadSeq() {
        return this.t;
    }

    public String getBidAdm() {
        return this.v;
    }

    public String getCodeId() {
        return this.a;
    }

    public String getCreativeId() {
        return this.x;
    }

    public int getDurationSlotType() {
        return this.p;
    }

    public float getExpressViewAcceptedHeight() {
        return this.e;
    }

    public float getExpressViewAcceptedWidth() {
        return this.d;
    }

    public String getExt() {
        return this.y;
    }

    public int[] getExternalABVid() {
        return this.r;
    }

    public String getExtraSmartLookParam() {
        return this.s;
    }

    public int getImgAcceptedHeight() {
        return this.c;
    }

    public int getImgAcceptedWidth() {
        return this.b;
    }

    public String getMediaExtra() {
        return this.k;
    }

    public int getNativeAdType() {
        return this.o;
    }

    public int getOrientation() {
        return this.m;
    }

    public static int getPosition(int var0) {
        switch(var0) {
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
            case 4:
            case 7:
            case 8:
                return 5;
            case 5:
            case 9:
                return 3;
            case 6:
            default:
                return 3;
        }
    }

    public String getPrimeRit() {
        String var1;
        if (this.u == null) {
            var1 = "";
        } else {
            var1 = this.u;
        }

        return var1;
    }

    public int getRewardAmount() {
        return this.j;
    }

    public String getRewardName() {
        return this.i;
    }

    public String getUserData() {
        return this.z;
    }

    public String getUserID() {
        return this.l;
    }

    public boolean isAutoPlay() {
        return this.q;
    }

    public boolean isSupportDeepLink() {
        return this.g;
    }

    public boolean isSupportRenderConrol() {
        return this.h;
    }

    public void setAdCount(int var1) {
        this.f = var1;
    }

    public void setDurationSlotType(int var1) {
        this.p = var1;
    }

    public void setExternalABVid(int... var1) {
        this.r = var1;
    }

    public void setNativeAdType(int var1) {
        this.o = var1;
    }

    public void setUserData(String var1) {
        this.z = var1;
    }

    public JSONObject toJsonObj() {
        JSONObject var1 = new JSONObject();

        try {
            var1.put("mCodeId", this.a);
            var1.put("mIsAutoPlay", this.q);
            var1.put("mImgAcceptedWidth", this.b);
            var1.put("mImgAcceptedHeight", this.c);
            var1.put("mExpressViewAcceptedWidth", (double)this.d);
            var1.put("mExpressViewAcceptedHeight", (double)this.e);
            var1.put("mAdCount", this.f);
            var1.put("mSupportDeepLink", this.g);
            var1.put("mSupportRenderControl", this.h);
            var1.put("mRewardName", this.i);
            var1.put("mRewardAmount", this.j);
            var1.put("mMediaExtra", this.k);
            var1.put("mUserID", this.l);
            var1.put("mOrientation", this.m);
            var1.put("mNativeAdType", this.o);
            var1.put("mAdloadSeq", this.t);
            var1.put("mPrimeRit", this.u);
            var1.put("mExtraSmartLookParam", this.s);
            var1.put("mAdId", this.w);
            var1.put("mCreativeId", this.x);
            var1.put("mExt", this.y);
            var1.put("mBidAdm", this.v);
            var1.put("mUserData", this.z);
        } catch (Exception var2) {
        }

        return var1;
    }

    public String toString() {
        return "AdSlot{mCodeId='" + this.a + '\'' + ", mImgAcceptedWidth=" + this.b + ", mImgAcceptedHeight=" + this.c + ", mExpressViewAcceptedWidth=" + this.d + ", mExpressViewAcceptedHeight=" + this.e + ", mAdCount=" + this.f + ", mSupportDeepLink=" + this.g + ", mSupportRenderControl=" + this.h + ", mRewardName='" + this.i + '\'' + ", mRewardAmount=" + this.j + ", mMediaExtra='" + this.k + '\'' + ", mUserID='" + this.l + '\'' + ", mOrientation=" + this.m + ", mNativeAdType=" + this.o + ", mIsAutoPlay=" + this.q + ", mPrimeRit" + this.u + ", mAdloadSeq" + this.t + ", mAdId" + this.w + ", mCreativeId" + this.x + ", mExt" + this.y + ", mUserData" + this.z + '}';
    }

    public static class Builder {
        private String a;
        private int b = 640;
        private int c = 320;
        private boolean d = true;
        private boolean e = false;
        private int f = 1;
        private String g = "";
        private int h = 0;
        private String i;
        private String j = "defaultUser";
        private int k = 2;
        private int l;
        private String m;
        private int n;
        private float o;
        private float p;
        private boolean q = true;
        private int[] r;
        private int s;
        private String t;
        private String u;
        private String v;
        private String w;
        private String x;
        private String y;

        public Builder() {
        }

        public AdSlot.Builder setExtraParam(String var1) {
            this.m = var1;
            return this;
        }

        public AdSlot.Builder setAdType(int var1) {
            this.n = var1;
            return this;
        }

        public AdSlot.Builder setAdId(String var1) {
            this.w = var1;
            return this;
        }

        public AdSlot.Builder setCreativeId(String var1) {
            this.x = var1;
            return this;
        }

        public AdSlot.Builder setExt(String var1) {
            this.y = var1;
            return this;
        }

        public AdSlot.Builder setIsAutoPlay(boolean var1) {
            this.q = var1;
            return this;
        }

        public AdSlot.Builder setCodeId(String var1) {
            this.a = var1;
            return this;
        }

        public AdSlot.Builder setImageAcceptedSize(int var1, int var2) {
            this.b = var1;
            this.c = var2;
            return this;
        }

        public AdSlot.Builder setExpressViewAcceptedSize(float var1, float var2) {
            this.o = var1;
            this.p = var2;
            return this;
        }

        public AdSlot.Builder setSupportDeepLink(boolean var1) {
            this.d = var1;
            return this;
        }

        public AdSlot.Builder supportRenderControl() {
            this.e = true;
            return this;
        }

        public AdSlot.Builder setAdCount(int var1) {
            if (var1 <= 0) {
                var1 = 1;
                //com.bytedance.sdk.component.utils.k.c("TT_AD_SDK", "setAdCount: adCount must greater than 0 ");
            }

            if (var1 > 20) {
                var1 = 20;
                //com.bytedance.sdk.component.utils.k.c("TT_AD_SDK", "setAdCount: adCount must less than or equal to 20 ");
            }

            this.f = var1;
            return this;
        }

        public AdSlot.Builder setRewardName(String var1) {
            this.g = var1;
            return this;
        }

        public AdSlot.Builder setRewardAmount(int var1) {
            this.h = var1;
            return this;
        }

        public AdSlot.Builder setMediaExtra(String var1) {
            this.i = var1;
            return this;
        }

        public AdSlot.Builder setUserID(String var1) {
            this.j = var1;
            return this;
        }

        public AdSlot.Builder setOrientation(int var1) {
            this.k = var1;
            return this;
        }

        public AdSlot.Builder setNativeAdType(int var1) {
            this.l = var1;
            return this;
        }

        public AdSlot.Builder setAdloadSeq(int var1) {
            this.s = var1;
            return this;
        }

        public AdSlot.Builder setPrimeRit(String var1) {
            this.t = var1;
            return this;
        }

        public AdSlot.Builder setExternalABVid(int... var1) {
            this.r = var1;
            return this;
        }

        public AdSlot.Builder setUserData(String var1) {
            this.v = var1;
            return this;
        }

        public AdSlot.Builder withBid(String var1) {
            if (TextUtils.isEmpty(var1)) {
                return this;
            } else {
               // com.bytedance.sdk.component.utils.k.c("bidding", "AdSlot -> bidAdm=" + com.bytedance.sdk.openadsdk.p.g.b.a(var1));
                this.u = var1;
                return this;
            }
        }

        public AdSlot build() {
            AdSlot var1 = new AdSlot();
            var1.a = this.a;
            var1.f = this.f;
            var1.g = this.d;
            var1.h = this.e;
            var1.b = this.b;
            var1.c = this.c;
            if (this.o <= 0.0F) {
                var1.d = (float)this.b;
                var1.e = (float)this.c;
            } else {
                var1.d = this.o;
                var1.e = this.p;
            }

            var1.i = this.g;
            var1.j = this.h;
            var1.k = this.i;
            var1.l = this.j;
            var1.m = this.k;
            var1.o = this.l;
            var1.q = this.q;
            var1.r = this.r;
            var1.t = this.s;
            var1.u = this.t;
            var1.s = this.m;
            var1.w = this.w;
            var1.x = this.x;
            var1.y = this.y;
            var1.n = this.n;
            var1.v = this.u;
            var1.z = this.v;
            return var1;
        }
    }
}
