package system00.theheroic.init;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import system00.theheroic.items.ItemBase;
import system00.theheroic.items.consumables.GummyHeart;
import system00.theheroic.items.consumables.Ragetable;
import system00.theheroic.items.misc.*;
import system00.theheroic.items.weapons.Aclass.*;
import system00.theheroic.items.weapons.Bclass.*;
import system00.theheroic.items.weapons.Cclass.*;
import system00.theheroic.items.weapons.Sclass.*;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final ToolMaterial MATERIAL_FLASHSTEEL = EnumHelper.addToolMaterial("material_flashsteel", 4, 7000, 7.0F, 7.0F, 20);
	public static final ToolMaterial MATERIAL_HELLFORGED = EnumHelper.addToolMaterial("material_hellforged", 4, 10000, 3.0F, 3.0F, 16);
	public static final ToolMaterial MATERIAL_DARKNESS = EnumHelper.addToolMaterial("material_darkness", 3, 5000, 5.0F, 2.0F, 40);
	public static final ToolMaterial MATERIAL_UNKNOWN = EnumHelper.addToolMaterial("material_unknown", 8, 99999, 10.0F, 0.0F, 100);
	public static final ToolMaterial MATERIAL_HEAVEN = EnumHelper.addToolMaterial("material_heaven", 14, 99999, 8.0F, 3.0F, 500);
	public static final ToolMaterial MATERIAL_CCLASS = EnumHelper.addToolMaterial("material_cclass", 1, 300, 5.0F, 0F, 7);
	public static final ToolMaterial MATERIAL_BCLASS = EnumHelper.addToolMaterial("material_bclass", 3, 1500, 7.5F, 0F, 13);
	public static final ToolMaterial MATERIAL_ACLASS = EnumHelper.addToolMaterial("material_aclass", 5, 6000, 9F, 0F, 16);
	
	
	//Items: "Tier" shows how many layers of crafting are needed to obtain the item

	//Tier 0
	public static final Item ESSENCE = new ItemBase("essence"); //recipe done
	public static final Item EARTH_INGOT = new ItemBase("earth_ingot"); //recipe done
	public static final Item LIVING_WIND = new ItemBase("living_wind"); //recipe done
	public static final Item TRIPLE_COMPRESSED_IRON_INGOT = new ItemBase("triple_compressed_iron_ingot"); //recipe done
	public static final Item SHADOWDUST = new ItemBase("shadowdust"); //recipe done
	public static final Item PUTRID_SLIME = new ItemBase("putrid_slime"); //recipe done
	public static final Item GLASS_ROD = new ItemBase("glass_rod"); //recipe done
	public static final Item WEAK_CHARGE = new ItemBase("weak_charge"); //recipe done
	public static final Item AQUASTICK = new ItemBase("aquastick"); //recipe done

	//Tier 1
	public static final Item SUPERSTRONG_IRON_INGOT = new ItemBase("superstrong_iron_ingot"); //recipe done
	public static final Item FIRED_UP_ENERGY = new FiredUpEnergy("fired_up_energy"); //recipe done
	public static final Item MAGIBALL = new Magiball("magiball"); //recipe done
	public static final Item FLAMES_OF_SEVEN = new ItemBase("flames_of_seven"); //recipe done
	public static final Item LIQUID_BODY = new ItemBase("liquid_body"); //recipe done
	public static final Item PAINWATER = new ItemBase("painwater"); //recipe done
	public static final Item REINFORCED_ROD = new ItemBase("reinforced_rod"); //recipe done
	public static final Item WEATHER_TALISMAN = new ItemBase("weather_talisman"); //recipe done

	//Tier 2
	public static final Item INFERNUS_STAR = new InfernusStar("infernus_star"); //recipe done
	public static final Item REALITY_FRAGMENT = new RealityFragment("reality_fragment");


	//Tier 3
	public static final Item STRONG_CHARGE = new ItemBase("strong_charge"); //recipe done
	public static final Item SHARD_OF_CREATION = new ShardOfCreation("shard_of_creation");


	//Consumables
	public static final Item GUMMY_HEART = new GummyHeart("gummy_heart", 0, 0, false); //recipe done
	public static final Item RAGETABLE = new Ragetable("ragetable", 3, 3, false); //recipe done


	//C-Class Weapons
	public static final ItemSword NATURE_SLAYER = new NatureSlayer("nature_slayer", MATERIAL_CCLASS); //recipe done
	public static final ItemSword GLASSY_EDGE = new GlassyEdge("glassy_edge", MATERIAL_CCLASS); //recipe done
	public static final ItemSword BLADE_OF_MINOR_WOUNDS = new BladeOfMinorWounds("blade_of_minor_wounds", MATERIAL_CCLASS); //recipe done
	public static final ItemSword SPECIAL_SHOCK = new SpecialShock("special_shock", MATERIAL_CCLASS); //recipe done
	public static final ItemSword SPEEDSTER_SWORD = new SpeedsterSword("speedster_sword", MATERIAL_CCLASS); //recipe done
	public static final ItemSword TOUGH_GUY = new ToughGuy("tough_guy", MATERIAL_CCLASS); //recipe done
	public static final ItemSword LIL_PIERCER = new LilPiercer("lil_piercer", MATERIAL_CCLASS); //recipe done
	public static final ItemSword SMOKESCREEN = new Smokescreen("smokescreen", MATERIAL_CCLASS); //recipe done
	public static final ItemSword RANCID_POISON = new RancidPoison("rancid_poison", MATERIAL_CCLASS); //recipe done


	//B-Class Weapons
	public static final ItemSword FLAMEWAKER = new FlameWaker("flamewaker", MATERIAL_BCLASS); //recipe done
	public static final ItemSword STREAMS_OF_PAIN = new StreamsOfPain("streams_of_pain", MATERIAL_BCLASS); //recipe done
	public static final ItemSword TASKMASTER = new Taskmaster("taskmaster", MATERIAL_BCLASS); //recipe done
	public static final ItemSword BURSTER = new Burster("burster", MATERIAL_BCLASS); //recipe done
	public static final ItemSword FRUIT_NINJA = new FruitNinja("fruit_ninja", MATERIAL_BCLASS); //recipe done
	public static final ItemSword BAD_WEATHER = new BadWeather("bad_weather", MATERIAL_BCLASS); //recipe done
	public static final ItemSword PRIME_TARGET = new PrimeTarget("prime_target", MATERIAL_BCLASS);


	//A-Class Weapons
	public static final ItemSword FLASHY_SLASH = new FlashySlash("flashy_slash", MATERIAL_FLASHSTEEL);
	public static final ItemSword REPEATING_BLADE = new RepeatingBlade("repeating_blade", MATERIAL_HELLFORGED);
	public static final ItemSword DEATH_MARK = new DeathMark("death_mark", MATERIAL_DARKNESS);
	public static final Item STAFF_OF_END = new StaffOfEnd("staff_of_end");
	public static final Item HERMES_DAGGER = new HermesDagger("hermes_dagger", MATERIAL_ACLASS);
	public static final Item BULWARKS_HALLMARK = new BulwarksHallmark("bulwarks_hallmark");
	public static final Item HELLFIRE_BLAZE = new HellfireBlaze("hellfire_blaze");
	public static final Item EARTHSCORN = new Earthscorn("earthscorn");
	public static final ItemSword QUICK_DRAW = new QuickDraw("quick_draw", MATERIAL_ACLASS);
	public static final ItemSword LAST_STAND = new LastStand("last_stand", MATERIAL_ACLASS);

	//S-Class Weapons
	public static final ItemSword SCHRODINGERS_GIFT = new SchrodingersGift("schrodingers_gift", MATERIAL_UNKNOWN);
	public static final ItemSword HEAVENLY_SMITE = new HeavenlySmite("heavenly_smite", MATERIAL_HEAVEN);
	public static final ItemSword THE_ENABLER = new TheEnabler("the_enabler", MATERIAL_HEAVEN);
	public static final Item GOD_OF_THUNDER = new GodOfThunder("god_of_thunder");
	public static final Item JUDGEMENT = new Judgement("judgement");
}
