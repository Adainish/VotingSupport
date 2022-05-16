package io.github.adainish.votingsupport.obj;

import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message {
    private List <String> messageList = new ArrayList <>();

    private EntityPlayerMP target;
    private String messageType;
    private boolean useNotificationSound;
    private boolean spawnParticles;
    private UUID targetUUID;

    public Message(String message, EntityPlayerMP target) {
        setTargetUUID(target.getUniqueID());
        setTarget(target);
        messageList.add(message);
    }
    public Message(List<String> messageList, EntityPlayerMP target) {
        setTargetUUID(target.getUniqueID());
        setTarget(target);
        setMessageList(messageList);
    }
    public Message(String message) {
        messageList.add(message);
    }

    public Message(List<String> messageList) {
        setMessageList(messageList);
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public boolean isUseNotificationSound() {
        return useNotificationSound;
    }

    public void setUseNotificationSound(boolean useNotificationSound) {
        this.useNotificationSound = useNotificationSound;
    }

    public boolean isSpawnParticles() {
        return spawnParticles;
    }

    public void setSpawnParticles(boolean spawnParticles) {
        this.spawnParticles = spawnParticles;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    public void setTargetUUID(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }
    public void setTarget(EntityPlayerMP target) {
        this.target = target;
    }
    public static String returnPrettyStringWithoutCommand(String s) {
        StringBuilder fs = new StringBuilder();
        String[] arr = s.split(" ");
        boolean shouldAppend = true;

        for (String val : arr) {
            if (val.contains("[")) {
                shouldAppend = false;
                continue;
            }

            if (val.contains("]")) {
                shouldAppend = true;
                continue;
            }
            if (shouldAppend) {
                String s1 = val
                        .replaceAll("cmd:", "")
                        .replaceAll("<", "")
                        .replaceAll(">", "")
                        .replace("[", "")
                        .replace("]", "");
                if (val.contains("/")) fs.append(s1);
                else fs.append(" ").append(s1);
            }
        }

        return fs.toString();
    }

    public static String extractCommand(String s) {
        StringBuilder fs = new StringBuilder();
        String[] arr = s.split(" ");
        boolean shouldAppend = false;
        for (String val : arr) {
            if (val.contains("[") || val.contains("<")) shouldAppend = true;

            if (val.contains("]") || val.contains(">")) {
                fs.append(" ").append(val
                        .replaceAll("cmd:", "")
                        .replaceAll(">", "")
                        .replace("[", "")
                        .replace("]", ""));
                break;
            }
            if (shouldAppend) {
                String s1 = val
                        .replaceAll("cmd:", "")
                        .replaceAll("<", "")
                        .replaceAll(">", "")
                        .replace("[", "")
                        .replace("]", "");
                if (val.contains("/")) fs.append(s1);
                else fs.append(" ").append(s1);
            }
        }
        return fs.toString();
    }

    public void sendMessage() {
        List<String> text = getMessageList();
        List<TextComponentString> stringList = new ArrayList<>();
        for (String s: text) {
            boolean isUrl = false;
            boolean isCMD = false;
            TextComponentString textComp = new TextComponentString(returnPrettyStringWithoutCommand(s)
                    .replaceAll("&([0-9a-fk-or])", "\u00a7$1")
                    .replaceAll("url:", ""));

            if (s.contains("url:"))
                isUrl = true;

            if (s.contains("cmd:"))
                isCMD = true;

            if (isUrl) {
                String[] ar = s.split(" ");
                String fs = "";
                for (String value : ar) {
                    if (value.contains("url:")) {
                        fs = value;
                        break;
                    }
                }
                textComp.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, fs.replaceAll("url:", "")));
            }

            if (isCMD) {
                String s1 = extractCommand(s);
                textComp.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, s1.replaceAll("@pl", target.getName())));
            }
            stringList.add(textComp);
            stringList.forEach(it -> Util.send(target, it));
        }
    }


}
