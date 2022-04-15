package edu.up.cs301.SeasonsHigh;

import static java.lang.Integer.valueOf;

/**
 * TODO: comment class
 */
public class Card {

    private static final long serialVersionUID = 99L;

    //instantiate card value and suit
    private char cardRank;
    private char cardSuit;
    private boolean isDealt;
    private boolean isSelected; //for the Draw-Phase

    /**
     *
     * @param rank a string of a number between 2 and 10 or a "Ace", "Jack", "Queen", and "King"
     * @param suit
     */
    public Card(char rank, char suit){
        //declare value and suit
        this.cardRank = rank;
        this.cardSuit = suit;
        //set to false by default
        this.isDealt = false;
        this.isSelected = false;
    }

    //copy constructor
    public Card(Card orig){
        this.cardRank = orig.cardRank;
        this.cardSuit = orig.cardSuit;
    }

    /**
     * turns the string cardRank variable into a int
     * @return the int value of the rank of the card
     */
    public int getValue(){
        if(this.cardRank == 'c') {
            return 0;
        } else if(this.cardRank == 'A'){
            return 1;
        } else if(this.cardRank == 'T') {
            return 10;
        } else if(this.cardRank == 'J'){
            return 11;
        } else if(this.cardRank == 'Q'){
            return 12;
        } else if(this.cardRank == 'K'){
            return 13;
        } else {
            return valueOf(this.cardRank);
        }
    }

    public char getSuit(){ return this.cardSuit; }

    public char getRank(){ return this.cardRank; }

    //suit/rank setters are omitted because they will not need to be changed

    public boolean getIsSelected(){ return this.isSelected; }

    public boolean getIsDealt(){ return this.isDealt; }

    public void setIsDealt(boolean f){ this.isDealt = f; }

    @Override
    public String toString(){
        return this.cardRank + "" + this.cardSuit;
    }

}
