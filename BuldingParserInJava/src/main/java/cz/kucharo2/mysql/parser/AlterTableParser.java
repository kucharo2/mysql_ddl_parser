package cz.kucharo2.mysql.parser;

import sjm.parse.Alternation;
import sjm.parse.Empty;
import sjm.parse.Parser;
import sjm.parse.Sequence;
import sjm.parse.tokens.CaselessLiteral;
import sjm.parse.tokens.TokenAssembly;
import sjm.parse.tokens.Word;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class AlterTableParser {

    public void parse(String text) {
        Parser parser = query();
        System.out.println(parser.bestMatch(new TokenAssembly(text)));
    }

    protected Parser query() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("alter"));
        sequence.add(new CaselessLiteral("table"));
        sequence.add(tableName());
        sequence.add(alterSpecification());
        return sequence;
    }

    protected Parser alterSpecification() {
        Alternation alternation = new Alternation();
        alternation.add(addColumn());
        alternation.add(dropColumn());
        alternation.add(changeColumn());
        return alternation;
    }

    protected Parser addColumn() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("add"));
        sequence.add(optional(new CaselessLiteral("column")));
        sequence.add(columnName());
        sequence.add(columnDefinition());
        return sequence;
    }

    protected Parser dropColumn() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("drop"));
        sequence.add(optional(new CaselessLiteral("column")));
        sequence.add(columnName());
        return sequence;
    }

    protected Parser changeColumn() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("change"));
        sequence.add(optional(new CaselessLiteral("column")));
        // old column
        sequence.add(columnName());
        // new column
        sequence.add(columnName());
        sequence.add(columnDefinition());
        return sequence;
    }

    protected Parser columnDefinition() {
        Sequence sequence = new Sequence();
        Alternation nullability = new Alternation();
        nullability.add(notNull());
        nullability.add(new CaselessLiteral("null"));

        sequence.add(dataType());
        sequence.add(optional(nullability));
        return sequence;
    }

    protected Parser notNull() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("not"));
        sequence.add(new CaselessLiteral("null"));
        return sequence;
    }

    protected Parser dataType() {
        Alternation alternation = new Alternation();
        alternation.add(new CaselessLiteral("date"));
        alternation.add(new CaselessLiteral("text"));
        alternation.add(new CaselessLiteral("integer"));
        return alternation;
    }

    protected Parser tableName() {
        return new Word();
    }

    protected Parser columnName() {
        return new Word();
    }

    protected Parser optional(Parser parser) {
        Alternation alternation = new Alternation();
        alternation.add(parser);
        alternation.add(new Empty());
        return alternation;
    }
}
