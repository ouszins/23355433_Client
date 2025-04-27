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

    

}
