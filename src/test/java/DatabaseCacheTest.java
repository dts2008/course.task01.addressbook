import org.example.addressbook.application.Common.DatabaseCache;
import org.example.addressbook.application.DTO.CityInfo;
import org.example.addressbook.application.DTO.UserInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseCacheTest {

    private static ApplicationContext context;

    @BeforeAll
    static void Init()
    {
        context = new ClassPathXmlApplicationContext("context.xml");
    }

    @Test
    void selectTest() {
        var cache = context.getBean(DatabaseCache.class);

        assertNotNull(cache.getInfo(UserInfo.class));
        assertNotNull(cache.getInfo(CityInfo.class));
    }
}
