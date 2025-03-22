package com.tcc.rag_open_ai_tcc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.ZonedDateTime;

@Data
public class EvolutionApiPayload {
    private String event;
    private String instance;
    private Data data;
    private String destination;
    private ZonedDateTime dateTime;
    private String sender;
    private String serverUrl;
    private String apikey;

    @ToString
    @Getter
    public static class Data {
        private Key key;
        private String pushName;
        private String status;
        private Message message;
        private ContextInfo contextInfo;
        private String messageType;
        private Instant messageTimestamp;
        private String instanceId;
        private String source;

        @ToString
        @Getter
        public static class Key {
            private String remoteJid;
            private boolean fromMe;
            private String id;
        }

        @ToString
        @Getter
        public static class Message {
            private MessageContextInfo messageContextInfo;
            private String conversation;

            @ToString
            @Getter
            public static class MessageContextInfo {
                private DeviceListMetadata deviceListMetadata;
                private int deviceListMetadataVersion;
                private String messageSecret;

                @ToString
                @Getter
                public static class DeviceListMetadata {
                    private String senderKeyHash;
                    private long senderTimestamp;
                    private String recipientKeyHash;
                    private Instant recipientTimestamp;
                }
            }
        }

        @ToString
        @Getter
        public static class ContextInfo {
            private long expiration;
            private long ephemeralSettingTimestamp;
            private DisappearingMode disappearingMode;

            @ToString
            @Getter
            public static class DisappearingMode {
                private String initiator;
            }
        }
    }

}