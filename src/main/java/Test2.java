import net.andreinc.mockneat.MockNeat;

import java.nio.file.Path;
import java.util.List;

import static net.andreinc.mockneat.unit.seq.Seq.seq;
import static net.andreinc.mockneat.unit.text.Formatter.fmt;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.user.Names.names;

public class Test2 {

    public static void main(String[] args) {

        System.out.println("test");
        MockNeat m = MockNeat.threadLocal();
        List<String> somePeople = names().full().list(10).get();

        fmt("#{person} rolled: #{roll1} #{roll2}")
                .param("person", seq(somePeople))
                .param("roll1", ints().rangeClosed(1, 6))
                .param("roll2", ints().rangeClosed(1, 6))
                .accumulate(10, "\n")
                .consume(System.out::println);

        System.out.println("\nWho wins ?\n");
    }

}
