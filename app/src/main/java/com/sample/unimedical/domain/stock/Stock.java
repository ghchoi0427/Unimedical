package com.sample.unimedical.domain.stock;

import java.util.HashMap;

public class Stock {

    private String PROD_CD;
    private String BAL_QTY;
    HashMap<String, String> productCodeMap = new HashMap<>();


    public Stock() {

        productCodeMap.put("0001", "TIP CLEANER 5X5 CM [5X5 CM]");
        productCodeMap.put("00013", "칼라캐스트슈즈-XL(대)-280mm(군청색)");
        productCodeMap.put("00014", "칼라캐스트슈즈-L(중)-260mm(하늘색)");
        productCodeMap.put("00015", "칼라캐스트슈즈-M(소)-240mm(녹색)");
        productCodeMap.put("00022", "칼라캐스트슈즈-S(별도사이즈)-220mm(보라색)");
        productCodeMap.put("00026", "QTIVE COHESIVE BANDAGE (쿨밴드 비급여코드 : BK7100UC)");
        productCodeMap.put("00059", "(성인)AMBU Neck collar(넥칼라 비급여코드 :BC1201LD )");
        productCodeMap.put("00060-01", "(성인)Neck collar(넥칼라 비급여코드 :)");
        productCodeMap.put("00060-02", "(유아)Neck collar(넥칼라 비급여코드 :)");
        productCodeMap.put("00060-03", "(회색)오션넥 Neck collar(넥칼라 비급여코드 :)");
        productCodeMap.put("00063", "성심수액필터세트(SFY4-301)");
        productCodeMap.put("00064", "K8103846 ALLARD 단하지 [대]");
        productCodeMap.put("00065", "K8103846 ALLARD 단하지 [중]");
        productCodeMap.put("00066", "K8103846 ALLARD 단하지 [소]");
        productCodeMap.put("00072", "OCEAN-FIX MULTI BRACE-(ALL)(HS-6603) (오션픽스 보조기)");
        productCodeMap.put("00073", "OCEAN-FIX WAIST BRACE(M)(HS-5603-M) (오션픽스 보조기)");
        productCodeMap.put("00074", "OCEAN-FIX WAIST BRACE(L)(HS-5603-L) (오션픽스 보조기)");
        productCodeMap.put("00075", "OCEAN-FIX WAIST BRACE(XL)(HS-5603-XL) (오션픽스 보조기)");
        productCodeMap.put("00077", "OCEAN-FIX-압박아대밴드(발목)-S (비급여코드 : BC1202UC)");
        productCodeMap.put("00078", "OCEAN-FIX-압박아대밴드(발목)-M (비급여코드 : BC1202UC)");
        productCodeMap.put("00079", "OCEAN-FIX-압박아대밴드(발목)-L (비급여코드 : BC1202UC)");
        productCodeMap.put("00081", "OCEAN-FIX-압박아대밴드(손목)-S (비급여코드 : BC1203UC)");
        productCodeMap.put("00082", "OCEAN-FIX-압박아대밴드(손목)-M (비급여코드 : BC1203UC)");
        productCodeMap.put("00083", "OCEAN-FIX-압박아대밴드(손목)-L (비급여코드 : BC1203UC)");
        productCodeMap.put("00089", "OCEANFIX-압박스타킹-종아리형 S (급여코드:K7301514)");
        productCodeMap.put("00090", "OCEANFIX-압박스타킹-종아리형 M (급여코드:K7301514)");
        productCodeMap.put("00091", "OCEANFIX-압박스타킹-종아리형 L (급여코드:K7301514)");
        productCodeMap.put("00092", "OCEANFIX-압박스타킹-종아리형 XL (급여코드:K7301514)");
        productCodeMap.put("00099", "OCEANFIX-압박스타킹-발등형 S (급여코드:K7301514)");
        productCodeMap.put("00100", "OCEANFIX-압박스타킹-발등형 M (급여코드:K7301514)");
        productCodeMap.put("00101", "OCEANFIX-압박스타킹-발등형 L (급여코드:K7301514)");
        productCodeMap.put("00102", "OCEANFIX-압박스타킹-발등형 XL (급여코드:K7301514)");
        productCodeMap.put("00105", "OCEANFIX-압박스타킹-상지(콕업형) S (급여코드:K7301513)");
        productCodeMap.put("00106", "OCEANFIX-압박스타킹-상지(콕업형) M (급여코드:K7301513)");
        productCodeMap.put("00107", "OCEANFIX-압박스타킹-상지(콕업형) L (급여코드:K7301513)");
        productCodeMap.put("00109", "OCEAN-FIX-압박아대밴드(무릎)-S (비급여코드 : BC1202UC)");
        productCodeMap.put("00110", "OCEAN-FIX-압박아대밴드(무릎)-M (비급여코드 : BC1202UC)");
        productCodeMap.put("00111", "OCEAN-FIX-압박아대밴드(무릎)-L (비급여코드 : BC1202UC)");
        productCodeMap.put("00112", "OCEAN-FIX-압박아대밴드(무릎)-XL (비급여코드 : BC1202UC)");
        productCodeMap.put("00113", "OCEAN-FIX-압박아대밴드(팔꿈치)-S (비급여코드 : BC1203UC)");
        productCodeMap.put("00114", "OCEAN-FIX-압박아대밴드(팔꿈치)-M (비급여코드 : BC1203UC)");
        productCodeMap.put("00115", "OCEAN-FIX-압박아대밴드(팔꿈치)-L (비급여코드 : BC1203UC)");
        productCodeMap.put("00118", "OCEAN-FIX 황토면온찜질팩(대) [39*26.5]");
        productCodeMap.put("00119", "OCEAN-FIX 황토면온찜질팩(소) [26*16]");
        productCodeMap.put("00120", "OCEANFIX-압박스타킹-허벅지형 발막힘 S (급여코드:K7301514)");
        productCodeMap.put("00121", "OCEANFIX-압박스타킹-허벅지형 발막힘 M (급여코드:K7301514)");
        productCodeMap.put("00122", "OCEANFIX-압박스타킹-허벅지형 발막힘 L (급여코드:K7301514)");
        productCodeMap.put("00123", "OCEANFIX-압박스타킹-허벅지형 발막힘 XL (급여코드:K7301514)");
        productCodeMap.put("00125", "OCEANFIX-압박스타킹-허벅지형 발등형 S (급여코드:K7301514)");
        productCodeMap.put("00126", "OCEANFIX-압박스타킹-허벅지형 발등형 M (급여코드:K7301514)");
        productCodeMap.put("00127", "OCEANFIX-압박스타킹-허벅지형 발등형 L (급여코드:K7301514)");
        productCodeMap.put("00128", "OCEANFIX-압박스타킹-허벅지형 발등형 XL (급여코드:K7301514)");
        productCodeMap.put("00130", "OCEANFIX-압박스타킹-종아리형 발막힘 S (급여코드:K7301514)");
        productCodeMap.put("00131", "OCEANFIX-압박스타킹-종아리형 발막힘 M (급여코드:K7301514)");
        productCodeMap.put("00132", "OCEANFIX-압박스타킹-종아리형 발막힘 L (급여코드:K7301514)");
        productCodeMap.put("00133", "OCEANFIX-압박스타킹-종아리형 발막힘 XL (급여코드:K7301514)");
        productCodeMap.put("00141", "Balance Gun (밸런스건)");
        productCodeMap.put("3021", "	surgical regular skin marker [T3021]");
        productCodeMap.put("3022", "surgical fine skin marker [T3022]");
        productCodeMap.put("3023", "surgical twin skin marker [T3023]");
        productCodeMap.put("3024", "surgical regular skin marker [T3024]");
        productCodeMap.put("40013401", "[Stryker] full radius 3.4 (40013401) [AQ]");
        productCodeMap.put("40014201", "[Stryker] full radius 4.2 (40014201) [AQ]");
        productCodeMap.put("40015301", "[Stryker] full radius 5.3 (40015301) [AQ]");
        productCodeMap.put("40023401", "[Stryker] clean cut 3.4 (40023401) [AQ]");
        productCodeMap.put("40024201", "[Stryker] clean cut 4.2 (40024201) [AQ]");
        productCodeMap.put("40043401", "[Stryker] fast cut 3.4 (40043401) [AQ]");
        productCodeMap.put("40044201", "[Stryker] fast cut 4.2 (40044201) [AQ]");
        productCodeMap.put("40045301", "[Stryker] fast cut 5.3 (40045301) [AQ]");
        productCodeMap.put("40053401", "[Stryker] new clean cut 3.4 (40053401) [AQ]");
        productCodeMap.put("40054201", "[Stryker] new clean cut 4.2 (40054201) [AQ]");
        productCodeMap.put("40055301", "[Stryker] new clean cut 5.3 (40055301) [AQ]");
        productCodeMap.put("41013401", "[Linvatec] full radius 3.4 (41013401) [AQ]");
        productCodeMap.put("41014201", "[Linvatec] full radius 4.2 (41014201) [AQ]");
        productCodeMap.put("41015301", "[Linvatec] full radius 5.3 (41015301) [AQ]");
        productCodeMap.put("41023401", "[Linvatec] clean cut 3.4 (41023401) [AQ]");
        productCodeMap.put("41024201", "[Linvatec] clean cut 4.2 (41024201) [AQ]");
        productCodeMap.put("41043401", "[Linvatec] fast cut 3.4 (41043401) [AQ]");
        productCodeMap.put("41044201", "[Linvatec] fast cut 4.2 (41044201) [AQ]");
        productCodeMap.put("41045301", "[Linvatec] fast cut 5.3 (41045301) [AQ]");
        productCodeMap.put("41053401", "[Linvatec] new clean cut 3.4 (41053401) [AQ]");
        productCodeMap.put("41054201", "[Linvatec] new clean cut 4.2 (41054201) [AQ]");
        productCodeMap.put("41055301", "[Linvatec] new clean cut 5.3 (41055301) [AQ]");
        productCodeMap.put("42013401", "[Smith&nephew] full radius 3.4 (42013401) [AQ]");
        productCodeMap.put("42014201", "[Smith&nephew] full radius 4.2 (42014201) [AQ]");
        productCodeMap.put("42043401", "[Smith&nephew] fast cut 3.4 (42043401) [AQ]");
        productCodeMap.put("42044201", "[Smith&nephew] fast cut 4.2 (42044201) [AQ]");
        productCodeMap.put("42054201", "[Smith&nephew] new clean cut 4.2 (42054201) [AQ]");
        productCodeMap.put("60035501", "[Stryker] round bur 5.5 (60035501) [AQ]");
        productCodeMap.put("60045501", "[Stryker] oval bur 5.5 (60045501) [AQ]");
        productCodeMap.put("61035501", "[Linvatec] round bur 5.5 (61035501) [AQ]");
        productCodeMap.put("61045501", "[Linvatec] oval bur 5.5 (61045501) [AQ]");
        productCodeMap.put("에코보조기-대우	", "k8102135 ecosplint 단상지 에코(보조기)대 우");
        productCodeMap.put("에코보조기-대좌	", "k8102135 ecosplint 단상지 에코(보조기)대 좌");
        productCodeMap.put("에코보조기-소우	", "k8102135 ecosplint 단상지 에코(보조기)소 우");
        productCodeMap.put("에코보조기-소좌	", "k8102135 ecosplint 단상지 에코(보조기)소 좌");
        productCodeMap.put("에코보조기-중우	", "k8102135 ecosplint 단상지 에코(보조기)중 우");
        productCodeMap.put("에코보조기-중좌	", "k8102135 ecosplint 단상지 에코(보조기)중 좌");
        productCodeMap.put("ECOFINGER[대]", "	k8102035 ECOSPLINT 손가락 [대]");
        productCodeMap.put("ECOFINGER[소]", "	k8102035 ECOSPLINT 손가락 [소]");
        productCodeMap.put("ECOFINGER[중]", "	k8102035 ECOSPLINT 손가락 [중]");
        productCodeMap.put("finger", "QPLINT 손가락 단품");
        productCodeMap.put("K1000-QWLL", "K8103535 QPLINT 단상지 엄지고정 [대 좌]");
        productCodeMap.put("K1000-QWLR", "K8103535 QPLINT 단상지 엄지고정 [대 우]");
        productCodeMap.put("K1000-QWML", "K8103535 QPLINT 단상지 엄지고정 [중 좌]");
        productCodeMap.put("K1000-QWMR", "K8103535 QPLINT 단상지 엄지고정 [중 우]");
        productCodeMap.put("K1000-QWSL", "K8103535 QPLINT 단상지 엄지고정 [소 좌]");
        productCodeMap.put("K1000-QWSR", "K8103535 QPLINT 단상지 엄지고정 [소 우]");
        productCodeMap.put("K1100-QWLL", "K8103535 QPLINT 단상지 엄지자유 [대 좌]");
        productCodeMap.put("K1100-QWLR", "K8103535 QPLINT 단상지 엄지자유 [대 우]");
        productCodeMap.put("K1100-QWML", "K8103535 QPLINT 단상지 엄지자유 [중 좌]");
        productCodeMap.put("K1100-QWMR", "K8103535 QPLINT 단상지 엄지자유 [중 우]");
        productCodeMap.put("K1100-QWSL", "K8103535 QPLINT 단상지 엄지자유 [소 좌]");
        productCodeMap.put("K1100-QWSR", "K8103535 QPLINT 단상지 엄지자유 [소 우]");
        productCodeMap.put("K1110-QWLL", "K8103535 QPLINT 단상지 콕업 [대 좌]");
        productCodeMap.put("K1110-QWLR", "K8103535 QPLINT 단상지 콕업 [대 우]");
        productCodeMap.put("K1110-QWML", "K8103535 QPLINT 단상지 콕업 [중 좌]");
        productCodeMap.put("K1110-QWMR", "K8103535 QPLINT 단상지 콕업 [중 우]");
        productCodeMap.put("K1110-QWSL", "K8103535 QPLINT 단상지 콕업 [소 좌]");
        productCodeMap.put("K1110-QWSR", "K8103535 QPLINT 단상지 콕업 [소 우]");
        productCodeMap.put("K1210-QWLL", "K8103535 QPLINT 단상지 단상지 1 [대 좌]");
        productCodeMap.put("K1210-QWLR", "K8103535 QPLINT 단상지 단상지 1 [대 우]");
        productCodeMap.put("K1210-QWML", "K8103535 QPLINT 단상지 단상지 1 [중 좌]");
        productCodeMap.put("K1210-QWMR", "K8103535 QPLINT 단상지 단상지 1 [중 우]");
        productCodeMap.put("K1210-QWSL", "K8103535 QPLINT 단상지 단상지 1 [소 좌]");
        productCodeMap.put("K1210-QWSR", "K8103535 QPLINT 단상지 단상지 1 [소 우]");
        productCodeMap.put("K1230-QWLL", "K8103535 QPLINT 단상지 얼라 [대 좌]");
        productCodeMap.put("K1230-QWLR", "K8103535 QPLINT 단상지 얼라 [대 우]");
        productCodeMap.put("K1230-QWML", "K8103535 QPLINT 단상지 얼라 [중 좌]");
        productCodeMap.put("K1230-QWMR", "K8103535 QPLINT 단상지 얼라 [중 우]");
        productCodeMap.put("K1230-QWSL", "K8103535 QPLINT 단상지 얼라 [소 좌]");
        productCodeMap.put("K1230-QWSR", "K8103535 QPLINT 단상지 얼라 [소 우]");
        productCodeMap.put("K1240-QWLL", "K8103535 QPLINT 단상지 단상지 2 [대 좌]");
        productCodeMap.put("K1240-QWLR", "K8103535 QPLINT 단상지 단상지 2 [대 우]");
        productCodeMap.put("K1240-QWML", "K8103535 QPLINT 단상지 단상지 2 [중 좌]");
        productCodeMap.put("K1240-QWMR", "K8103535 QPLINT 단상지 단상지 2 [중 우]");
        productCodeMap.put("K1240-QWSL", "K8103535 QPLINT 단상지 단상지 2 [소 좌]");
        productCodeMap.put("K1240-QWSR", "K8103535 QPLINT 단상지 단상지 2 [소 우]");
        productCodeMap.put("K1320-QWL", "K8103535 QPLINT 단상지 손등 [대]");
        productCodeMap.put("K1320-QWM", "K8103535 QPLINT 단상지 손등 [중]");
        productCodeMap.put("K1320-QWS", "K8103535 QPLINT 단상지 손등 [소]");
        productCodeMap.put("K1400-QWLL", "K8103535 QPLINT 단상지 엄지손가락 [대 좌]");
        productCodeMap.put("K1400-QWLR", "K8103535 QPLINT 단상지 엄지손가락 [대 우]");
        productCodeMap.put("K1400-QWML", "K8103535 QPLINT 단상지 엄지손가락 [중 좌]");
        productCodeMap.put("K1400-QWMR", "K8103535 QPLINT 단상지 엄지손가락 [중 우]");
        productCodeMap.put("K1400-QWSL", "K8103535 QPLINT 단상지 엄지손가락 [소 좌]");
        productCodeMap.put("K1400-QWSR", "K8103535 QPLINT 단상지 엄지손가락 [소 우]");
        productCodeMap.put("K2010-QWLL", "K8103335 QPLINT 장상지 장상지 [대 좌]");
        productCodeMap.put("K2010-QWLR", "K8103335 QPLINT 장상지 장상지 [대 우]");
        productCodeMap.put("K2010-QWML", "K8103335 QPLINT 장상지 장상지 [중 좌]");
        productCodeMap.put("K2010-QWMR", "K8103335 QPLINT 장상지 장상지 [중 우]");
        productCodeMap.put("K2010-QWSL", "K8103335 QPLINT 장상지 장상지 [소 좌]");
        productCodeMap.put("K2010-QWSR", "K8103335 QPLINT 장상지 장상지 [소 우]");
        productCodeMap.put("K3010-QWL", "K8103435 QPLINT 단하지 단하지 통합 [대]");
        productCodeMap.put("K3010-QWLL", "K8103435 QPLINT 단하지 단하지 [대 좌]");
        productCodeMap.put("K3010-QWLR", "K8103435 QPLINT 단하지 단하지 [대 우]");
        productCodeMap.put("K3010-QWM", "K8103435 QPLINT 단하지 단하지 통합 [중]");
        productCodeMap.put("K3010-QWML", "K8103435 QPLINT 단하지 단하지 [중 좌]");
        productCodeMap.put("K3010-QWMR", "K8103435 QPLINT 단하지 단하지 [중 우]");
        productCodeMap.put("K3010-QWS", "K8103435 QPLINT 단하지 단하지 통합 [소]");
        productCodeMap.put("K3010-QWSF", "K8103435 QPLINT 단하지 단하지 통합 [기능성 소]");
        productCodeMap.put("K3010-QWSL", "K8103435 QPLINT 단하지 단하지 [소 좌]");
        productCodeMap.put("K4010-QWLL", "K8103235 QPLINT 장하지 장하지 [대 좌]");
        productCodeMap.put("K4010-QWLR", "K8103235 QPLINT 장하지 장하지 [대 우]");
        productCodeMap.put("K4010-QWML", "K8103235 QPLINT 장하지 장하지 [중 좌]");
        productCodeMap.put("K4010-QWMR", "K8103235 QPLINT 장하지 장하지 [중 우]");
        productCodeMap.put("K4010-QWSL", "K8103235 QPLINT 장하지 장하지 [소 좌]");
        productCodeMap.put("K4010-QWSR", "K8103235 QPLINT 장하지 장하지 [소 우]");
        productCodeMap.put("K4020-QWLL", "K8103235 QPLINT 장하지 실린더 [대 좌]");
        productCodeMap.put("K4020-QWLR", "K8103235 QPLINT 장하지 실린더 [대 우]");
        productCodeMap.put("K4020-QWML", "K8103235 QPLINT 장하지 실린더 [중 좌]");
        productCodeMap.put("K4020-QWMR", "K8103235 QPLINT 장하지 실린더 [중 우]");
        productCodeMap.put("K4020-QWSL", "K8103235 QPLINT 장하지 실린더 [소 좌]");
        productCodeMap.put("K4020-QWSR", "K8103235 QPLINT 장하지 실린더 [소 우]");


    }

    public HashMap<String, String> getProductCodeMap() {
        return productCodeMap;
    }

    public String getPROD_CD() {
        return PROD_CD;
    }

    public void setPROD_CD(String PROD_CD) {
        this.PROD_CD = PROD_CD;
    }

    public String getBAL_QTY() {
        return BAL_QTY;
    }

    public void setBAL_QTY(String BAL_QTY) {
        this.BAL_QTY = BAL_QTY;
    }
}
