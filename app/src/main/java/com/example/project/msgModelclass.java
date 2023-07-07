package com.example.project;


    public class msgModelclass {
        String message;
        String senderid,toid;
        long timeStamp;
        public boolean sender=false;
        public msgModelclass() {
        }

        public msgModelclass(String message, String senderid,String toid, long timeStamp) {
            this.message = message;
            this.senderid = senderid;
            this.timeStamp = timeStamp;
            this.toid=toid;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSenderid() {
            return senderid;
        }

        public void setSenderid(String senderid) {
            this.senderid = senderid;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
        public String getToid()
        {
            return toid;
        }
        public void setToid(String toid)
        {
            this.toid=toid;
        }

    }


