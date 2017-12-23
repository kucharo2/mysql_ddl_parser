package cz.kucharo2.mysql.assembler;

import cz.kucharo2.mysql.query.ChangeColumnQuery;
import sjm.parse.Assembly;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class ChangeColumnAssembler extends AddColumnAssembler {

    @Override
    public void workOn(Assembly a) {
        ChangeColumnQuery addColumnQuery = new ChangeColumnQuery();
        workOnColumnDefinition(a, addColumnQuery);
        addColumnQuery.setOldColumnName(popString(a));
        addColumnQuery.setTableName(popString(a));

        a.setTarget(addColumnQuery);
    }
}
