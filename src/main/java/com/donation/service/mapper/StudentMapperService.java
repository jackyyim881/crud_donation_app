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
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        dto.setSchool(student.getSchool());
        dto.setBio(student.getBio());

        if (student.getHomelessDetails() != null) {
            List<HomelessStudentDetailsDTO> homelessDetailsDTOList = student.getHomelessDetails().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            dto.setHomelessDetails(homelessDetailsDTOList);
        }

        return dto;
    }

    public HomelessStudentDetailsDTO toDTO(HomelessStudentDetails homelessDetails) {
        HomelessStudentDetailsDTO dto = new HomelessStudentDetailsDTO();
        dto.setId(homelessDetails.getId());
        dto.setCurrentShelter(homelessDetails.getCurrentShelter());
        dto.setDurationOfHomelessness(homelessDetails.getDurationOfHomelessness());
        dto.setEmergencyContact(homelessDetails.getEmergencyContact());
        dto.setSpecialNeeds(homelessDetails.getSpecialNeeds());

        return dto;
    }
}