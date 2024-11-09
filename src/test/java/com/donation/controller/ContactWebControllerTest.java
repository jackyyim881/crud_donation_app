package com.donation.controller;

import com.donation.models.data.Contact;
import com.donation.repository.ContactRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactWebController.class)
@ExtendWith(SpringExtension.class)
class ContactWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactRepository contactRepository;

    // Test for the GET /contact endpoint
    @Test
    void testShowContactForm() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact/contact"))
                .andExpect(model().attributeExists("contact"));
    }

    // Test for the POST /contact endpoint
    @Test
    void testSubmitContactForm() throws Exception {
        Contact contact = new Contact();
        contact.setName("John Doe");
        contact.setEmail("john@example.com");
        contact.setMessage("Hello, I have a question.");

        mockMvc.perform(post("/contact")
                .flashAttr("contact", contact))
                .andExpect(status().isOk())
                .andExpect(view().name("contact/contact"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message",
                        "Thank you for reaching out to us. We will get back to you soon."));

        // Verify that contactRepository.save() was called once with the contact
        verify(contactRepository, times(1)).save(contact);
    }
}
