package com.IAS.calculadoraTecnico;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static javax.management.Query.value;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@AutoConfigureMockMvc
@SpringBootTest
public class TecnicoImplTest {
    private final  String URL ="/tecnico";
    @Autowired
    MockMvc mvc;

    @Test
    public void consultarTiempoTecnico() throws Exception{
        mvc.perform(get(URL+"/1/6")).andExpect(MockMvcResultMatchers.jsonPath
                ("$.horasSemanaNormales").value(38)).andDo(print());
    }
}
