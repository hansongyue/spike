package top.doperj.util.security;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@Scope("singleton")
public class SaltMap {
    @PostConstruct
    void initialize() {
        salts = new HashMap<>();
        rand = new Random();
    }
    Map<Integer, Integer> salts;
    Random rand;

    public int getSalt(int productId) {
        return salts.computeIfAbsent(productId, (k) -> rand.nextInt());
    }
}
