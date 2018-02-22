grammar MySqlDdl;

// Parser rules
alterTable:
ALTER TABLE tableName=TEXT alterSpecification;

alterSpecification :
action=ADD (COLUMN|) columnName=TEXT columnDefinition
 | action=DROP (COLUMN|) columnName=TEXT
 | action=CHANGE (COLUMN|) columnName=TEXT newColumnName=TEXT columnDefinition;

columnDefinition :
dataType=DATA_TYPE nullability=(NOT_NULL | NULL)?;

// Lexer rules
ALTER: 'alter';
TABLE: 'table';

ADD: 'add';
DROP: 'drop';
CHANGE: 'change';

COLUMN: 'column';
NULL : 'null';
NOT_NULL : 'not' 'null';

DATA_TYPE : ('date' | 'text' | 'integer');

TEXT : ('a'..'z' | 'A'..'Z' | '_' | '$' | '0'..'9')+ ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;

