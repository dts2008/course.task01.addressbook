import Common.DatabaseCache;
import DTO.CityInfo;
import DTO.CommonInfo;
import DTO.UserInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseCacheTest {
    @Test
    public void selectTest() {
        var cache = new DatabaseCache();

        assertNotNull(cache.getInfo(UserInfo.class));
        assertNotNull(cache.getInfo(CityInfo.class));
    }
}
