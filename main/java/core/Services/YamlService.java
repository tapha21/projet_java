package core.Services;

import java.util.Map;

public interface YamlService{
        Map<String,Object> loadyaml();
        Map<String,Object> loadyaml(String path);

}