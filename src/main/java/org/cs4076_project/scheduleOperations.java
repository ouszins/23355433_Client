/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cs4076_project;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author bmcmo
 */
public class scheduleOperations {

    private static ConcurrentHashMap<String, String> courseSchedule = new ConcurrentHashMap<>();


    static String addLecture(String moduleCode, String scheduleKey) {
        if (courseSchedule.containsKey(scheduleKey)) {
            return "Error: Room is already booked for " + courseSchedule.get(scheduleKey);
        }
        courseSchedule.put(scheduleKey,moduleCode);
        return "Lecture added: " + moduleCode + "_" + scheduleKey;
    }

    static String removeLecture(String scheduleKey) {
        if (courseSchedule.containsKey(scheduleKey)) {
            String removedLecture = courseSchedule.remove(scheduleKey);
            return "Lecture removed: " + removedLecture;
        } else {
            return "Lecture not found";
        }
    }

    static String displaySchedule() {
        if (courseSchedule.isEmpty()) {
            return "No lectures scheduled";
        }

        StringBuilder schedule = new StringBuilder();
        for (String key : courseSchedule.keySet()) {
            schedule.append(courseSchedule.get(key)).append("_").append(key).append("/");
        }

        return schedule.toString();
    }


    static String moveLecturesEarlier() {
        int[] days = {0,1,2,3,4};
        Thread[] threads = new Thread[days.length];

        for (int i = 0; i < days.length; i++) {
            int day = days[i];

            threads[i] = new Thread(() -> shiftDayLectures(day));
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        return "Moved lectures to earlier slots.";
    }

    static  synchronized String shiftDayLectures(int day) {
        String[] earlySlots = {"9", "10", "11", "12", "13", "14", "15", "16", "17"};

        HashMap<String, String> tempDayLectures = new HashMap<>();
        int dayShifted = day + 1;
        for (String key : courseSchedule.keySet()) {
            String[] parts = key.split("_");
            String[] partsDate = parts[2].split("-");
            int dayPart = Integer.parseInt(parts[2]);

            if (dayPart == dayShifted || dayPart == dayShifted+7 || dayPart == dayShifted+14 || dayPart == dayShifted+21 || dayPart == dayShifted+28) {
                tempDayLectures.put(key, courseSchedule.get(key));
            }

        }
        if (tempDayLectures.isEmpty()) {
            System.out.println(day +"dwmp");
            return "No lectures to shift.";
        }

        //  Try to move them to early slots
        for (String oldKey : tempDayLectures.keySet()) {
            String moduleCode = tempDayLectures.get(oldKey);
            String[] parts = oldKey.split("_"); // Room_Day_Time

            String room = parts[0];
            String dayPart = parts[1];

            boolean moved = false;

            for (String earlySlot : earlySlots) {
                String newKey = room + "_" + dayPart + "_" + earlySlot;

                // If early timeslot is free
                if (!courseSchedule.containsKey(newKey)) {
                    // Move lecture
                    System.out.println("new");
                    courseSchedule.remove(oldKey);
                    courseSchedule.put(newKey, moduleCode);
                    moved = true;
                    break;
                }
            }
        }
        return "Attempted to move lectures to earlier slots.";
    }

}
