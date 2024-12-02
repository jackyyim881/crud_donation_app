package com.donation.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.models.data.HomelessStudentDetails;
import com.donation.models.data.MedicalRecords;
import com.donation.models.data.Needs;
import com.donation.models.data.ProgressUpdate;
import com.donation.models.data.ShelterHistory;
import com.donation.models.data.Student;
import com.donation.repository.HomelessStudentDetailsRepository;
import com.donation.repository.MedicalRecordsRepository;
import com.donation.repository.NeedsRepository;
import com.donation.repository.ProgressUpdateRepository;
import com.donation.repository.ShelterHistoryRepository;
import com.donation.repository.StudentRepository;
import com.donation.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HomelessStudentDetailsRepository homelessStudentDetailsRepository;

    @Autowired
    private NeedsRepository needsRepository;

    @Autowired
    private ProgressUpdateRepository progressUpdateRepository;

    @Autowired
    private ShelterHistoryRepository shelterHistoryRepository;

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    // Fetch all students
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Fetch a student by ID
    @Override
    public Student getStudentById(Long id) {
        // Fetch the student by ID, or throw an exception if not found
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // Save or update a student
    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    // Delete a student by ID
    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public HomelessStudentDetails getHomelessStudentDetailsByStudentId(Long studentId) {
        Optional<HomelessStudentDetails> homelessStudentDetails = homelessStudentDetailsRepository
                .findByStudentId(studentId);
        return homelessStudentDetails.orElse(null);
    }

    @Override
    public List<Needs> getNeedsByStudentId(Long studentId) {
        return needsRepository.findAllByStudentId(studentId);
    }

    @Override
    public List<ProgressUpdate> getProgressUpdatesByStudentId(Long studentId) {
        return progressUpdateRepository.findAllByStudentId(studentId);
    }

    @Override
    public List<ShelterHistory> getShelterHistoryByStudentId(Long studentId) {
        return shelterHistoryRepository.findAllByStudentId(studentId);
    }

    @Override
    public List<MedicalRecords> getMedicalRecordsByStudentId(Long studentId) {
        return medicalRecordsRepository.findAllByStudentId(studentId);
    }
}
