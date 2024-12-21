package com.core.Services.impl;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.core.Services.YamlService;

public class YamlServiceImpl implements YamlService{

    private String path="Meta-INF/app.yaml";
    @Override
    public Map<String, Object> loadyaml() {
     return this.loadyaml(path);
    }

    @Override
    public Map<String, Object> loadyaml(String path) {
      Yaml yaml = new Yaml();
      InputStream inputStream = this.getClass()
     .getClassLoader()
       .getResourceAsStream(path);
      return yaml.load(inputStream);
    }

}