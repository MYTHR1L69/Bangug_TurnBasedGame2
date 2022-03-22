package mcm.edu.ph.bangug_turnbasedgame2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtPlayerName, txtPlayerHP, txtPlayerMP, txtEnemyName, txtEnemyHP, txtLog, txtTurn, txtTurnLog;
    ImageButton btnTurn, btnSkill1, btnSkill2;

    int turnNum = 1;
    int skill1CD = 0;
    int skill2CD =0;

    int burnDuration = 0;

    String playerName = "noobmaster69";
    int playerHP = 2000;
    int playerMP = 200;
    int playerMinDMG = 120;
    int playerMaxDMG = 180;

    String enemyName = "TONDO MAN";
    int enemyHP = 2000;
    int enemyMinDMG = 120;
    int enemyMaxDMG = 170;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLog = findViewById(R.id.txtLog);
        txtTurn = findViewById(R.id.txtTurn);
        txtTurnLog = findViewById(R.id.txtTurnLog);

        txtPlayerName = findViewById(R.id.txtPlayerName);
        txtPlayerHP = findViewById(R.id.txtPlayerHP);
        txtPlayerMP = findViewById(R.id.txtPlayerMP);

        txtEnemyName = findViewById(R.id.txtEnemyName);
        txtEnemyHP = findViewById(R.id.txtEnemyHP);

        txtPlayerName.setText(playerName);
        txtPlayerHP.setText(String.valueOf(playerHP));
        txtPlayerMP.setText(String.valueOf(playerMP));

        txtEnemyName.setText(enemyName);
        txtEnemyHP.setText(String.valueOf(enemyHP));

        btnTurn = findViewById(R.id.btnTurn);
        btnSkill1 = findViewById(R.id.btnSkill1);
        btnSkill2 = findViewById(R.id.btnSkill2);

        btnTurn.setOnClickListener(this);
        btnSkill1.setOnClickListener(this);
        btnSkill2.setOnClickListener(this);
    }

    private void skillCD(){
        if (skill1CD > 0){
            skill1CD--;
        }
        if (skill2CD > 0) {
            skill2CD--;
        }
    }

    private void resetGame(){
        turnNum = 1;
        skill1CD = 0;
        skill2CD = 0;
        enemyHP = 2000;
        playerHP = 2000;
        playerMP = 200;

        txtTurnLog.setText("Turn ("+ turnNum +")");
        txtTurn.setText("Reset game");
        txtPlayerHP.setText(String.valueOf(playerHP));
        txtPlayerMP.setText(String.valueOf(playerMP));
        txtEnemyHP.setText(String.valueOf(enemyHP));
    }

    @Override
    public void onClick(View v) {

        if (turnNum % 2 == 1){
            btnSkill1.setEnabled(false);
            btnSkill2.setEnabled(false);
        }

        else if (turnNum % 2 == 0){
            if (skill1CD > 0){
                btnSkill1.setEnabled(false);
            }
            else if (skill1CD == 0){
                btnSkill1.setEnabled(true);
            }
            if (skill2CD > 0){
                btnSkill2.setEnabled(false);
            }
            else if (skill2CD == 0){
                btnSkill2.setEnabled(true);
            }
        }

        if (turnNum % 2 != 1 && burnDuration > 0){
            enemyHP -= 100;
            burnDuration--;
        }

        Random randomizer = new Random();
        int playerDPT = randomizer.nextInt(playerMaxDMG - playerMinDMG) + playerMinDMG;
        int enemyDPT = randomizer.nextInt(enemyMaxDMG - enemyMinDMG) + enemyMinDMG;

        switch (v.getId()) {
            case R.id.btnSkill1:
                if (playerMP > 80) {
                    playerMP -= 80;
                    skill1CD = 10;
                    burnDuration = 5;
                    enemyHP -= 50;
                    turnNum++;
                    txtTurnLog.setText("Turn ("+ turnNum +")");
                    txtPlayerMP.setText(String.valueOf(playerMP));
                    txtEnemyHP.setText(String.valueOf(enemyHP));
                    txtLog.setText(""+playerName+" used burn! "+enemyName+" will \nbe burned for "+burnDuration+" turns!");
                    txtTurn.setText("Enemy's \nturn");
                } else {
                    txtLog.setText("Cannot use skill. Your mana is insufficient.");
                }

                if (enemyHP < 0) {
                    resetGame();
                    txtLog.setText(""+playerName+" is too powerful!");
                }
                break;

            case R.id.btnSkill2:
                if (playerMP > 50) {
                    playerMP -= 50;
                    skill2CD = 8;
                    enemyHP -= 200;
                    playerHP += 90;
                    turnNum++;
                    txtTurnLog.setText("Turn ("+ turnNum +")");
                    txtPlayerHP.setText(String.valueOf(playerHP));
                    txtPlayerMP.setText(String.valueOf(playerMP));
                    txtEnemyHP.setText(String.valueOf(enemyHP));
                    txtLog.setText(""+playerName+" used lifesteal! He dealt \n200 damage to "+enemyName+" and gained 90 HP.");
                    txtTurn.setText("Enemy's \nturn");
                } else {
                    txtLog.setText("Cannot use skill. Your mana is insufficient.");
                }

                if (enemyHP < 0) {
                    resetGame();
                    txtLog.setText(""+playerName+" dealt the finishing blow!");
                }
                break;

            case R.id.btnTurn:
                if (turnNum % 2 == 1) {
                    enemyHP = enemyHP - playerDPT;
                    turnNum++;
                    txtTurnLog.setText("Turn ("+ turnNum +")");
                    txtEnemyHP.setText(String.valueOf(enemyHP));
                    txtLog.setText(""+playerName+" dealt "+playerDPT+" damage \nto "+enemyName+"!");
                    txtTurn.setText("Enemy's \nturn");
                    if (enemyHP < 0) {
                        resetGame();
                        txtLog.setText(""+playerName+" dealt "+playerDPT+" damage and killed\n"+enemyName+"! He is victorious!");
                    }
                }

                else if (turnNum % 2 != 1) {
                    playerHP = playerHP - enemyDPT;
                    turnNum++;
                    txtTurnLog.setText("Turn ("+ turnNum +")");
                    txtPlayerHP.setText(String.valueOf(playerHP));
                    txtLog.setText(""+enemyName+" dealt "+enemyDPT+" damage \nto "+playerName+"!");
                    txtTurn.setText("Attack");
                    if (playerHP < 0) {
                        resetGame();
                        txtLog.setText(""+enemyName+" kills "+playerName+"!\n LMAOO U A LOSER");
                    }
                }
                skillCD();
                break;
        }
    }
}