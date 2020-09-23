package system00.theheroic.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import system00.theheroic.items.ItemBase;
import system00.theheroic.items.weapons.BulwarksHallmark;
import system00.theheroic.items.weapons.DeathMark;
import system00.theheroic.items.weapons.FlashBlade;
import system00.theheroic.items.weapons.HermesBlade;
import system00.theheroic.items.weapons.RepeatingBlade;
import system00.theheroic.items.weapons.StaffOfEnd;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final ToolMaterial MATERIAL_FLASHSTEEL = EnumHelper.addToolMaterial("material_flashsteel", 4, 7000, 7.0F, 7.0F, 20);
	public static final ToolMaterial MATERIAL_HELLFORGED = EnumHelper.addToolMaterial("material_hellforged", 4, 14000, 3.0F, 3.0F, 16);
	public static final ToolMaterial MATERIAL_DARKNESS = EnumHelper.addToolMaterial("material_darkness", 3, 5000, 5.0F, 2.0F, 40);
	
	//Items
	public static final Item ESSENCE = new ItemBase("essence");
	
	//Weapons
	public static final ItemSword FLASH_BLADE = new FlashBlade("flash_blade", MATERIAL_FLASHSTEEL);
	public static final ItemSword REPEATING_BLADE = new RepeatingBlade("repeating_blade", MATERIAL_HELLFORGED);
	public static final ItemSword DEATH_MARK = new DeathMark("death_mark", MATERIAL_DARKNESS);
	public static final Item STAFF_OF_END = new StaffOfEnd("staff_of_end");
	public static final Item HERMES_BLADE = new HermesBlade("hermes_blade", MATERIAL_DARKNESS);
	public static final Item BULWARKS_HALLMARK = new BulwarksHallmark("bulwarks_hallmark");
}
