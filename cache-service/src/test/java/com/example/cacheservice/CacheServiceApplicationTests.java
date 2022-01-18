package com.example.cacheservice;

import com.example.cacheservice.dto.ResultDTO;
import com.example.cacheservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class CacheServiceApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void aVoid() throws Throwable {
        TestRunnable testRunnable = new TestRunnable() {
            @Override
            public void runTest() {
                ResultDTO<Object> info = userService.getInfo("a1e6d3ea-d6e2-41f0-af5c-7a262cc0557d");
                log.info(String.valueOf(info));
            }
        };
        int runnerCount = 3;
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = testRunnable;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 并发执行测试内容
        mttr.runTestRunnables();
    }

}
