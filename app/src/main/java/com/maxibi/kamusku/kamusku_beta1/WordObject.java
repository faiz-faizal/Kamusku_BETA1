package com.maxibi.kamusku.kamusku_beta1;

/**
 * Created by User on 8/8/2017.
 */

public class WordObject {
    String wordMS, wordEN;


    //Declare constructor for wordobject class
    public WordObject(String wordMS, String wordEN){
        this.wordMS = wordMS;
        this.wordEN = wordEN;
    }

    //getter mehod for both text
    public String getWordBM(){ return wordMS;}
    public String getWordBI(){ return wordEN;}

}
