package io.github.adainish.votingsupport.gui;

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
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.storage.PlayerStorage;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.item.ItemStack;

import java.util.*;

import static io.github.adainish.votingsupport.gui.Buttons.filler;

public class LeaderBoardGUI {

    public static List <Button> leaderboardPlayers() {
        List<Button> buttonList = new ArrayList <>();

        Leaderboard leaderboard = VotingSupport.getLeaderboard();
        List<VotePlayer> votePlayers = new ArrayList <>();
        if (!leaderboard.getPlayerUUIDList().isEmpty()) {
            for (UUID uuid:leaderboard.getPlayerUUIDList()) {
                VotePlayer p;
                if (Util.isOnline(uuid)) {
                    p = VotingSupport.getVotePlayers().get(uuid);
                } else {
                    p = PlayerStorage.getPlayer(uuid);
                }
                if (p == null)
                    continue;
                votePlayers.add(p);
            }
        }

        votePlayers.sort(Comparator.comparing(VotePlayer::getLeaderBoardCount));
        for (int i = 0, votePlayersSize = votePlayers.size(); i < votePlayersSize; i++) {
            VotePlayer p = votePlayers.get(i);
            GooeyButton button = GooeyButton.builder()
                    .display(Buttons.playerSkull(p.getUserName()))
                    .lore(Util.formattedArrayList(Arrays.asList("&7Leaderboard Position: &b%pos%".replace("%pos%", String.valueOf(i + 1)), "&7Votes: &b" + p.getLeaderBoardCount())))
                    .title(Util.formattedString(p.getUserName()))
                    .build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static LinkedPage LeaderBoardDisplay() {
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

        if (leaderboardPlayers().size() > 18) {
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

        return PaginationHelper.createPagesFromPlaceholders(template, leaderboardPlayers(), LinkedPage.builder().title("Leaderboard").template(template));
    }
}
