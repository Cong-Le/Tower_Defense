package main;

import javafx.scene.Scene;

public class Config {

    //PLAYER
    public static final int Player_HP = 5;
    public static final int Player_Money = 100;

    //Normal GameStage.Enemy
    public static final int NorE_ID = 1;
    public static final int NorE_Health = 30;
    public static final int NorE_Reward = 10;
    public static final double NorE_Speed = 1.2;
    //Tanker GameStage.Enemy
    public static final int TanE_ID = 2;
    public static final int TanE_Health = 40;
    public static final int TanE_Reward = 10;
    public static final double TanE_Speed = 0.6;
    //Smaller GameStage.Enemy
    public static final int SmaE_ID = 3;
    public static final int SmaE_Health = 20;
    public static final int SmaE_Reward = 10;
    public static final double SmaE_Speed = 1.4;
    //Boss GameStage.Enemy
    public static final int BosE_ID = 4;
    public static final int BosE_Health = 60;
    public static final int BosE_Reward = 30;
    public static final double BosE_Speed = 1;

    //Normal Tower
    public static final int NorT_ID = 1;
    public static final int NorT_Damage = 5;
    public static final double NorT_Range = 150;
    public static final double NorT_Spaw = 90;   // Tốc độ sinh đạn, số càng lớn càng chậm
    public static final int NorT_Price = 40;
    //Sniper Tower
    public static final int SniT_ID = 2;
    public static final int SniT_Damage = 7;
    public static final double SniT_Range = 180;
    public static final double SniT_Spaw = 100;
    public static final int SniT_Price = 50;
    //MachineGun Tower
    public static final int MacT_ID = 3;
    public static final int MacT_Damage = 5;
    public static final double MacT_Range = 100;
    public static final double MacT_Spaw = 80;
    public static final int MacT_Price = 40;

    //Bullet
    public static final double NorB_Speed = 5;     // Tốc độ bay của đạn, càng lớn càng nhanh
    public static final double SniB_Speed = 23;
    public static final double MacB_Speed = 15;

    //Spawner
    public static final int Spawner_x = 0;
    public static final int Spawner_y = 467;
    //Target
    public static final int Target_x = 1020;
    public static final int Target_y = 440;
}
