package com.uniqueomokenny.connect3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;  // Importing import android.widget.GridLayout; --> Crashes my App, Why??????????
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = red, 1 = yellow
    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedConter = Integer.parseInt(counter.getTag().toString());

        // Finite number of winning positions
        int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

        if (gameState[tappedConter] == 2 && gameIsActive) {

            gameState[tappedConter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotationY(360f).setDuration(500);

            // Checking if any of the player has reach the winning positions i.e Someone has won
            for (int[] winningPosition: winningPositions){
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){
                    System.out.println(gameState[winningPosition[0]]);

                    // Turning off the game active
                    gameIsActive = false;

                    // setting the winner
                    String winner = "Yellow";
                    if (gameState[winningPosition[0]] == 0){
                        winner = "Red";
                    }

                    // Displaying some has won with the Linearlayout
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won");
                    // Setting the LinerLayout visible
                    LinearLayout layout = findViewById(R.id.playAgainLayout);
                    layout.setVisibility(view.VISIBLE);

                }
                // Hence, it's a draw
                else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState){
                        if (counterState == 2)
                            gameIsOver = false;
                    }
                    if (gameIsOver){
                        // Displaying some has won with the Linearlayout
                        TextView winnerMessage = findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw!");
                        // Setting the LinerLayout visible
                        LinearLayout layout = findViewById(R.id.playAgainLayout);
                        layout.setVisibility(view.VISIBLE);
                    }

                }
            }
        }
    }

    public void playAgain(View view){
        // Putting back gameIsActive
        gameIsActive = true;

        // Setting the LinerLayout invisible this time
        LinearLayout layout = findViewById(R.id.playAgainLayout);
        layout.setVisibility(view.INVISIBLE);

        // Initializing gamestate to their initials
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        // Setting the Grid Layout to blank
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
