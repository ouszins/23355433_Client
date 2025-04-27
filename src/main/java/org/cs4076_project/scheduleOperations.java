/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cs4076_project;
import java.util.HashMap;

/**
 *
 * @author bmcmo
 */
public class scheduleOperations {

    private static HashMap<String, String> courseSchedule = new HashMap<>();

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
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        Thread[] threads = new Thread[days.length];

        for (int i = 0; i < days.length; i++) {
            String day = days[i];

            threads[i] = new Thread(() -> shiftDayLectures(day));
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println( e.getMessage());
            }
        }

        return "Moved lectures to earlier slots.";
    }

    static  synchronized String shiftDayLectures(String day) {
        String[] earlySlots = {"9", "10", "11", "12", "13", "14", "15", "16", "17"};

        HashMap<String, String> tempDayLectures = new HashMap<>();

        for (String key : courseSchedule.keySet()) {
            if (key.contains(day)) {
                tempDayLectures.put(key, courseSchedule.get(key));

            }
        }
        if (tempDayLectures.isEmpty()) {
            return "No lectures to shift.";
        }

        //  Try to move them to early slots
        for (String oldKey : tempDayLectures.keySet()) {
            String moduleCode = tempDayLectures.get(oldKey);
            String[] parts = oldKey.split("_"); // Room_Day_Time

            String room = parts[0];
            String dayPart = parts[1];

            for (String earlySlot : earlySlots) {
                String newKey = room + "_" + dayPart + "_" + earlySlot;

                // If early timeslot is free
                if (!courseSchedule.containsKey(newKey)) {
                    // Move lecture
                    courseSchedule.remove(oldKey);
                    courseSchedule.put(newKey, moduleCode);
                    break;
                }
            }
        }
        return "Attempted to move lectures to earlier slots.";
    }

}