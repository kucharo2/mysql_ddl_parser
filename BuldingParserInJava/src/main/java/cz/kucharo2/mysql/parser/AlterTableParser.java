package cz.kucharo2.mysql.parser;

import cz.kucharo2.mysql.assembler.AddColumnAssembler;
import cz.kucharo2.mysql.assembler.ChangeColumnAssembler;
import cz.kucharo2.mysql.assembler.DropColumnAssembler;
import sjm.parse.*;
import sjm.parse.tokens.CaselessLiteral;
import sjm.parse.tokens.TokenAssembly;
import sjm.parse.tokens.Word;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class AlterTableParser {

    public void parse(String text) {
        Parser parser = query();
        Assembly assembly = parser.bestMatch(new TokenAssembly(text));
        if (assembly == null) {
            System.out.println("Nothing matched.");
        } else if (assembly.getTarget() == null) {
            System.out.println("Match found, but bo target specified");
        } else {
            System.out.println(assembly.getTarget());
        }
    }

    protected Parser query() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("alter").discard());
        sequence.add(new CaselessLiteral("table").discard());
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
        sequence.add(new CaselessLiteral("add").discard());
        sequence.add(optional(new CaselessLiteral("column").discard()));
        sequence.add(columnName());
        sequence.add(columnDefinition());

        sequence.setAssembler(new AddColumnAssembler());
        return sequence;
    }

    protected Parser dropColumn() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("drop").discard());
        sequence.add(optional(new CaselessLiteral("column").discard()));
        sequence.add(columnName());

        sequence.setAssembler(new DropColumnAssembler());

        return sequence;
    }

    protected Parser changeColumn() {
        Sequence sequence = new Sequence();
        sequence.add(new CaselessLiteral("change").discard());
        sequence.add(optional(new CaselessLiteral("column").discard()));
        // old column
        sequence.add(columnName());
        // new column
        sequence.add(columnName());
        sequence.add(columnDefinition());

        sequence.setAssembler(new ChangeColumnAssembler());
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
