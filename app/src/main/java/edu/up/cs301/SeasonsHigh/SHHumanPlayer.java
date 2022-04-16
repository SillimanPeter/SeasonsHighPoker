package edu.up.cs301.SeasonsHigh;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.game.R;


/**
 * A GUI for a human to play Pig. This default version displays the GUI but is incomplete
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class SHHumanPlayer extends GameHumanPlayer implements View.OnClickListener {

    /* instance variables */

    // These variables will reference widgets that will be modified during play
    private ImageView foe0Card1;
    private ImageView foe0Card2;
    private ImageView foe0Card3;
    private ImageView foe0Card4;
    private ImageView foe1Card1;
    private ImageView foe1Card2;
    private ImageView foe1Card3;
    private ImageView foe1Card4;

    private TextView userBal;
    private TextView potAmount;
    private TextView currentBet;
    private TextView foe0Bal;
    private TextView foe1Bal;
    private TextView userName;
    private TextView foe0Name;
    private TextView foe1Name;
    private TextView infoSection;
    private TextView betValue;

    private Button holdButt;
    private Button foldButt;
    private Button drawButt;
    private Button betButt;

    private ImageButton userCard0;
    private ImageButton userCard1;
    private ImageButton userCard2;
    private ImageButton userCard3;

    private EditText userBetAmount;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public SHHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() { return this.myActivity.findViewById(R.id.Main_Layout); }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @caveat
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        //set gui
        if(info instanceof SHState){
            SHState shgs = (SHState)info;
            this.infoSection.setText("Let's play a game! \n");
            //determine human player, set TextViews accordingly
            if(super.playerNum == 0) {
                //sets balances
                this.userBal.setText("" + shgs.getPBal(0));
                this.foe0Bal.setText("" + shgs.getPBal(1));
                this.foe1Bal.setText("" + shgs.getPBal(2));
                //sets names
                this.userName.setText("" + shgs.getPName(0));
                this.foe0Name.setText("" + shgs.getPName(1));
                this.foe1Name.setText("" + shgs.getPName(2));
                //sets user's cards
                this.userCard0.setImageResource(shgs.getPCardRecId(0, 0));
                this.userCard1.setImageResource(shgs.getPCardRecId(0, 1));
                this.userCard2.setImageResource(shgs.getPCardRecId(0, 2));
                this.userCard3.setImageResource(shgs.getPCardRecId(0, 3));
                //sets foe0's cards
                this.foe0Card4.setImageResource(shgs.getPCardRecId(1, 0));
                this.foe0Card1.setImageResource(shgs.getPCardRecId(1, 1));
                this.foe0Card2.setImageResource(shgs.getPCardRecId(1, 2));
                this.foe0Card3.setImageResource(shgs.getPCardRecId(1, 3));
                //sets foe1's cards
                this.foe1Card4.setImageResource(shgs.getPCardRecId(2, 0));
                this.foe1Card1.setImageResource(shgs.getPCardRecId(2, 1));
                this.foe1Card2.setImageResource(shgs.getPCardRecId(2, 2));
                this.foe1Card3.setImageResource(shgs.getPCardRecId(2, 3));
            } else if(super.playerNum == 1){
                //sets balances
                this.userBal.setText("" + shgs.getPBal(1));
                this.foe0Bal.setText("" + shgs.getPBal(0));
                this.foe1Bal.setText("" + shgs.getPBal(2));
                //sets names
                this.userName.setText("" + shgs.getPName(1));
                this.foe0Name.setText("" + shgs.getPName(0));
                this.foe1Name.setText("" + shgs.getPName(2));
                //sets user's cards
                this.userCard0.setImageResource(shgs.getPCardRecId(1, 0));
                this.userCard1.setImageResource(shgs.getPCardRecId(1, 1));
                this.userCard2.setImageResource(shgs.getPCardRecId(1, 2));
                this.userCard3.setImageResource(shgs.getPCardRecId(1, 3));
                //sets foe0's cards
                this.foe0Card4.setImageResource(shgs.getPCardRecId(0, 0));
                this.foe0Card1.setImageResource(shgs.getPCardRecId(0, 1));
                this.foe0Card2.setImageResource(shgs.getPCardRecId(0, 2));
                this.foe0Card3.setImageResource(shgs.getPCardRecId(0, 3));
                //sets foe1's cards
                this.foe1Card4.setImageResource(shgs.getPCardRecId(2, 0));
                this.foe1Card1.setImageResource(shgs.getPCardRecId(2, 1));
                this.foe1Card2.setImageResource(shgs.getPCardRecId(2, 2));
                this.foe1Card3.setImageResource(shgs.getPCardRecId(2, 3));
            } else {
                //sets balances
                this.userBal.setText("" + shgs.getPBal(2));
                this.foe0Bal.setText("" + shgs.getPBal(1));
                this.foe1Bal.setText("" + shgs.getPBal(0));
                //sets names
                this.userName.setText("" + shgs.getPName(2));
                this.foe0Name.setText("" + shgs.getPName(1));
                this.foe1Name.setText("" + shgs.getPName(0));
                //sets user's cards
                this.userCard0.setImageResource(shgs.getPCardRecId(2, 0));
                this.userCard1.setImageResource(shgs.getPCardRecId(2, 1));
                this.userCard2.setImageResource(shgs.getPCardRecId(2, 2));
                this.userCard3.setImageResource(shgs.getPCardRecId(2, 3));
                //sets foe0's cards
                this.foe0Card4.setImageResource(shgs.getPCardRecId(1, 0));
                this.foe0Card1.setImageResource(shgs.getPCardRecId(1, 1));
                this.foe0Card2.setImageResource(shgs.getPCardRecId(1, 2));
                this.foe0Card3.setImageResource(shgs.getPCardRecId(1, 3));
                //sets foe1's cards
                this.foe1Card4.setImageResource(shgs.getPCardRecId(0, 0));
                this.foe1Card1.setImageResource(shgs.getPCardRecId(0, 1));
                this.foe1Card2.setImageResource(shgs.getPCardRecId(0, 2));
                this.foe1Card3.setImageResource(shgs.getPCardRecId(0, 3));
            }/* TODO: issue - establishes the correct user but not other players
                                (i.e. non-user players' may be switched in order)*/
            //sets current pot
            this.potAmount.setText("" + shgs.getPotBalance());
            //sets current bet
            this.currentBet.setText("" + shgs.getCurrentBet());

        } else { super.flash(Color.RED, 20); } //send error flash (is not PigGameState)
    }//receiveInfo

    /**
     * this method gets called when the user clicks their card or a action button. It
     * creates a new SHAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    @Override
    public void onClick(View button) {
        if(button instanceof ImageButton) {
            if(button.getId() == this.userCard0.getId()){
                this.game.sendAction(new SHActionCard0Select(this));
                Log.d("Human sendAction", "Card0Select Action");
                this.infoSection.append("Card 0 has been clicked \n");
            } else if(button.getId() == this.userCard1.getId()){
                this.game.sendAction(new SHActionCard1Select(this));
                Log.d("Human sendAction", "Card1Select Action");
                this.infoSection.append("Card 1 has been clicked \n");
            } else if(button.getId() == this.userCard2.getId()){
                this.game.sendAction(new SHActionCard2Select(this));
                Log.d("Human sendAction", "Card2Select Action");
                this.infoSection.append("Card 2 has been clicked \n");
            } else if(button.getId() == this.userCard3.getId()){
                this.game.sendAction(new SHActionCard3Select(this));
                Log.d("Human sendAction", "Card3Select Action");
                this.infoSection.append("Card 3 has been clicked \n");
            }
        } else if(button instanceof Button){
            if(button.getId() == this.betButt.getId()){
                super.game.sendAction(new SHActionBet(this));
                Log.d("Human sendAction", "Bet Action");
                this.currentBet.setText(""+getBetAmount());
                this.potAmount.setText("" + getBetAmount());
                int bal = Integer.parseInt(""+userBal.getText());
                int newBal = bal - getBetAmount();
                this.userBal.setText("" + newBal);
                this.infoSection.append(this.name + " bet " + getBetAmount() + "\n");

            } else if(button.getId() == this.holdButt.getId()){
                super.game.sendAction(new SHActionHold(this));
                Log.d("Human sendAction", "Hold Action");
                this.infoSection.append("Hold button clicked \n");
            } else if(button.getId() == this.drawButt.getId()){
                super.game.sendAction(new SHActionDraw(this));
                Log.d("Human sendAction", "Draw Action");
                this.infoSection.append("Draw button clicked \n");
            } else if(button.getId() == foldButt.getId()){
                super.game.sendAction(new SHActionFold(this));
                Log.d("Human sendAction", "Fold Action");
                this.infoSection.append("Fold button clicked \n");
            }
        }
    }// onClick

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        this.myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.sh_human_player);

        /**Initialize the widget reference member variables*/

        //user info
        this.userName = (TextView)activity.findViewById(R.id.userTV);
        this.userBal = (TextView)activity.findViewById(R.id.userBalanceTV);
        this.userBetAmount = (EditText)activity.findViewById(R.id.betAmountET);
        this.userCard0 = (ImageButton)activity.findViewById(R.id.userCard0IB);
        this.userCard1 = (ImageButton)activity.findViewById(R.id.userCard1IB);
        this.userCard2 = (ImageButton)activity.findViewById(R.id.userCard2IB);
        this.userCard3 = (ImageButton)activity.findViewById(R.id.userCard3IB);
        this.infoSection = (TextView)activity.findViewById(R.id.infoSectionTV);
        this.betValue = (TextView)activity.findViewById(R.id.betAmountET);

        //user buttons
        this.betButt = (Button)activity.findViewById(R.id.betButton);
        this.holdButt = (Button)activity.findViewById(R.id.holdButton);
        this.drawButt = (Button)activity.findViewById(R.id.drawButton);
        this.foldButt = (Button)activity.findViewById(R.id.foldButton);

        //foe 0 info
        this.foe0Name = (TextView)activity.findViewById(R.id.opponent0TV);
        this.foe0Bal = (TextView)activity.findViewById(R.id.opponent0BalanceNumTV);
        this.foe0Card1 = (ImageView)activity.findViewById(R.id.opponent0Card1);
        this.foe0Card2 = (ImageView)activity.findViewById(R.id.opponent0Card2);
        this.foe0Card3 = (ImageView)activity.findViewById(R.id.opponent0Card3);
        this.foe0Card4 = (ImageView)activity.findViewById(R.id.opponent0Card4);

        //foe 1 info
        this.foe1Name = (TextView)activity.findViewById(R.id.opponent1TV);
        this.foe1Bal = (TextView)activity.findViewById(R.id.opponent1BalanceNumTV);
        this.foe1Card1 = (ImageView)activity.findViewById(R.id.opponent1Card1);
        this.foe1Card2 = (ImageView)activity.findViewById(R.id.opponent1Card2);
        this.foe1Card3 = (ImageView)activity.findViewById(R.id.opponent1Card3);
        this.foe1Card4 = (ImageView)activity.findViewById(R.id.opponent1Card4);

        //game info
        this.potAmount = (TextView)activity.findViewById(R.id.potAmountTV);
        this.currentBet = (TextView)activity.findViewById(R.id.currentBetTV);

        //Listen for button/ImageButton presses
        this.betButt.setOnClickListener(this);
        this.holdButt.setOnClickListener(this);
        this.drawButt.setOnClickListener(this);
        this.foldButt.setOnClickListener(this);

        this.userCard0.setOnClickListener(this);
        this.userCard1.setOnClickListener(this);
        this.userCard2.setOnClickListener(this);
        this.userCard3.setOnClickListener(this);



    }//setAsGui

    public int getBetAmount(){return Integer.parseInt("" + betValue.getText());}

}// class SHHumanPlayer