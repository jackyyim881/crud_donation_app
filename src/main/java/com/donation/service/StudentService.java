package com.donation.service;

import com.donation.models.data.HomelessStudentDetails;
import com.donation.models.data.MedicalRecords;
import com.donation.models.data.Needs;
import com.donation.models.data.ProgressUpdate;
import com.donation.models.data.ShelterHistory;
import com.donation.models.data.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    Student getStudentById(Long id);

    void saveStudent(Student student);

    void deleteStudentById(Long id);

    HomelessStudentDetails getHomelessStudentDetailsByStudentId(Long studentId);

    List<Needs> getNeedsByStudentId(Long studentId);

    List<ProgressUpdate> getProgressUpdatesByStudentId(Long studentId);

    List<ShelterHistory> getShelterHistoryByStudentId(Long studentId);

    List<MedicalRecords> getMedicalRecordsByStudentId(Long studentId);
}