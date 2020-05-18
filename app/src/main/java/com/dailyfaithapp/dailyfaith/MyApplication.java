package com.dailyfaithapp.dailyfaith;

import android.app.Application;

import com.dailyfaithapp.dailyfaith.Model.Quotes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyApplication extends Application {
    public FirebaseFirestore db;
    public ArrayList<Quotes> myGlobalArray = null;
    public ArrayList<String> themeFontsList = new ArrayList<>();

    public MyApplication() {
        myGlobalArray = new ArrayList();

    }

    public ArrayList<String> setThemeFonts() {

        String alexBrush = "AlexBrush-Regular.ttf";
        themeFontsList.add(alexBrush);
        String alwaysInMyHeart = "Always In My Heart.ttf";
        themeFontsList.add(alwaysInMyHeart);
        String amaticSc = "AmaticSc-Regular.ttf";
        themeFontsList.add(amaticSc);
        String antonioLight = "Antonio-Light.ttf";
        themeFontsList.add(antonioLight);
        String bebasNeue = "BebasNeue-Regular.ttf";
        themeFontsList.add(bebasNeue);
        String dancingScript = "DancingScript-Regular.ttf";
        themeFontsList.add(dancingScript);
        String dreamorphans = "dreamorphans-i-.ttf";
        themeFontsList.add(dreamorphans);
        String greatVibes = "GreatVibes-Regular.ttf";
        themeFontsList.add(greatVibes);
        String helveticaNeue = "HelveticaNeue Thin.ttf";
        themeFontsList.add(helveticaNeue);
        String helveticaNeueIt = "HelveticaNeueIt.ttf";
        themeFontsList.add(helveticaNeueIt);
        String kaushanScript = "KaushanScript-Regular.otf";
        themeFontsList.add(kaushanScript);
        String latoHairline = "Lato-Hairline.ttf";
        themeFontsList.add(latoHairline);
        String latoLight = "Lato-Light.ttf";
        themeFontsList.add(latoLight);
        String latoLightItalic = "Lato-LightItalic.ttf";
        themeFontsList.add(latoLightItalic);
        String mrf = "MRF LemonberrySans.otf";
        themeFontsList.add(mrf);
        String openSansCondBold = "OpenSans-CondBold.ttf";
        themeFontsList.add(openSansCondBold);
        String openSansCondLight = "OpenSans-CondLight.ttf";
        themeFontsList.add(openSansCondLight);
        String openSansCondLightItalic = "OpenSans-CondLightItalic.ttf";
        themeFontsList.add(openSansCondLightItalic);
        String openSansSemiBold = "OpenSans-Semibold.ttf";
        themeFontsList.add(openSansSemiBold);
        String openSansLight = "OpenSans-Light.ttf";
        themeFontsList.add(openSansLight);
        String openSansLightItalic = "OpenSans-LightItalic.ttf";
        themeFontsList.add(openSansLightItalic);
        String oswaldLight = "Oswald-Light.ttf";
        themeFontsList.add(oswaldLight);
        String playball = "Playball.ttf";
        themeFontsList.add(playball);
        String quickSandBold = "Quicksand-Bold.ttf";
        themeFontsList.add(quickSandBold);
        String quickSandLight = "QuickSand-Light.ttf";
        themeFontsList.add(quickSandLight);
        String ralewayLight = "Raleway-Light.ttf";
        themeFontsList.add(ralewayLight);
        String robotoMedium = "Roboto-Medium.ttf";
        themeFontsList.add(robotoMedium);
        String robotThin = "Roboto-Thin.ttf";
        themeFontsList.add(robotThin);
        String skinnyJeans = "SkinnyJeans.ttf";
        themeFontsList.add(skinnyJeans);

        return themeFontsList;
    }
}