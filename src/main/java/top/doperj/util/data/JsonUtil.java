package top.doperj.util.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("singleton")
public class JsonUtil {
    @PostConstruct
    void initialize() {
        mapper = new ObjectMapper();
    }

    private ObjectMapper mapper;

    public String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public <T> T fromJson(String jsonStr, Class<? extends T> clazz) throws JsonProcessingException {
        return mapper.readValue(jsonStr, clazz);
    }
}
