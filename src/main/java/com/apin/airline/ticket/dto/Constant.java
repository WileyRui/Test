package com.apin.airline.ticket.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huanglei
 * @date 2017/10/26
 * @remark
 */
public class Constant {
    public enum CredType{
        IDENTIFICATION_CARD(0,"身份证"),
        PASSPORT(1,"护照"),
        MAINLAND_TRAVEL_PERMIT_FOR_TAIWAN_RESIDENTS(2,"台胞证"),
        HONG_KONG_MACAU_LAISSEZ_PASSER(3,"港澳通行证"),
        MAINLAND_TRAVEL_PERMIT_FOR_HONG_KONG_AND_MACAO_RESIDENTS(4,"回乡证"),
        SAILOR(5,"海员证"),
        MILITARY_OFFICER_CARD_NO(6,"军官证"),
        SOLDIER_CARD_NO(7,"士兵证"),
        OTHER(8,"其他");
//        BIRTH_CERTIFICATE(8,"出生证明"),
//        TAIWAN_PASS_PROCESS(9,"台湾通行证");

        CredType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private int code;
        private String value;
        private static Map<Integer,CredType> map = new HashMap<>();

        static {
            for (CredType credType : CredType.values()) {
                map.put(credType.code, credType);
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static String valueOf(int code) {
            return map.get(code).getValue();
        }
    }

    public enum PassengerType{
        ADULT(0,"成人"),
        CHIDREN(1,"儿童"),
        BABY(2,"婴儿"),
        YOUTH(3,"青年"),
        STUDENTS(4,"留学生"),
        LABOS(5,"劳工"),
        IMMIGRANT(6,"移民"),
        SAILOR(7,"海员"),
        OLD(8,"老年"),
        MOTHERING(9,"探亲");

        PassengerType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private int code;
        private String value;
        private static Map<Integer,PassengerType> map = new HashMap<>();

        static {
            for (PassengerType passengerType : PassengerType.values()) {
                map.put(passengerType.code, passengerType);
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static String valueOf(int code) {
            return map.get(code).getValue();
        }
    }

    public enum Gender{
        FEMALE(0,"女"),
        MALE(1,"男");

        private Gender(int code,String value){
            this.code = code;
            this.value = value;
        }

        private int code;
        private String value;
        private static Map<Integer,Gender> map = new HashMap<>();

        static {
            for (Gender gender : Gender.values()) {
                map.put(gender.code, gender);
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static String valueOf(int code) {
            return map.get(code).getValue();
        }
    }


}
