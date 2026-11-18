#!/bin/bash

BASE_DIR="src/main/java/com/bolsines"
TEST_DIR="src/test/java/com/bolsines"

# Crear estructura de carpetas
mkdir -p "$BASE_DIR"/{model,repository,service,controller,dto,exception}
mkdir -p "$TEST_DIR"

# Crear clase principal de Spring Boot
cat << 'EOF' > "$BASE_DIR/DemoApplication.java"
package com.bolsines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
EOF

# Crear test por defecto vacío
cat << 'EOF' > "$TEST_DIR/DemoApplicationTests.java"
package com.bolsines;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
EOF

echo "Estructura y archivos base creados en $BASE_DIR y $TEST_DIR"