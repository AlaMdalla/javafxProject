    package com.example.jobflow.entities;

    public class projet {


        private int id ;

        private String prname ;

        private String stdate ;
        private String enddate ;

        private String description ;

        private String type ;

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public String getPrname() {
            return prname;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPrname(String prname) {
            this.prname = prname;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public projet( int id, String prname, String description,  String type,String stdate, String enddate) {
            this.prname = prname;
            this.id = id;
            this.stdate = stdate;
            this.enddate = enddate;
            this.description = description;
            this.type = type;
        }

        public projet(String prname, String description,  String type,String stdate, String enddate) {
            this.prname = prname;
            this.stdate = stdate;
            this.enddate = enddate;
            this.type = type;
            this.description = description;

        }

        public String getStdate() {
            return stdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setStdate(String stdate) {
            this.stdate = stdate;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        @Override
        public String toString() {
            return "projet{" +
                    "id=" + id +
                    ", prname='" + prname + '\'' +
                    ", stdate=" + stdate +
                    ", enddate=" + enddate +
                    ", description='" + description + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }



