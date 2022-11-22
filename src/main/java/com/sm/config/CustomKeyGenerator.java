package com.sm.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Component
public class CustomKeyGenerator implements KeyGenerator {

    /*

    In the GET endpoint put this and you will see the below response of cache/data/3
    @Cacheable(keyGenerator = "customKeyGenerator")

    {
        "EmployeeController_find_6": {
            "headers": {},
            "body": {
                "value": {
                    "id": 6,
                    "name": "hello-world 5"
                },
                "empty": false,
                "present": true
            },
            "status": "OK",
            "statusCode": "OK",
            "statusCodeValue": 200
        }
    }
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName() + "_" + method.getName() + "_" + StringUtils.arrayToDelimitedString(params, "_");
    }
}
