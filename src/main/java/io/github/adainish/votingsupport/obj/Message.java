package io.github.adainish.votingsupport.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message {
    private List <String> messageList = new ArrayList <>();

    private String messageType;
    private boolean useNotificationSound;
    private boolean spawnParticles;
    private UUID targetUUID;

    public Message(String message) {

    }

    public Message(List<String> messageList) {

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
}
