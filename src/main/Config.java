package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class Config {

    //PLAYER
    public static final int Player_HP = 5;
    public static final int Player_Money = 150;

    //Normal GameStage.Enemy
    public static final int NorE_ID = 1;
    public static final int NorE_Health = 35;
    public static final int NorE_Reward = 5;
    public static final int NorE_Speed = 2;
    //Tanker GameStage.Enemy
    public static final int TanE_ID = 2;
    public static final int TanE_Health = 50;
    public static final int TanE_Reward = 5;
    public static final int TanE_Speed = 2;
    //Smaller GameStage.Enemy
    public static final int SmaE_ID = 3;
    public static final int SmaE_Health = 25;
    public static final int SmaE_Reward = 5;
    public static final int SmaE_Speed = 3;
    //Boss GameStage.Enemy
    public static final int BosE_ID = 4;
    public static final int BosE_Health = 150;
    public static final int BosE_Reward = 15;
    public static final int BosE_Speed = 2;

    //Normal Tower
    public static final int NorT_ID = 1;
    public static final int NorT_Damage = 5;
    public static final double NorT_Range = 180;
    public static final double NorT_Spaw = 25;   // Tốc độ sinh đạn, số càng lớn càng chậm
    public static final int NorT_Price = 40;
    //Sniper Tower
    public static final int SniT_ID = 2;
    public static final int SniT_Damage = 7;
    public static final double SniT_Range = 250;
    public static final double SniT_Spaw = 35;
    public static final int SniT_Price = 50;
    //MachineGun Tower
    public static final int MacT_ID = 3;
    public static final int MacT_Damage = 2;
    public static final double MacT_Range = 140;
    public static final double MacT_Spaw = 20;
    public static final int MacT_Price = 50;

    //Bullet
    public static final double NorB_Speed = 25;     // Tốc độ bay của đạn, càng lớn càng nhanh
    public static final double SniB_Speed = 25;
    public static final double MacB_Speed = 20;

    //Spawner
    public static final int Spawner_x = 0;
    public static final int Spawner_y = 467;
    //Target
    public static final int Target_x = 1020;
    public static final int Target_y = 440;

    //Sound


}
