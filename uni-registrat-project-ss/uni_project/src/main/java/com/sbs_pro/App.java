package com.sbs_pro;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Create Registration instance
        Registration registration = new Registration();

        // Create faculty
        Faculty faculty1 = new Faculty("F001", "Dr. Smith", "smith@example.com");
        registration.createFaculty(faculty1.getFacultyID(), faculty1.getName(), faculty1.getContactDetails());

        // Create semester
        LocalDate semesterStart = LocalDate.of(2024, 2, 1);
        LocalDate semesterEnd = LocalDate.of(2024, 6, 1);
        registration.createSemester("Spring2024", semesterStart, semesterEnd);

        // Retrieve created semester
        Semester spring2024 = registration.semesters.get(0);

        // Create courses
        ArrayList<MeetingSession> meetingSessions1 = new ArrayList<>();
        meetingSessions1.add(new MeetingSession(DayOfWeek.MONDAY, LocalTime.of(10, 0), Duration.ofHours(2)));
        registration.createCourse("Biology", 4, faculty1, meetingSessions1, new ArrayList<>(), registration.semesters);

        ArrayList<MeetingSession> meetingSessions2 = new ArrayList<>();
        meetingSessions2.add(new MeetingSession(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), Duration.ofHours(2)));
        registration.createCourse("Math", 3, faculty1, meetingSessions2, new ArrayList<>(), registration.semesters);

        // Retrieve created courses
        Course biology = registration.courses.get(0);
        Course math = registration.courses.get(1);

        // Create a student
        registration.createStudent("S001", "Alice Smith", "Biology", "alice@example.com", faculty1);

        // Retrieve created student
        Student alice = registration.students.get(0);

        // Assign Alice to courses
        registration.assignStudentToCourse(alice, biology, spring2024);
        registration.assignStudentToCourse(alice, math, spring2024);

        // Enter grades for Alice
        registration.enterGrade(alice, biology, "B+");
        registration.enterGrade(alice, math, "C+");

        // Create a course with a schedule conflict
        ArrayList<MeetingSession> conflictingMeetingSessions = new ArrayList<>();
        conflictingMeetingSessions.add(new MeetingSession(DayOfWeek.MONDAY, LocalTime.of(10, 0), Duration.ofHours(2)));
        registration.createCourse("Chemistry", 3, faculty1, conflictingMeetingSessions, new ArrayList<>(),
                registration.semesters);
        Course chemistry = registration.courses.get(2);

        // Attempt to assign Alice to the conflicting course
        boolean conflictResult = registration.assignStudentToCourse(alice, chemistry, spring2024);
        System.out.println("Attempt to assign conflicting course resulted in: " + conflictResult);

        // Create a course with prerequisites
        ArrayList<Course> prerequisites = new ArrayList<>();
        prerequisites.add(biology); // Assuming Biology is a prerequisite for Physics
        ArrayList<MeetingSession> physicsMeetingSessions = new ArrayList<>();
        physicsMeetingSessions.add(new MeetingSession(DayOfWeek.FRIDAY, LocalTime.of(14, 0), Duration.ofHours(2)));
        registration.createCourse("Physics", 4, faculty1, physicsMeetingSessions, prerequisites,
                registration.semesters);
        Course physics = registration.courses.get(3);

        // Attempt to assign Alice to the course with prerequisites
        boolean prereqResult = registration.assignStudentToCourse(alice, physics, spring2024);
        System.out.println("Attempt to assign course with unmet prerequisites resulted in: " + prereqResult);

        // Generate academic report for Alice for Spring2024 semester
        registration.generateAcademicsReport(alice, spring2024);
    }
}
