package io.github.adainish.votingsupport.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import io.github.adainish.votingsupport.handlers.RewardHandler;
import io.github.adainish.votingsupport.obj.StreakDay;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

import static io.github.adainish.votingsupport.gui.Buttons.filler;

public class StreakGUI {

    public static List <Button> streakItems(VotePlayer p) {
        List<Button> buttonList = new ArrayList <>();

        for (StreakDay d:p.getStreak().getStreakDayList()) {
            Item i = Item.getByNameOrId(d.getItemDisplay());
            if (i == null)
                i = Items.PAPER;
            ItemStack stack = new ItemStack(i);

            Button b = GooeyButton.builder()
                    .display(stack)
                    .title(Util.formattedString(d.getDisplay()))
                    .lore(Util.formattedArrayList(d.getLoreList()))
                    .onClick(ba -> {
                        UIManager.closeUI(ba.getPlayer());
                        if (d.isCompleted()) {
                            if (d.isClaimed()) {
                                Util.send(ba.getPlayer(), "&cYou've already claimed this streak reward!");
                            } else {
                                RewardHandler.handOutStreakDayRewards(d, ba.getPlayer(), p);
                            }
                        }
                        else {
                            Util.send(ba.getPlayer(), "&cYou can't claim these rewards yet");
                        }
                    })
                    .build();

            buttonList.add(b);
        }
        return buttonList;
    }

    public static LinkedPage StreakDisplay(VotePlayer p) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.LtradeHolderLeft))
                .title("Previous Page")
                .linkType(LinkType.Previous)
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.tradeHolderRight))
                .title("Next Page")
                .linkType(LinkType.Next)
                .build();

        Template template = null;

        if (streakItems(p).size() > 18) {
            template = ChestTemplate.builder(5)
                    .border(0, 0, 5, 9, filler)
                    .set(0, 3, previous)
                    .set(0, 5, next)
                    .rectangle(1, 1, 3, 6, placeHolderButton)
                    .build();
        } else {
            template = ChestTemplate.builder(5)
                    .border(0, 0, 5, 9, filler)
                    .rectangle(1, 1, 2, 5, placeHolderButton)
                    .build();
        }

        return PaginationHelper.createPagesFromPlaceholders(template, streakItems(p), LinkedPage.builder().title("Vote Streak").template(template));
    }

}
