package scratchpad.jline;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Test");
        final Terminal terminal = TerminalBuilder.terminal();

        //https://github.com/jline/jline3/wiki/LineReader
        final LineReader lineReader = LineReaderBuilder.builder()
            .terminal(terminal)
            .build();

        String name = lineReader.readLine("Enter name:");
        String pw = lineReader.readLine("Enter password:", '*');
        terminal.close();
        System.out.println(name + " - " + pw);
    }
}
