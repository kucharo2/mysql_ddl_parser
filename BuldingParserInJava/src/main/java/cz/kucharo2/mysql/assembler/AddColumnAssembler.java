package cz.kucharo2.mysql.assembler;

import cz.kucharo2.mysql.query.AddColumnQuery;
import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

/**
 * Assembler for alter table - add column query type
 *
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class AddColumnAssembler extends Assembler {

    /**
     * Sets an assembly's target to be a {@link AddColumnQuery} objects.
     *
     * For this assembler the assembly stack contains nullability option, column definition,
     * column and table name for drop query.
     */
    public void workOn(Assembly a) {
        AddColumnQuery addColumnQuery = new AddColumnQuery();
        workOnColumnDefinition(a, addColumnQuery);
        addColumnQuery.setTableName(popString(a));

        a.setTarget(addColumnQuery);
    }

    protected void workOnColumnDefinition(Assembly a, AddColumnQuery addColumnQuery) {
        String pop1 = popString(a);
        // check if query contains nullability option
        if (pop1.equals("null")) {
            String pop2 = popString(a);
            if(pop2.equals("not")) {
                addColumnQuery.setColumnNullability(false);
                addColumnQuery.setColumnDefinition(popString(a));
            } else {
                addColumnQuery.setColumnNullability(true);
                addColumnQuery.setColumnDefinition(pop2);
            }
        } else {
            addColumnQuery.setColumnDefinition(pop1);
        }
        addColumnQuery.setColumnName(popString(a));
    }

    protected String popString(Assembly a) {
        return ((Token) a.pop()).sval();
    }
}
