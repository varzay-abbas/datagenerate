import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.CreditCardType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static net.andreinc.mockneat.unit.seq.Seq.seq;
import static net.andreinc.mockneat.unit.text.Formatter.fmt;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.user.Names.names;

public class Test3 {

    public static void main(String[] args) {

        System.out.println("test");
        MockNeat m = MockNeat.threadLocal();
        //List<String> somePeople = names().full().list(10).get();

       /* fmt("#{person} rolled: #{roll1} #{roll2}")
                .param("person", seq(somePeople))
                .param("roll1", ints().rangeClosed(1, 6))
                .param("roll2", ints().rangeClosed(1, 6))
                .accumulate(10, "\n")
                .consume(System.out::println);

        System.out.println("\nWho wins ?\n"); */
        m.fmt("#{id} #{first} #{last} #{email} #{salary} #{creditCardNum}")
          .param("id", m.longSeq().start(10).increment(10))
          .param("first", m.names().first())
          .param("last", m.names().last())
          .param("email", m.emails().domain("ddl.com"))
          .param("salary", m.ints().range(1000, 5000))
          .param("creditCardNum", m.creditCards().type(CreditCardType.AMERICAN_EXPRESS))
          .list(10000)
          .consume(list -> {
               try {

                   Path file = Paths.get("./test15.csv");
                   Files.write(file, list, StandardCharsets.UTF_8);
                   System.out.println("Successfully wrote to the file.");

               }
               catch (IOException e) {

               }
          });



    }

}
