package com.chen.assistant.member.zhongx;

import java.util.*;

public class MeetingPlan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.replaceAll("\\[","");
        input = input.replaceAll("\\]","");
        input = input.replaceAll("\\‘","");
        input = input.replaceAll("\\’","");
        input = input.replaceAll(",","");
        String[] inputArray = input.split(" ");

        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < inputArray.length; i += 3) {
            String name = inputArray[i];
            double duration = Double.parseDouble(inputArray[i + 1]);
            String type = inputArray[i + 2];
            Meeting meeting = new Meeting(name, duration, type);
            meetings.add(meeting);
        }

        planMeetings(meetings);
    }

    private static void planMeetings(List<Meeting> meetings) {
        Map<String, List<Meeting>> meetingMap = new HashMap<>();
        int day = 1;

        for (Meeting meeting : meetings) {
            String type = meeting.getType();
            if (!meetingMap.containsKey(type)) {
                meetingMap.put(type, new ArrayList<>());
                meetingMap.get(type).add(meeting);
            } else {
                meetingMap.get(type).add(meeting);
            }
        }
        List<Meeting> OrderMeetings = new ArrayList<>();
        for (Map.Entry<String, List<Meeting>> meetingList : meetingMap.entrySet()) {
            for (Meeting meet : meetingList.getValue()) {
                OrderMeetings.add(meet);
            }
        }
        List<Meeting> DayMeetings = new ArrayList<>();
        double remainingTime = 8;
        for (Meeting meet : OrderMeetings){
            if (remainingTime >= meet.getTime()) {
                DayMeetings.add(meet);
                remainingTime -= meet.getTime();
            } else {
                System.out.println("Day " + day + ":");
                for (Meeting m : DayMeetings) {
                    System.out.println(m);
                }
                day++;
                DayMeetings = new ArrayList<>();
                DayMeetings.add(meet);
                remainingTime = 8-meet.getTime();
            }
        }
        System.out.println("Day " + day + ":");
        for (Meeting m : DayMeetings) {
            System.out.println(m);
        }
    }

//    private static void planMeetings(List<Meeting> meetings) {
//        Map<String, List<Meeting>> meetingMap = new HashMap<>();
//        int day = 1;
//        Map<String, Double> deque = new HashMap<>();
//
//
//        for (Meeting meeting : meetings) {
//            String type = meeting.getType();
//            if (!meetingMap.containsKey(type)) {
//                meetingMap.put(type, new ArrayList<>());
//                deque.put(meeting.type, meeting.time);
//                meetingMap.get(type).add(meeting);
//            } else {
//                meetingMap.get(type).add(meeting);
//                deque.put(meeting.type, deque.get(meeting.type)+meeting.time);
//            }
//        }
//        int len = deque.size();
//        double[] times = new double[len];
//        int i=0;
//        for (Map.Entry<String, Double> meetingList : deque.entrySet()) {
//            times[i++] = meetingList.getValue();
//        }
//        int index = 0;
//        int sum = 0;
//        int slow = 0;
//        for (index = 0; index < len; index++) {
//            if(times[index]+su)
//        }
//
//        List<Meeting> OrderMeetings = new ArrayList<>();
//        for (Map.Entry<String, List<Meeting>> meetingList : meetingMap.entrySet()) {
//            for (Meeting meet : meetingList.getValue()) {
//                OrderMeetings.add(meet);
//            }
//        }
//        List<Meeting> DayMeetings = new ArrayList<>();
//        double remainingTime = 8;
//        for (Meeting meet : OrderMeetings){
//            if (remainingTime >= meet.getTime()) {
//                DayMeetings.add(meet);
//                remainingTime -= meet.getTime();
//            } else {
//                System.out.println("Day " + day + ":");
//                for (Meeting m : DayMeetings) {
//                    System.out.println(m);
//                }
//                day++;
//                DayMeetings = new ArrayList<>();
//                DayMeetings.add(meet);
//                remainingTime = 8-meet.getTime();
//            }
//        }
//        System.out.println("Day " + day + ":");
//        for (Meeting m : DayMeetings) {
//            System.out.println(m);
//        }
//    }

    private static class Meeting {
        private String name;
        private double time;
        private String type;

        public Meeting(String name, double time, String type) {
            this.name = name;
            this.time = time;
            this.type = type;
        }

        public double getTime() {
            return time;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return String.format("'%s', %.1f, '%s'", name, time, type);
        }
    }
}
