package com.IAS.calculadoraTecnico;

import com.IAS.calculadoraTecnico.Domain.Servicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ServicioImplTest {
    private final  String URL ="/servicio";
    @Autowired
    MockMvc mvc;

    @Test
    public void crearServicio() throws  Exception{
        Servicio servicio= new Servicio();
        servicio.setCedula(5L);
        Date fechaFinal= new Date(2021,9,13,41,02);
        Date fechaInicial= new Date(2021,9,14,41,02);
        servicio.setFechaFinal(fechaFinal);
        servicio.setFechaInicial(fechaInicial);
         mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(servicio))).andExpect(status().isOk()).andDo(print());
    }
    public static String toJson(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
