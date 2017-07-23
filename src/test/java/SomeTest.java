import com.blogspot.positiveguru.config.Config;
import com.blogspot.positiveguru.utils.ConfigUtil;

/**
 * Created by Avenir Voronov on 7/19/2017.
 */

public class SomeTest {

    public void testConfigFilling(){
        //given
        String[] args="-c IcmpPing -hf hosts.txt -n 5 -d 3 -log logs.log  -r www.google.com".split(" ");
        //when
        ConfigUtil.fillConfigFromArray(args);
        //then
        assert(!Config.getHosts().isEmpty());
    }
}
