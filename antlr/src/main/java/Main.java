import antlr.MySqlDdlBaseListener;
import antlr.MySqlDdlLexer;
import antlr.MySqlDdlParser;
import org.antlr.v4.runtime.*;
import query.AddColumnQuery;
import query.AlterTableQuery;
import query.ChangeColumnQuery;
import query.DropColumnQuery;

import java.io.IOException;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        int benchmarkIteration = 100;
        String[] queries = {
                "query",
                "incorrect query",
                "alter table query",
                "alter table person drop hand",
                "alter table person drop column hand",
                "alter table person please drop column hand",
                "alter table person add column brain text",
                "alter table person add brain word",
                "alter table person add column brain integer null",
                "alter table person add brain integer not null",
                "alter table person change column hair dreadlocks text not null",
                "alter table person change column hair to dreadlocks text not null",
        };
        for (int i = 0; i < benchmarkIteration; i++) {
//            System.out.println("\n--------------- Iteration " + i + " ---------------");
            long start = System.nanoTime();
            for (String query : queries) {
//                System.out.println("\nParsing: " + query);
                try {
                    MySqlDdlLexer mySqlDdlLexer = new MySqlDdlLexer(CharStreams.fromString(query));
                    MySqlDdlParser mySqlDdlParser = new MySqlDdlParser(new CommonTokenStream(mySqlDdlLexer));
//                    mySqlDdlParser.addErrorListener(new BaseErrorListener() {
//                        @Override
//                        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
//                            throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
//                        }
//                    });
                    mySqlDdlParser.removeErrorListeners();
                    mySqlDdlParser.addParseListener(new MySqlDdlBaseListener() {
                        private AlterTableQuery alterTableQuery;

                        @Override
                        public void exitAlterSpecification(MySqlDdlParser.AlterSpecificationContext ctx) {
                            if (ctx.action != null) {
                                switch (ctx.action.getText()) {
                                    case "add":
                                        alterTableQuery = new AddColumnQuery();
                                        break;
                                    case "drop":
                                        alterTableQuery = new DropColumnQuery();
                                        break;
                                    case "change":
                                        alterTableQuery = new ChangeColumnQuery();
                                        break;
                                    default:
                                        alterTableQuery = null;
                                }
                                if (alterTableQuery != null) {
                                    alterTableQuery.workOnAlterSpecification(ctx);
                                }
                            }
                            super.exitAlterSpecification(ctx);
                        }

                        @Override
                        public void exitAlterTable(MySqlDdlParser.AlterTableContext ctx) {
                            if (ctx.exception == null && alterTableQuery != null) {
                                alterTableQuery.workOnAlterTable(ctx);
                                System.out.println(alterTableQuery);
                            }
                            super.exitAlterTable(ctx);
                        }
                    });
                    mySqlDdlParser.alterTable();
                } catch (IllegalStateException e) {
//                    System.out.println(e.getMessage());
                }
            }
            long end = System.nanoTime();
//            System.out.println("Computation time: " + (end - start) / 1000 + "ns\n");
            System.out.println((end - start) / 1000);
        }
    }

}
