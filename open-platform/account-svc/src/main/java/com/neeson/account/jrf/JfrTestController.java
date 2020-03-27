package com.neeson.account.jrf;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/27 23:01
 */
@RestController
@RequestMapping("JfrTestController")
public class JfrTestController {

    @GetMapping("testThread")
    public void testThread() {
        while (true) {

        }
    }

    @GetMapping("testThread2")
    public void testThread2() throws InterruptedException {
        Thread.sleep(10000L);
    }

    @GetMapping("testOOM")
    public void testOOM(int i) throws InterruptedException {
        List<byte[]> bytes = Lists.newArrayList();
        for (int j = 0; j < i ; j++) {
            bytes.add(new byte[1024 * 1024]);
        }
        Thread.sleep(5000L);
    }

}
