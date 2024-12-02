package com.donation.service.mapper;

import com.donation.models.data.Student;
import com.donation.models.data.HomelessStudentDetails;
import com.donation.dto.StudentDTO;
import com.donation.dto.HomelessStudentDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentMapperService {

    public StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getSchool(),
                student.getBio(),
                student.getLatitude(),
                student.getLongitude(),
                student.getImageBase64(), // Handling byte[] -> Base64 conversion
                mapHomelessDetails(student.getHomelessDetails()) // Map homeless details to DTO
        );
    }

    // The new toEntity method
    public Student toEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.id()); // If it's an update scenario, the ID will be non-null
        student.setName(studentDTO.name());
        student.setAge(studentDTO.age());
        student.setSchool(studentDTO.school());
        student.setBio(studentDTO.bio());
        // No need for latitude and longitude here unless you add them to StudentDTO
        // Convert Base64 back to byte[] if needed
        if (studentDTO.studentImageBase64() != null) {
            student.setStudentImage(decodeBase64Image(studentDTO.studentImageBase64()));
        }
        return student;
    }

    // Utility method to decode Base64 to byte[]
    private byte[] decodeBase64Image(String base64Image) {
        return base64Image != null ? java.util.Base64.getDecoder().decode(base64Image) : null;
    }

    public HomelessStudentDetailsDTO toDTO(HomelessStudentDetails homelessDetails) {
        return new HomelessStudentDetailsDTO(
                homelessDetails.getId(),
                homelessDetails.getCurrentShelter(),
                homelessDetails.getDurationOfHomelessness(),
                homelessDetails.getEmergencyContact(),
                homelessDetails.getSpecialNeeds());
    }

    private List<HomelessStudentDetailsDTO> mapHomelessDetails(List<HomelessStudentDetails> homelessDetails) {
        return homelessDetails != null ? homelessDetails.stream()
                .map(this::toDTO)
                .collect(Collectors.toList()) : List.of(); // Return an empty list if homelessDetails is null
    }
}
