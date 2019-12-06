public class Constant {

	public static int userLifes_default = 20;// player default life
	public static int npcLifes_default = 20;// NPC default life
	public static int fireball_killLifes_default = 5;// Fire default damage
	public static int snowball_killLifes_default = 2;// Water Cannon default damage
	public static int lightning_killLifes_default = 1;// lightning default damage
	public static int crystal_killLifes_default = 0;// Ice arrow default damage

	public static int user_shooting_timeout_default = 2600;// player default shout time
	public static int npc_shooting_timeout_default = 5000;// NPC default shout time

	public static int npc_show_keypresstimes_default = 3;// number you press f
	
	public Constant(){
		String[] values = FileReader.getTxtFile("config.txt", "").split("|");
		fireball_killLifes_default = Integer.parseInt(values[0]);
		snowball_killLifes_default = Integer.parseInt(values[1]);
		lightning_killLifes_default = Integer.parseInt(values[2]);
		crystal_killLifes_default = Integer.parseInt(values[3]);
	}
}
