package gr.snika.diorasi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import gr.snika.diorasi.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class DiorasiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserRepository userRepository;

	
	@WithMockUser(value = "user")
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
		mockMvc.perform(get("/api/users")).andExpect(status().isOk());
	}
	
	@Test
    public void givenAuthRequestOnPrivateServiceWithNoUser_shouldFailwith403() throws Exception {
		mockMvc.perform(get("/api/users")).andExpect(status().isForbidden());
	}
		
}
