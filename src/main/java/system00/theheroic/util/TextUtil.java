package system00.theheroic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

public class TextUtil {

	public static String stringToRainbow(String parString, boolean parReturnToBlack) {
		int stringLength = parString.length();
		if (stringLength < 1) {
			return "";
		}
		String outputString = "";
		TextFormatting[] colorChar = {
				TextFormatting.RED,
				TextFormatting.GOLD,
				TextFormatting.YELLOW,
				TextFormatting.GREEN,
				TextFormatting.AQUA,
				TextFormatting.BLUE,
				TextFormatting.LIGHT_PURPLE,
				TextFormatting.DARK_PURPLE
		};

		for (int i = 0; i < stringLength; i++) {
			outputString = outputString + colorChar[i % 8] + parString.substring(i, i + 1);
		}
		if (parReturnToBlack) {
			return outputString + TextFormatting.BLACK;
		}
		return outputString + TextFormatting.WHITE;
	}

	public static String stringToGolden(String parString, int parShineLocation, boolean parReturnToBlack) {
		int stringLength = parString.length();
		if (stringLength < 1) {
			return "";
		}
		String outputString = "";
		for (int i = 0; i < stringLength; i++) {
			if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 0) {
				outputString = outputString + TextFormatting.WHITE + parString.substring(i, i + 1);
			}
			else if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 1) {
				outputString = outputString + TextFormatting.YELLOW + parString.substring(i, i + 1);
			} else if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 87) {
				outputString = outputString + TextFormatting.YELLOW + parString.substring(i, i + 1);
			} else {
				outputString = outputString + TextFormatting.GOLD + parString.substring(i, i + 1);
			}
		}
		if (parReturnToBlack) {
			return outputString + TextFormatting.BLACK;
		}
		return outputString + TextFormatting.WHITE;
	}
}
