package complex11.theheroic.init;

import java.util.ArrayList;
import java.util.List;

import complex11.theheroic.items.ItemBase;
import complex11.theheroic.items.misc.RealityFragment;
import complex11.theheroic.items.misc.ShardOfCreation;
import complex11.theheroic.items.weapons.Aclass.BulwarksHallmark;
import complex11.theheroic.items.weapons.Aclass.DeathMark;
import complex11.theheroic.items.weapons.Aclass.FlashBlade;
import complex11.theheroic.items.weapons.Aclass.HellfireBlaze;
import complex11.theheroic.items.weapons.Aclass.HermesBlade;
import complex11.theheroic.items.weapons.Aclass.RepeatingBlade;
import complex11.theheroic.items.weapons.Aclass.StaffOfEnd;
import complex11.theheroic.items.weapons.Bclass.Burster;
import complex11.theheroic.items.weapons.Bclass.FlameWaker;
import complex11.theheroic.items.weapons.Bclass.FruitNinja;
import complex11.theheroic.items.weapons.Bclass.StreamsOfPain;
import complex11.theheroic.items.weapons.Bclass.Taskmaster;
import complex11.theheroic.items.weapons.Cclass.BladeOfMinorWounds;
import complex11.theheroic.items.weapons.Cclass.GlassyEdge;
import complex11.theheroic.items.weapons.Cclass.NatureSlayer;
import complex11.theheroic.items.weapons.Cclass.SpecialShock;
import complex11.theheroic.items.weapons.Cclass.SpeedsterSword;
import complex11.theheroic.items.weapons.Cclass.ToughGuy;
import complex11.theheroic.items.weapons.Sclass.HeavenlySmite;
import complex11.theheroic.items.weapons.Sclass.RealityBlade;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final ToolMaterial MATERIAL_FLASHSTEEL = EnumHelper.addToolMaterial("material_flashsteel", 4, 7000, 7.0F, 7.0F, 20);
	public static final ToolMaterial MATERIAL_HELLFORGED = EnumHelper.addToolMaterial("material_hellforged", 4, 14000, 3.0F, 3.0F, 16);
	public static final ToolMaterial MATERIAL_DARKNESS = EnumHelper.addToolMaterial("material_darkness", 3, 5000, 5.0F, 2.0F, 40);
	public static final ToolMaterial MATERIAL_UNKNOWN = EnumHelper.addToolMaterial("material_unknown", 8, 99999, 10.0F, 10.0F, 100);
	public static final ToolMaterial MATERIAL_CCLASS = EnumHelper.addToolMaterial("material_cclass", 1, 220, 5.0F, 1.5F, 7);
	public static final ToolMaterial MATERIAL_BCLASS = EnumHelper.addToolMaterial("material_bclass", 3, 2000, 7.5F, 4.5F, 13);
	
	//Items
	public static final Item ESSENCE = new ItemBase("essence");
	public static final Item REALITY_FRAGMENT = new RealityFragment("reality_fragment");
	public static final Item SHARD_OF_CREATION = new ShardOfCreation("shard_of_creation");
	
	//C-Class Weapons
	public static final ItemSword NATURE_SLAYER = new NatureSlayer("nature_slayer", MATERIAL_CCLASS);
	public static final ItemSword GLASSY_EDGE = new GlassyEdge("glassy_edge", MATERIAL_CCLASS);
	public static final ItemSword BLADE_OF_MINOR_WOUNDS = new BladeOfMinorWounds("blade_of_minor_wounds", MATERIAL_CCLASS);
	public static final ItemSword SPECIAL_SHOCK = new SpecialShock("special_shock", MATERIAL_CCLASS);
	public static final ItemSword SPEEDSTER_SWORD = new SpeedsterSword("speedster_sword", MATERIAL_CCLASS);
	public static final ItemSword TOUGH_GUY = new ToughGuy("tough_guy", MATERIAL_CCLASS);
	
	//B-Class Weapons
	public static final ItemSword FLAMEWAKER = new FlameWaker("flamewaker", MATERIAL_BCLASS);
	public static final ItemSword STREAMS_OF_PAIN = new StreamsOfPain("streams_of_pain", MATERIAL_BCLASS);
	public static final ItemSword TASKMASTER = new Taskmaster("taskmaster", MATERIAL_BCLASS);
	public static final ItemSword BURSTER = new Burster("burster", MATERIAL_BCLASS);
	public static final ItemSword FRUIT_NINJA = new FruitNinja("fruit_ninja", MATERIAL_BCLASS);
	
	//A-Class Weapons
	public static final ItemSword FLASH_BLADE = new FlashBlade("flash_blade", MATERIAL_FLASHSTEEL);
	public static final ItemSword REPEATING_BLADE = new RepeatingBlade("repeating_blade", MATERIAL_HELLFORGED);
	public static final ItemSword DEATH_MARK = new DeathMark("death_mark", MATERIAL_DARKNESS);
	public static final Item STAFF_OF_END = new StaffOfEnd("staff_of_end");
	public static final Item HERMES_BLADE = new HermesBlade("hermes_blade", MATERIAL_DARKNESS);
	public static final Item BULWARKS_HALLMARK = new BulwarksHallmark("bulwarks_hallmark");
	public static final Item HELLFIRE_BLAZE = new HellfireBlaze("hellfire_blaze");
	
	//S-Class Weapons
	public static final ItemSword REALITY_BLADE = new RealityBlade("reality_blade", MATERIAL_UNKNOWN);
	public static final ItemSword HEAVENLY_SMITE = new HeavenlySmite("heavenly_smite", MATERIAL_UNKNOWN);

	
	
}
