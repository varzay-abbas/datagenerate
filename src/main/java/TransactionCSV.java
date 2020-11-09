import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.CreditCardType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

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

public class TransactionCSV {

    public static void main(String[] args) {


        MockNeat m = MockNeat.threadLocal();

        m.fmt("#{creditCardNum} #{first} #{last}")
                .param("creditCardNum", m.creditCards().type(CreditCardType.AMERICAN_EXPRESS))
                .param("first", m.names().first())
                .param("last", m.names().last())
                .list(10000)
                .consume(list -> {
                    try {
                        Path file = Paths.get("./transactions.csv");
                        Files.write(file, list, StandardCharsets.UTF_8);
                        System.out.println("Successfully generated csv file.");
                    }
                    catch (IOException e) {
                            e.printStackTrace();
                    }
                });



    }

}
