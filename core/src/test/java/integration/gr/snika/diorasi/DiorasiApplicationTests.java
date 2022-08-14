package integration.gr.snika.diorasi;

//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; 
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import gr.snika.diorasi.controllers.UserController;
import gr.snika.diorasi.dto.AppUserDTO;
import gr.snika.diorasi.services.UserService;

//@SpringBootTest
//@AutoConfigureMockMvc
@ContextConfiguration(classes = {UserController.class})
@WebMvcTest(UserController.class)
@EnableWebMvc
public class DiorasiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;
	
	@WithMockUser(value = "user")
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
		mockMvc.perform(post("/api/users")
						.content(asJsonString(new AppUserDTO("username", "pass", "pass")))
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.with(csrf())
						)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").exists())
				.andExpect(jsonPath("$").value("success"))
			;
	}
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
		
}
