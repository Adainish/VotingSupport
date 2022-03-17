package io.github.adainish.votingsupport.gui;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.VoteParty;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static io.github.adainish.votingsupport.gui.Buttons.filler;

public class VotePartyGUI {


    public static GooeyPage VotePartyGUI(EntityPlayer pl) {

        Template template = null;

        ChestTemplate.Builder builder = ChestTemplate.builder(3);
        builder.fill(filler);

        VoteParty p = VotingSupport.getParty();
        VotePlayer vp = VotingSupport.getVotePlayers().get(pl.getUniqueID());

        GooeyButton infoButton = GooeyButton.builder()
                .display(new ItemStack(Items.BOOK))
                .title(Util.formattedString("&bVote Party Info"))
                .lore(Util.formattedArrayList(Arrays.asList("&b%v%&7/&b%l% &7Votes needed"
                        .replace("%v%", String.valueOf(p.getCurrentVotes()))
                        .replace("%l%", String.valueOf(p.getRequiredVotes()))
                )))
                .build();

        GooeyButton playerInfoButton = GooeyButton.builder()
                .display(Buttons.playerSkull(pl.getName()))
                .title(Util.formattedString("&b" + pl.getName()))
                .lore(Util.formattedArrayList(Arrays.asList("&7Current Votes: &b" + vp.getVoteCount())))
                .build();

        builder.set(1, 3, infoButton);
        builder.set(1, 5, playerInfoButton);
        template = builder.build();

        return GooeyPage.builder()
                .template(template)
                .title("Vote Party")
                .build();
    }

}
