package com.example.demo.ControllerTest;

import com.Repository.TodoRepository;
import com.model.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc client;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JacksonTester<List<TodoItem>> todoItemJacksonTester;

    @Test
    void should_return_all_todos_when_get_given_() throws Exception {
        //Given
        final List<TodoItem> givenTodos = todoRepository.findAll();

        //When
        final MvcResult result = client.perform(MockMvcRequestBuilders.get("/todo")).andReturn();

        //Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        final List<TodoItem> fetchedTodos = todoItemJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedTodos).hasSameSizeAs(givenTodos);
        assertThat(fetchedTodos)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

    @Test
    void should_return_created_todo_when_post_given_a_new_todo() throws Exception {

        //Given
        String givenTodo = "{\n" +
                "    \"text\": \"text 4\",\n" +
                "    \"done\": false\n" +  // Removed trailing comma
                "}";

        //When
        client.perform(MockMvcRequestBuilders.post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenTodo))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("text 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
        //Then
    }

    @Test
    void should_update_todo_when_put_given_todo_id() throws Exception {
        //Given
        Integer givenID = todoRepository.findAll().get(0).getId();
        String updatedText = "Updated Todo";
        boolean updatedDone = false;

        String updatedTodo = "{\n" +
                "    \"text\": \"" + updatedText + "\",\n" +
                "    \"done\": " + updatedDone + "\n" +
                "}";

        client.perform(MockMvcRequestBuilders.put("/todo/" + givenID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTodo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(givenID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(updatedText))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(updatedDone));
        //When

        //Then
    }

    @Test
    void should_delete_todo_when_delete_given_todo_id() throws Exception {
        //Given
        Integer givenID = todoRepository.findAll().get(0).getId();

        //When
        client.perform(MockMvcRequestBuilders.delete("/todo/" + givenID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        //Then
    }

    @Test
    void should_toggle_when_put_given_id_and_status() throws Exception {
        //Given
        Integer givenID = todoRepository.findAll().get(0).getId();
        boolean newStatus = !(todoRepository.findAll().get(0).isDone());
        String text = todoRepository.findAll().get(0).getText();
        String updatedTodo = "{\n" +
                "    \"text\": \"" + text + "\",\n" +
                "    \"done\": " + newStatus + "\n" +
                "}";
        //When
        client.perform(MockMvcRequestBuilders.put("/todo/" + givenID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTodo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(givenID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(text))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(newStatus));

        //Then

    }

}



