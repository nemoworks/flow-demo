package info.nemoworks.enkrino.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import info.nemoworks.enkrino.engine.Engine;
import info.nemoworks.enkrino.engine.Flow;
import info.nemoworks.enkrino.engine.FlowMirror;
import info.nemoworks.enkrino.engine.FlowRepository;
import info.nemoworks.enkrino.engine.deserializer.FlowDeserializer;
import info.nemoworks.enkrino.engine.serializer.FlowSerializer;
import info.nemoworks.enkrino.engine.serializer.MirrorSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import info.nemoworks.enkrino.engine.exception.IllegalFlowException;

@Slf4j
@SpringBootApplication
public class EngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineApplication.class, args);
    }

    @Bean
    public Gson getGson(){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Flow.class, new FlowDeserializer());
        builder.registerTypeAdapter(Flow.class, new FlowSerializer());
        builder.registerTypeAdapter(FlowMirror.class, new MirrorSerializer());
        Gson gson = builder.create();
        return gson;
    }

    @Bean
    public Engine getEngine() throws IllegalFlowException{
        final Engine engine = new Engine(FlowRepository.getFlow1());
        return engine;
    }
}
