package net.eofitg.armorstand359.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ArmorStandUtil {
    public static String getArmorStandId(ArmorStandEntity armorStand) {
        ItemStack headItem = armorStand.getEquippedStack(EquipmentSlot.HEAD);
        if (headItem == null) return null;

        Item item = headItem.getItem();
        if (item != Items.PLAYER_HEAD) return null;

        if (headItem.contains(DataComponentTypes.PROFILE)) {
            ProfileComponent profile = headItem.get(DataComponentTypes.PROFILE);
            if (profile != null) {
                GameProfile gameProfile = profile.getGameProfile();
                if (gameProfile != null) {
                    return gameProfile.id().toString();
                }
            }
        }
        return null;
    }
}
