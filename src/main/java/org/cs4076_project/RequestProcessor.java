/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cs4076_project;

/**
 *
 * @author bmcmo
 */
public class RequestProcessor {

    static String checkRequest(String request) {

        String[] parts = request.split("_");

        String command = parts[0].toUpperCase();


        switch (command) {
            case "ADD":
                String moduleCode = parts[1];
                String scheduleKey = parts[2]+"_"+parts[3]+"_"+parts[4];
                if (parts.length < 4) {
                    return "Invalid ADD format";
                }
                return scheduleOperations.addLecture(moduleCode, scheduleKey);

            case "REMOVE":

                scheduleKey = parts[2]+"_"+parts[3]+"_"+parts[4];
                return scheduleOperations.removeLecture(scheduleKey);

            case "DISPLAY":
                return scheduleOperations.displaySchedule();

            case "EARLY":
                String day = parts[2];
                return scheduleOperations.moveLecturesEarlier();

            default:
                return "Unknown command";
        }
    }
}