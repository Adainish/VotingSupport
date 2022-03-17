package io.github.adainish.votingsupport.gui;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class Buttons {
    public static GooeyButton filler = GooeyButton.builder()
            .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 7))
            .build();

    public static GooeyButton playerSkull(VotePlayer p, List <String> lore, String title) {

        return GooeyButton.builder()
                .display(playerSkull(p.getUserName()))
                .lore(Util.formattedArrayList(lore))
                .title(Util.formattedString(title))
                .build();
    }

    public static ItemStack playerSkull(String n) {
        ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("SkullOwner", n);
        skull.setTagCompound(tagCompound);
        return skull;
    }
}
