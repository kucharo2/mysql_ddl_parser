package cz.kucharo2.mysql.assembler;

import cz.kucharo2.mysql.query.DropColumnQuery;
import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

/**
 * Assembler for alter table - drop column query type
 *
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class DropColumnAssembler extends Assembler {

    /**
     * Sets an assembly's target to be a {@link DropColumnQuery} objects.
     *
     * For this assembler the assembly stack contains column and table name for drop query.
     */
    public void workOn(Assembly a) {
        Token columnName = (Token) a.pop();
        Token tableName = (Token) a.pop();

        a.setTarget(new DropColumnQuery(columnName.sval(), tableName.sval()));
    }
}
