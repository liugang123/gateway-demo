# 工程简介



# 延伸阅读

```
spring:
  cloud:
    gateway:
      routes:
        - id: test_route
          uri: https://myapi.com
          filters:
            - name: TestLoggingFilter
              args:
                value: ThisIsATest
```

