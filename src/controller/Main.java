package controller;

import view.MainGUIFrame;

/**
 * Created by mzglinicki.96 on 17.05.2016.
 */
public class Main {

    public static void main(String[] args) {
        MainGUIFrame.getInstance();
        ButtonManager.getInstance();
    }
}