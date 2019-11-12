package main;

public class Config {

    //PLAYER
    public static final int Player_HP = 5;
    public static final int Player_Money = 100;

    //Normal GameStage.Enemy
    public static final int NorE_ID = 1;
    public static final int NorE_Health = 25;
    public static final int NorE_Reward = 8;
    public static final double NorE_Speed = 1.6;
    //Tanker GameStage.Enemy
    public static final int TanE_ID = 2;
    public static final int TanE_Health = 40;
    public static final int TanE_Reward = 8;
    public static final double TanE_Speed = 0.9;
    //Smaller GameStage.Enemy
    public static final int SmaE_ID = 3;
    public static final int SmaE_Health = 17;
    public static final int SmaE_Reward = 8;
    public static final double SmaE_Speed = 1.8;
    //Boss GameStage.Enemy
    public static final int BosE_ID = 4;
    public static final int BosE_Health = 60;
    public static final int BosE_Reward = 25;
    public static final double BosE_Speed = 1.3;

    //Normal Tower
    public static final int NorT_ID = 1;
    public static final int NorT_Damage = 6;
    public static final double NorT_Range = 140;
    public static final double NorT_Spaw = 50;   // Tốc độ sinh đạn, số càng lớn càng chậm
    public static final int NorT_Price = 40;
    //Sniper Tower
    public static final int SniT_ID = 2;
    public static final int SniT_Damage = 9;
    public static final double SniT_Range = 200;
    public static final double SniT_Spaw = 70;
    public static final int SniT_Price = 50;
    //MachineGun Tower
    public static final int MacT_ID = 3;
    public static final int MacT_Damage = 5;
    public static final double MacT_Range = 110;
    public static final double MacT_Spaw = 30;
    public static final int MacT_Price = 40;

    //Bullet
    public static final double NorB_Speed = 5;     // Tốc độ bay của đạn, càng lớn càng nhanh
    public static final double SniB_Speed = 12;
    public static final double MacB_Speed = 10;

    //Spawner
    public static final int Spawner_x = 0;
    public static final int Spawner_y = 467;
    //Target
    public static final int Target_x = 1020;
    public static final int Target_y = 440;
}
