package com.ea.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Source: https://www.youtube.com/channel/UC_Fh8kvtkVPkeihBs42jGcA

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private int player1Points = 0;
    private int player2Points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            roundCount = savedInstanceState.getInt("roundCount");
//            player1Points = savedInstanceState.getInt("player1Points");
//            player2Points = savedInstanceState.getInt("player2Points");
//            player1Turn = savedInstanceState.getBoolean("player1Turn");
//        }

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i=0; i<3; i++){
            for (int j = 0; j<3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

                // Link button views to array positions
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener((View.OnClickListener) this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                player1Points = 0;
                player2Points = 0;
                UpdatePointsText();
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // check if there is already an 'x' or 'o' in the clicked button
        if(!((Button) v).getText().toString().equals("")){
            return;
        }

        if (player1Turn){
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()){
            if (player1Turn){
                // player1 won
                player1Wins();
            }else{
                //player2 won
                player2Wins();
            }
        }else if(roundCount == 9){
            // draw - all rounds played
            draw();
        }else{
            // if no one won yet or no draw yet - change players turn
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i<3; i++){
            // check is 3 same vertical and check its net empty
            if(field[i][0].equals(field[i][1])
                && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;
            }
        }

        for (int i = 0; i<3; i++){
            // check is 3 same horizontal and check its net empty
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }

        // check is 3 same diagonal
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        UpdatePointsText();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        UpdatePointsText();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void UpdatePointsText(){
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);

        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}