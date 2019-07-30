import Gra.Swiat.IZyje;
import Gra.Swiat.Lokalizacja;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationTest implements IZyje {
    @Test
    public void x1y24locationTest_locationArray_x2y24_and_x1y23(){
        final List<Lokalizacja> expected = Arrays.asList(new Lokalizacja(2, 24), new Lokalizacja(1, 23));
        List<Lokalizacja> actual = mozliweSciezki(new Lokalizacja(1,24));
        assertThat(expected).isEqualTo(actual);
    }
}
